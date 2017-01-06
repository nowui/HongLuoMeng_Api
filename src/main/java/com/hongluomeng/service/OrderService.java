package com.hongluomeng.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Private;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.OrderDao;
import com.hongluomeng.model.*;
import com.hongluomeng.type.OrderEnum;
import com.hongluomeng.type.PayTypeEnum;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.HttpKit;
import com.sun.jmx.snmp.Timestamp;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import static java.net.URLEncoder.encode;

public class OrderService extends BaseService {

    private OrderDao orderDao = new OrderDao();
    private MemberService memberService = new MemberService();
    private MemberDeliveryService memberDeliveryService = new MemberDeliveryService();
    private CartService cartService = new CartService();
    private ProductSkuService productSkuService = new ProductSkuService();
    private OrderProductService orderProductService = new OrderProductService();
    private ProductLockStockService productLockStockService = new ProductLockStockService();
    private BrandService brandService = new BrandService();
    private BrandApplyService brandApplyService = new BrandApplyService();
    private String company = "Shanghai Star Channel IT Co.,LTD";

    public Map<String, Object> list(JSONObject jsonObject) {
        Integer count = orderDao.count();

        List<Order> orderList = orderDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Order order : orderList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Order.COLUMN_ORDER_ID, order.getOrder_id());
            map.put(Order.COLUMN_ORDER_NO, order.getOrder_no());
            map.put(Order.COLUMN_ORDER_PAY_PRICE, order.getOrder_pay_price());
            map.put(Order.COLUMN_ORDER_PRODUCT_PAY_AMOUNT, order.getOrder_product_pay_amount());
            map.put(Order.COLUMN_ORDER_STATUS, order.getOrder_status_value());

            list.add(map);
        }

        Map<String, Object> resultMap = Utility.setResultMap(count, list);

        return resultMap;
    }

    public Order find(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        Order order = orderDao.findByOrder_id(orderMap.getOrder_id());

        return order;
    }

    public Map<String, Object> save(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        Cart cartMap = jsonObject.toJavaObject(Cart.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<Cart> cartList = new ArrayList<Cart>();
        cartList.add(cartMap);

        orderMap.setCartList(cartList);

        Order order = save(orderMap, request_user_id, false);

        Object sign = sign(order, orderMap.getOrder_pay_type());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Order.COLUMN_ORDER_ID, order.getOrder_id());
        resultMap.put(Order.COLUMN_ORDER_SIGN, sign);

        return resultMap;
    }

    public Map<String, Object> saveCart(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        Order order = save(orderMap, request_user_id, true);

        Object sign = sign(order, orderMap.getOrder_pay_type());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Order.COLUMN_ORDER_ID, order.getOrder_id());
        resultMap.put(Order.COLUMN_ORDER_SIGN, sign);

        return resultMap;
    }

    private Order save(Order order, String request_user_id, Boolean isChekCart) {

        Member member = memberService.findByUser_id(request_user_id);

        if (order.getCartList().size() == 0) {
            throw new RuntimeException("商品列表为空");
        }

        for (Cart cart : order.getCartList()) {
            if (cart.getProduct_amount() <= 0) {
                throw new RuntimeException("商品数量不能为空");
            }
        }

        List<String> productSkuIdList = new ArrayList<String>();

        for (Cart cart : order.getCartList()) {
            productSkuIdList.add(cart.getProduct_sku_id());
        }

        List<ProductSku> productSkuList = productSkuService.listByProductSkuIdList(productSkuIdList);

        //检测所有商品的所属品牌是否已经代理
        for (ProductSku productSku : productSkuList) {
            Boolean isApply = brandService.checkIsApply(productSku.getBrand().getBrand_id(), request_user_id);

            if (!isApply) {
                throw new RuntimeException(productSku.getProduct().getProduct_name() + "所属品牌未代理");
            }
        }

        for (Cart cart : order.getCartList()) {
            //SKU编号是否存在
            Boolean isExit = false;
            //数量是否超出
            Boolean isOver = false;

            for (ProductSku productSku : productSkuList) {
                if (cart.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
                    isExit = true;

                    //设置商品数量, 用在下面计算商品数量和商品总价格
                    productSku.setProduct_amount(cart.getProduct_amount());

                    if (cart.getProduct_amount() + productSku.getProductLockStock().getProduct_lock_stock() > productSku.getProduct_stock()) {
                        isOver = true;
                    }

                    break;
                }
            }

            if (!isExit) {
                throw new RuntimeException("没有找到的商品SKU编号是:" + cart.getProduct_sku_id());
            }

            if (isOver) {
                throw new RuntimeException("超过库存的商品SKU编号是:" + cart.getProduct_sku_id());
            }
        }

        //是否直接购买, 否就检查购物车并且扣购物车库存
        if (isChekCart) {
            List<Cart> cartList = cartService.listByUser_idAndproductSkuIdList(request_user_id, productSkuIdList);

            List<Cart> cartUpdateList = new ArrayList<Cart>();
            List<Cart> cartDeleteList = new ArrayList<Cart>();

            //判断传过来的商品编号在购物车是否存在对应的数据
            for (Cart cart : order.getCartList()) {
                //SKU编号是否存在
                Boolean isExit = false;
                //数量是否超出
                Boolean isOver = false;

                for (Cart cartObject : cartList) {
                    if (cart.getProduct_sku_id().equals(cartObject.getProduct_sku_id())) {
                        isExit = true;

                        if (cart.getProduct_amount() > cartObject.getProduct_amount()) {
                            isOver = true;
                        } else {
                            //扣除购物车里面商品数量
                            int count = cartObject.getProduct_amount() - cart.getProduct_amount();

                            if (count > 0) {
                                cartObject.setProduct_amount(cartObject.getProduct_amount() - cart.getProduct_amount());

                                cartUpdateList.add(cartObject);
                            } else {
                                cartDeleteList.add(cartObject);
                            }
                        }

                        break;
                    }
                }

                if (!isExit) {
                    throw new RuntimeException("在购物车没有找到的商品SKU编号是:" + cart.getProduct_sku_id());
                }

                if (isOver) {
                    throw new RuntimeException("超过购物车中数量的商品SKU编号是:" + cart.getProduct_sku_id());
                }
            }

            //更新购物车商品数量
            cartService.updateProduct_amount(cartUpdateList, request_user_id);
            cartService.delete(cartDeleteList, request_user_id);
        }

        //订单总价格
        BigDecimal order_price = BigDecimal.valueOf(0);
        for (ProductSku productSku : productSkuList) {
            if (member.getMember_level_id().equals("")) {
                order_price = order_price.add(productSku.getProduct_price().multiply(BigDecimal.valueOf(productSku.getProduct_amount())));
            } else {
                Boolean isExit = false;

                JSONArray array = productSku.getMember_level_price();

                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String member_level_id = object.getString(MemberLevel.COLUMN_MEMBER_LEVEL_ID);

                    if (member.getMember_level_id().equals(member_level_id)) {
                        BigDecimal member_level_price = object.getBigDecimal(ProductSku.COLUMN_MEMBER_LEVEL_PRICE);

                        order_price = order_price.add(member_level_price.multiply(BigDecimal.valueOf(productSku.getProduct_amount())));

                        isExit = true;
                    }
                }

                if (!isExit) {
                    order_price = order_price.add(productSku.getProduct_price().multiply(BigDecimal.valueOf(productSku.getProduct_amount())));
                }
            }
        }
        order.setOrder_pay_price(order_price);


        //订单商品数量
        int order_product_pay_amount = 0;
        for (Cart cart : order.getCartList()) {
            order_product_pay_amount += cart.getProduct_amount();
        }
        order.setOrder_product_pay_amount(order_product_pay_amount);

        //更新收货信息
        MemberDelivery memberDelivery = memberDeliveryService.findByMember_delivery_id(order.getMember_delivery_id());
        if (memberDelivery == null) {
            throw new RuntimeException("无法获取收货信息");
        }
        order.setOrder_delivery_name(memberDelivery.getMember_delivery_name());
        order.setOrder_delivery_phone(memberDelivery.getMember_delivery_phone());
        order.setOrder_delivery_province(memberDelivery.getMember_delivery_province());
        order.setOrder_delivery_city(memberDelivery.getMember_delivery_city());
        order.setOrder_delivery_area(memberDelivery.getMember_delivery_area());
        order.setOrder_delivery_address(memberDelivery.getMember_delivery_address());
        order.setOrder_delivery_zip(memberDelivery.getMember_delivery_zip());

        orderDao.save(order, member.getMember_level_id(), member.getMember_level_name(), member.getMember_level_value(), request_user_id);

        //保存购物车里的商品到订单商品
        List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
        List<ProductLockStock> productLockStockList = new ArrayList<ProductLockStock>();
        for (ProductSku productSku : productSkuList) {
            BigDecimal product_pay_price = BigDecimal.valueOf(0);

            if (member.getMember_level_id().equals("")) {
                product_pay_price = productSku.getProduct_price();
            } else {
                Boolean isExit = false;

                JSONArray array = productSku.getMember_level_price();

                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String member_level_id = object.getString(MemberLevel.COLUMN_MEMBER_LEVEL_ID);

                    if (member.getMember_level_id().equals(member_level_id)) {
                        product_pay_price = object.getBigDecimal(ProductSku.COLUMN_MEMBER_LEVEL_PRICE);

                        isExit = true;
                    }
                }

                if (!isExit) {
                    product_pay_price = productSku.getProduct_price();
                }
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder_id(order.getOrder_id());
            orderProduct.setCategory_id(productSku.getCategory().getCategory_id());
            orderProduct.setCategory_name(productSku.getCategory().getCategory_name());
            orderProduct.setBrand_id(productSku.getBrand().getBrand_id());
            orderProduct.setBrand_name(productSku.getBrand().getBrand_name());
            orderProduct.setProduct_id(productSku.getProduct_id());
            orderProduct.setProduct_name(productSku.getProduct().getProduct_name());
            orderProduct.setProduct_image(productSku.getProduct().getProduct_image().toJSONString());
            orderProduct.setProduct_is_new(productSku.getProduct().getProduct_is_new());
            orderProduct.setProduct_is_recommend(productSku.getProduct().getProduct_is_recommend());
            orderProduct.setProduct_is_bargain(productSku.getProduct().getProduct_is_bargain());
            orderProduct.setProduct_is_hot(productSku.getProduct().getProduct_is_hot());
            orderProduct.setProduct_is_sell_out(productSku.getProduct().getProduct_is_sell_out());
            orderProduct.setProduct_is_sale(productSku.getProduct().getProduct_is_sale());
            orderProduct.setProduct_content(productSku.getProduct().getProduct_content());
            orderProduct.setProduct_sku_value(productSku.getProduct().getProduct_sku_value().toJSONString());
            orderProduct.setProduct_sku_id(productSku.getProduct_sku_id());
            orderProduct.setProduct_attribute_value(productSku.getProduct_attribute_value().toJSONString());
            orderProduct.setProduct_price(productSku.getProduct_price());
            orderProduct.setMember_level_price(productSku.getMember_level_price().toJSONString());
            orderProduct.setProduct_pay_price(product_pay_price);
            orderProduct.setProduct_pay_amount(0);
            for (Cart cart : order.getCartList()) {
                if (cart.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
                    orderProduct.setProduct_pay_amount(cart.getProduct_amount());
                }
            }
            orderProductList.add(orderProduct);

            ProductLockStock productLockStock = new ProductLockStock();
            productLockStock.setOrder_no(order.getOrder_no());
            productLockStock.setProduct_sku_id(productSku.getProduct_sku_id());
            productLockStock.setProduct_lock_stock(0);
            for (Cart cart : order.getCartList()) {
                if (cart.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
                    productLockStock.setProduct_lock_stock(cart.getProduct_amount());
                }
            }
            productLockStockList.add(productLockStock);
        }

        orderProductService.saveByOrderProductList(orderProductList, request_user_id);

        productLockStockService.save(productLockStockList, request_user_id);

        return order;
    }

    public void update(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        orderDao.update(orderMap, request_user_id);
    }

    public void delete(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        orderDao.delete(orderMap.getOrder_id(), request_user_id);
    }

    public List<Map<String, Object>> getList(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<Order> orderList = orderDao.listByUser_idAndOrder_flow_status(request_user_id, orderMap.getOrder_status(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<String> orderIdList = new ArrayList<String>();

        for (Order order : orderList) {
            orderIdList.add(order.getOrder_id());
        }

        List<OrderProduct> orderProductList = orderProductService.listByOrderIdList(orderIdList);

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        for (Order order : orderList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Order.COLUMN_ORDER_ID, order.getOrder_id());
            map.put(Order.COLUMN_ORDER_NO, order.getOrder_no());
            map.put(Order.COLUMN_ORDER_PAY_PRICE, order.getOrder_pay_price());
            map.put(Order.COLUMN_ORDER_PRODUCT_PAY_AMOUNT, order.getOrder_product_pay_amount());
            map.put(Order.COLUMN_ORDER_DELIVERY_NAME, order.getOrder_delivery_name());
            map.put(Order.COLUMN_ORDER_STATUS, order.getOrder_status());

            List<Map<String, Object>> orderProductRsultList = new ArrayList<Map<String, Object>>();

            for (OrderProduct orderProduct : orderProductList) {
                if (orderProduct.getOrder_id().equals(order.getOrder_id())) {
                    Map<String, Object> orderProductMap = new HashMap<String, Object>();
                    orderProductMap.put(OrderProduct.COLUMN_PRODUCT_ID, orderProduct.getProduct_id());
                    orderProductMap.put(OrderProduct.COLUMN_PRODUCT_NAME, orderProduct.getProduct_name());
                    orderProductMap.put(Product.COLUMN_PRODUCT_IMAGE, orderProduct.getProduct_image().get(0));
                    orderProductMap.put(OrderProduct.KEY_PRODUCT_PAY_PRICE, orderProduct.getProduct_pay_price());
                    orderProductMap.put(OrderProduct.KEY_PRODUCT_PAY_AMOUNT, orderProduct.getProduct_pay_amount());

                    String attribute_value = "";
                    for (int i = 0; i < orderProduct.getProduct_attribute_value().size(); i++) {
                        JSONObject object = orderProduct.getProduct_attribute_value().getJSONObject(i);

                        if (i > 0) {
                            attribute_value += "_";
                        }

                        attribute_value += object.getString(CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE);
                    }
                    orderProductMap.put(OrderProduct.COLUMN_PRODUCT_ATTRIBUTE_VALUE, attribute_value);

                    orderProductRsultList.add(orderProductMap);
                }
            }
            map.put(Order.COLUMN_ORDER_LIST, orderProductRsultList);

            resultList.add(map);
        }

        return resultList;
    }

    public Map<String, Object> pay(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        Order order = orderDao.findByOrder_id(orderMap.getOrder_id());

        if (!order.getOrder_status().equals(OrderEnum.WAIT.getKey())) {
            throw new RuntimeException("该订单已经支付过");
        }

        Object sign = sign(order, order.getOrder_pay_type());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Order.COLUMN_ORDER_ID, order.getOrder_id());
        resultMap.put(Order.COLUMN_ORDER_SIGN, sign);

        return resultMap;
    }

    public void payed(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        orderDao.payed(orderMap.getOrder_id());
    }

    private Object sign(Order order, String order_pay_type) {
        if (order.getOrder_pay_price().compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("价格不能为零");
        }

        if (order_pay_type.equals(PayTypeEnum.ALI_PAY.getKey())) {
            try {
                String content = "{\"timeout_express\":\"" + Const.ORDER_TIMEOUT_EXPRESS + "m\",\"seller_id\":\"\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + order.getOrder_pay_price() + "\",\"subject\":\"" + company + "\",\"body\":\"" + company + "\",\"out_trade_no\":\"" + order.getOrder_no() + "\"}";
                String data = "app_id=" + Private.ALIPAY_APP_ID + "&biz_content=" + content + "&charset=" + Private.ALIPAY_INPUT_CHARSET + "&format=json&method=alipay.trade.app.pay&notify_url=" + Private.ALIPAY_NOTIFY_URL + "&sign_type=" + Private.ALIPAY_SIGN_TYPE + "&timestamp=" + Utility.getDateTimeString(new Date()) + "&version=1.0";
                String sign = AlipaySignature.rsaSign(data, Private.ALIPAY_PRIVATE_KEY, Private.ALIPAY_INPUT_CHARSET, Private.ALIPAY_SIGN_TYPE);

                String orderInfo = "app_id=" + Private.ALIPAY_APP_ID + "&biz_content=" + encode(content, Private.ALIPAY_INPUT_CHARSET) + "&charset=" + Private.ALIPAY_INPUT_CHARSET + "&format=json&method=alipay.trade.app.pay&notify_url=" + encode(Private.ALIPAY_NOTIFY_URL, Private.ALIPAY_INPUT_CHARSET) + "&sign_type=" + Private.ALIPAY_SIGN_TYPE + "&timestamp=" + encode(Utility.getDateTimeString(new Date()), Private.ALIPAY_INPUT_CHARSET) + "&version=1.0";
                orderInfo += "&sign=" + encode(sign, Private.ALIPAY_INPUT_CHARSET);

                return orderInfo;
            } catch (AlipayApiException e) {
                throw new RuntimeException("生成签名错误");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("生成签名错误");
            }
        } else {
            return unifiedorder(order);
        }
    }

    public String notify(Map<String, String> parameterMap, String request_user_id) {
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(parameterMap, Private.ALIPAY_PUBLIC_KEY, Private.ALIPAY_INPUT_CHARSET);

            if (signVerified) {
                String order_no = parameterMap.get("out_trade_no");
                String order_trade_no = parameterMap.get("trade_no");
                String order_trade_account = parameterMap.get("buyer_id");
                String order_trade_price = parameterMap.get("receipt_amount");
                String order_pay_result = JSONObject.toJSONString(parameterMap);

                int result = orderDao.updateTrade(order_no, order_trade_no, order_trade_account, order_trade_price, order_pay_result);

                //删除锁定库存
                productLockStockService.delete(order_no, request_user_id);

                if (result == 1) {
                    return Const.KEY_SUCCESS;
                } else {
                    return Const.KEY_FAILURE;
                }
            } else {
                return Const.KEY_FAILURE;
            }
        } catch (AlipayApiException e) {
            return Const.KEY_FAILURE;
        }


    }

    public Map<String, Object> unifiedorder(Order order) {
        StringBuffer bf = new StringBuffer();

        bf.append("<xml>");

        bf.append("<appid><![CDATA[");
        bf.append(Private.WEIXIN_PAY_APP_ID);
        bf.append("]]></appid>");

        bf.append("<attach><![CDATA[");
        bf.append(company);
        bf.append("]]></attach>");

        bf.append("<body><![CDATA[");
        bf.append(company);
        bf.append("]]></body>");

        bf.append("<mch_id><![CDATA[");
        bf.append(Private.WEIXIN_PAY_MCH_ID);
        bf.append("]]></mch_id>");

        String nonce_str = Utility.getRandomStringByLength(32);

        bf.append("<nonce_str><![CDATA[");
        bf.append(nonce_str);
        bf.append("]]></nonce_str>");

        String notify_url = "http://api.hongluomeng.nowui.com/weixin/notify";

        bf.append("<notify_url><![CDATA[");
        bf.append(notify_url);
        bf.append("]]></notify_url>");

        String out_trade_no = order.getOrder_no();

        bf.append("<out_trade_no><![CDATA[");
        bf.append(out_trade_no);
        bf.append("]]></out_trade_no>");

        String spbill_create_ip = "0.0.0.0";

        bf.append("<spbill_create_ip><![CDATA[");
        bf.append(spbill_create_ip);
        bf.append("]]></spbill_create_ip>");

        DecimalFormat format = new DecimalFormat("0");

        String total_fee = format.format(order.getOrder_pay_price().multiply(BigDecimal.valueOf(100)));

        bf.append("<total_fee><![CDATA[");
        bf.append(total_fee);
        bf.append("]]></total_fee>");

        bf.append("<trade_type><![CDATA[");
        bf.append("APP");
        bf.append("]]></trade_type>");

        String stringA = "appid=" + Private.WEIXIN_PAY_APP_ID + "&attach=" + company + "&body=" + company + "&mch_id=" + Private.WEIXIN_PAY_MCH_ID + "&nonce_str=" + nonce_str + "&notify_url=" + notify_url + "&out_trade_no=" + out_trade_no + "&spbill_create_ip=" + spbill_create_ip + "&total_fee=" + total_fee + "&trade_type=APP";
        String stringSignTemp = stringA + "&key=" + Private.WEIXIN_PAY_KEY;
        String sign = HashKit.md5(stringSignTemp).toUpperCase();

        bf.append("<sign><![CDATA[");
        bf.append(sign);
        bf.append("]]></sign>");

        bf.append("</xml>");

        String result = HttpKit.post("https://api.mch.weixin.qq.com/pay/unifiedorder", bf.toString());

        try {
            Map<String, Object> map = Utility.getMapFromXML(result);

            map.put("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));

            return map;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("生成签名错误");
        } catch (IOException e) {
            throw new RuntimeException("生成签名错误");
        } catch (SAXException e) {
            throw new RuntimeException("生成签名错误");
        }
    }

}

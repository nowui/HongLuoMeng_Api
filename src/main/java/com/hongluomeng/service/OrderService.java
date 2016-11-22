package com.hongluomeng.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.OrderDao;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.MemberLevel;
import com.hongluomeng.model.Order;
import com.hongluomeng.model.OrderProduct;
import com.hongluomeng.model.ProductSku;
import com.hongluomeng.model.ProductLockStock;
import com.hongluomeng.type.PayTypeEnum;

import static java.net.URLEncoder.encode;

public class OrderService {

    private OrderDao orderDao = new OrderDao();
    private MemberService memberService = new MemberService();
    private CartService cartService = new CartService();
    private ProductSkuService productSkuService = new ProductSkuService();
    private OrderProductService orderProductService = new OrderProductService();
    private ProductLockStockService productLockStockService = new ProductLockStockService();

    public Map<String, Object> list(JSONObject jsonObject) {
        //Order orderMap = jsonObject.toJavaObject(Order.class);

        Integer count = orderDao.count();

        List<Order> orderList = orderDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Order order : orderList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Order.KEY_ORDER_ID, order.getOrder_id());
            map.put(Order.KEY_ORDER_NO, order.getOrder_no());
            map.put(Order.KEY_ORDER_TRADE_PRICE, order.getOrder_trade_price());

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

        List<Cart> cartList = new ArrayList<Cart>();
        cartList.add(cartMap);

        orderMap.setCartList(cartList);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        String order_id = save(orderMap, request_user_id, false);

        String sign = sign(order_id, orderMap.getOrder_pay_type());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Order.KEY_ORDER_ID, order_id);
        resultMap.put(Order.KEY_SIGN, sign);

        return resultMap;
    }

    public Map<String, Object> saveCart(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        String order_id = save(orderMap, request_user_id, true);

        String sign = sign(order_id, orderMap.getOrder_pay_type());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Order.KEY_ORDER_ID, order_id);
        resultMap.put(Order.KEY_SIGN, sign);

        return resultMap;
    }

    private String save(Order order, String request_user_id, Boolean isChekCart) {

        Member member = memberService.findByUser_id(request_user_id);

        if (order.getCartList().size() == 0) {
            throw new RuntimeException("商品列表为空");
        }

        for(Cart cart : order.getCartList()) {
            if(cart.getProduct_amount() <= 0) {
                throw new RuntimeException("商品数量不能为空");
            }
        }

        List<String> productSkuIdList = new ArrayList<String>();

        for (Cart cart : order.getCartList()) {
            productSkuIdList.add(cart.getProduct_sku_id());
        }

        List<ProductSku> productSkuList = productSkuService.listByProductSkuIdList(productSkuIdList);

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

                    if (cart.getProduct_amount() + productSku.getProduct_lock_stock() > productSku.getProduct_stock()) {
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

        //计算订单的总价格
        BigDecimal order_pament_price = BigDecimal.valueOf(0);
        for (ProductSku productSku : productSkuList) {
            if (member.getMember_level_id().equals("")) {
                order_pament_price = order_pament_price.add(productSku.getProduct_price().multiply(BigDecimal.valueOf(productSku.getProduct_amount())));
            } else {
                Boolean isExit = false;

                JSONArray array = productSku.getMember_level_price();

                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String member_level_id = object.getString(MemberLevel.KEY_MEMBER_LEVEL_ID);

                    if (member.getMember_level_id().equals(member_level_id)) {
                        BigDecimal member_level_price = object.getBigDecimal(ProductSku.KEY_MEMBER_LEVEL_PRICE);

                        order_pament_price = order_pament_price.add(member_level_price.multiply(BigDecimal.valueOf(productSku.getProduct_amount())));

                        isExit = true;
                    }
                }

                if (!isExit) {
                    order_pament_price = order_pament_price.add(productSku.getProduct_price().multiply(BigDecimal.valueOf(productSku.getProduct_amount())));
                }
            }
        }
        order.setOrder_trade_price(order_pament_price);

        orderDao.save(order, member.getMember_level_id(), member.getMember_level_name(), member.getMember_level_value(), request_user_id);

        //保存购物车里的商品
        List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
        List<ProductLockStock> productLockStockList = new ArrayList<ProductLockStock>();
        for (ProductSku productSku : productSkuList) {
            BigDecimal product_payment_price = BigDecimal.valueOf(0);

            if (member.getMember_level_id().equals("")) {
                product_payment_price = productSku.getProduct_price();
            } else {
                Boolean isExit = false;

                JSONArray array = productSku.getMember_level_price();

                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String member_level_id = object.getString(MemberLevel.KEY_MEMBER_LEVEL_ID);

                    if (member.getMember_level_id().equals(member_level_id)) {
                        //BigDecimal member_level_price = object.getBigDecimal(ProductSku.KEY_MEMBER_LEVEL_PRICE);

                        //product_payment_price = member_level_price;

                        isExit = true;
                    }
                }

                if (!isExit) {
                    product_payment_price = productSku.getProduct_price();
                }
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder_id(order.getOrder_id());
            orderProduct.setCategory_id(productSku.getCategory_id());
            orderProduct.setCategory_name(productSku.getCategory_name());
            orderProduct.setBrand_id(productSku.getBrand_id());
            orderProduct.setBrand_name(productSku.getBrand_name());
            orderProduct.setProduct_id(productSku.getProduct_id());
            orderProduct.setProduct_name(productSku.getProduct_name());
            orderProduct.setProduct_image(productSku.getProduct_image().toJSONString());
            orderProduct.setProduct_is_new(productSku.getProduct_is_new());
            orderProduct.setProduct_is_recommend(productSku.getProduct_is_recommend());
            orderProduct.setProduct_is_bargain(productSku.getProduct_is_bargain());
            orderProduct.setProduct_is_hot(productSku.getProduct_is_hot());
            orderProduct.setProduct_is_sell_out(productSku.getProduct_is_sell_out());
            orderProduct.setProduct_is_sale(productSku.getProduct_is_sale());
            orderProduct.setProduct_content(productSku.getProduct_content());
            orderProduct.setProduct_sku_value(productSku.getProduct_sku_value().toJSONString());
            orderProduct.setProduct_sku_id(productSku.getProduct_sku_id());
            orderProduct.setProduct_attribute_value(productSku.getProduct_attribute_value().toJSONString());
            orderProduct.setProduct_price(productSku.getProduct_price());
            orderProduct.setMember_level_price(productSku.getMember_level_price().toJSONString());
            orderProduct.setProduct_payment_price(product_payment_price);
            orderProduct.setProduct_payment_amount(0);
            for (Cart cart : order.getCartList()) {
                if (cart.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
                    orderProduct.setProduct_payment_amount(cart.getProduct_amount());
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

        return order.getOrder_id();
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

        List<Order> orderList = orderDao.listByUser_idAndOrder_flow_status(request_user_id, orderMap.getOrder_flow_status(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        for (Order order : orderList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Order.KEY_ORDER_ID, order.getOrder_id());
            map.put(Order.KEY_ORDER_NO, order.getOrder_no());
            map.put(Order.KEY_ORDER_TRADE_PRICE, order.getOrder_trade_price());
            map.put(Order.KEY_ORDER_FLOW_STATUS, order.getOrder_flow_status());

            resultList.add(map);
        }

        return resultList;
    }

    public void payed(JSONObject jsonObject) {
        Order orderMap = jsonObject.toJavaObject(Order.class);

        orderDao.payed(orderMap.getOrder_id());
    }

    private String sign(String order_id, String order_pay_type) {
        if (order_pay_type.equals(PayTypeEnum.ALI_PAY.getKey())) {
            try {
                Order order = orderDao.findByOrder_id(order_id);

                if(order == null) {
                    throw new RuntimeException("该订单不存在");
                }

                String content = "{\"timeout_express\":\"" + Const.ORDER_TIMEOUT_EXPRESS + "m\",\"seller_id\":\"\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + order.getOrder_trade_price() + "\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + order.getOrder_no() + "\"}";
                String data = "app_id=" + Const.ALIPAY_APP_ID + "&biz_content=" + content + "&charset=" + Const.ALIPAY_INPUT_CHARSET + "&format=json&method=alipay.trade.app.pay&notify_url=" + Const.ALIPAY_NOTIFY_URL + "&sign_type=" + Const.ALIPAY_SIGN_TYPE + "&timestamp=" + Utility.getDateTimeString(new Date()) + "&version=1.0";
                String sign = AlipaySignature.rsaSign(data, Const.ALIPAY_PRIVATE_KEY, Const.ALIPAY_INPUT_CHARSET, Const.ALIPAY_SIGN_TYPE);

                String orderInfo = "app_id=" + Const.ALIPAY_APP_ID + "&biz_content=" + encode(content, Const.ALIPAY_INPUT_CHARSET) + "&charset=" + Const.ALIPAY_INPUT_CHARSET + "&format=json&method=alipay.trade.app.pay&notify_url=" + encode(Const.ALIPAY_NOTIFY_URL, Const.ALIPAY_INPUT_CHARSET) + "&sign_type=" + Const.ALIPAY_SIGN_TYPE + "&timestamp=" + encode(Utility.getDateTimeString(new Date()), Const.ALIPAY_INPUT_CHARSET) + "&version=1.0";
                orderInfo += "&sign=" + encode(sign, Const.ALIPAY_INPUT_CHARSET);

                return orderInfo;
            } catch (AlipayApiException e) {
                throw new RuntimeException("生成签名错误");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("生成签名错误");
            }
        } else {
            return "";
        }
    }

    public String notify(String order_no, String order_trade_no, String order_trade_account, String order_trade_price, String request_user_id) {
        int result = orderDao.updateTrade(order_no, order_trade_no, order_trade_account, order_trade_price);

        //删除锁定库存
        productLockStockService.delete(order_no, request_user_id);

        if(result == 1) {

            return "success";
        } else {
            return "error";
        }
    }

}

package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.ProductDao;
import com.hongluomeng.model.*;
import com.hongluomeng.type.BrandApplyReviewEnum;
import com.hongluomeng.type.CatetoryEnum;

public class ProductService extends BaseService {

    private ProductDao productDao = new ProductDao();
    private CategoryService categoryService = new CategoryService();
    private BrandService brandService = new BrandService();
    private BrandApplyService brandApplyService = new BrandApplyService();
    private CategoryAttributeService categoryAttributeService = new CategoryAttributeService();
    private CategoryAttributeValueService categoryAttributeValueService = new CategoryAttributeValueService();
    private MemberLevelService memberLevelService = new MemberLevelService();
    private ProductSkuService productSkuService = new ProductSkuService();
    private ProductLockStockService productLockStockService = new ProductLockStockService();
    private ProductCollectService productCollectService = new ProductCollectService();

    public Map<String, Object> list(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        Integer count = productDao.count(productMap.getProduct_name());

        List<Product> productList = productDao.list(productMap.getProduct_name(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Product product : productList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Product.COLUMN_PRODUCT_ID, product.getProduct_id());
            map.put(Product.COLUMN_PRODUCT_NAME, product.getProduct_name());
            map.put(Product.COLUMN_PRODUCT_IS_NEW, product.getProduct_is_new());
            map.put(Product.COLUMN_PRODUCT_IS_RECOMMEND, product.getProduct_is_recommend());
            map.put(Product.COLUMN_PRODUCT_IS_BARGAIN, product.getProduct_is_bargain());
            map.put(Product.COLUMN_PRODUCT_IS_HOT, product.getProduct_is_hot());
            map.put(Product.COLUMN_PRODUCT_IS_SELL_OUT, product.getProduct_is_sell_out());
            map.put(Product.COLUMN_PRODUCT_PRICE, product.getProduct_price());
            map.put(Product.COLUMN_PRODUCT_STOCK, product.getProduct_stock());

            list.add(map);
        }

        return Utility.setResultMap(count, list);
    }

    public Product find(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        Product product;

        List<Category> categoryList = categoryService.listByCategory_key(CatetoryEnum.PRODUCT.getKey());
        for(Category category : categoryList) {
            category.keep(Category.COLUMN_CATEGORY_ID, Category.COLUMN_CATEGORY_NAME);
        }

        List<Brand> brandList = brandService.listAll();

        for(Brand brand : brandList) {
            brand.keep(Brand.COLUMN_BRAND_ID, Brand.COLUMN_BRAND_NAME);
        }

        List<MemberLevel> memberLevelList = memberLevelService.listAll();

        for(MemberLevel memberLevel : memberLevelList) {
            memberLevel.keep(MemberLevel.COLUMN_MEMBER_LEVEL_ID, MemberLevel.COLUMN_MEMBER_LEVEL_NAME);
        }

        List<ProductSku> productSkuList = new ArrayList<ProductSku>();

        if (Utility.isNullOrEmpty(productMap.getProduct_id())) {
            product = new Product();
        } else {
            product = productDao.findByProduct_id(productMap.getProduct_id());

            List<CategoryAttributeValue> categoryAttributeValueList = categoryAttributeValueService.listByObject_idAndObject_typeAndCategory_id(product.getProduct_id(), CatetoryEnum.PRODUCT.getKey(), product.getCategory_id());

            product.setCategoryAttributeValueList(categoryAttributeValueList);

            productSkuList = productSkuService.listByProduct_id(productMap.getProduct_id());
        }

        for(ProductSku productSku : productSkuList) {
            productSku.keep(ProductSku.COLUMN_PRODUCT_PRICE, ProductSku.COLUMN_PRODUCT_STOCK, ProductSku.COLUMN_PRODUCT_ATTRIBUTE_VALUE, ProductSku.COLUMN_MEMBER_LEVEL_PRICE);
        }

        product.setCategoryList(categoryList);
        product.setBrandList(brandList);
        product.setMemberLevelList(memberLevelList);
        product.setProductSkuList(productSkuList);

        return product;
    }

    public void save(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<ProductSku> productSkuList = productMap.getProductSkuList();

        List<ProductSku> productSkuSaveList = new ArrayList<ProductSku>();

        for (ProductSku productSku : productSkuList) {
            //更新product里面的价格信息
            if (productSku.getProduct_attribute_value().size() == 0) {
                productMap.setProduct_price(productSku.getProduct_price());
                productMap.setProduct_stock(productSku.getProduct_stock());
            }

            productSku.setProduct_id(productMap.getProduct_id());

            productSkuSaveList.add(productSku);
        }

        String product_id = productDao.save(productMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));

        categoryAttributeValueService.saveByObject_idAndObject_typeAndCategoryAttributeList(product_id, CatetoryEnum.PRODUCT.getKey(), productMap.getCategoryAttributeValueList());

        productSkuService.save(product_id, productSkuSaveList, request_user_id);
    }

    public void update(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<ProductSku> pkList = productSkuService.listByProduct_id(productMap.getProduct_id());

        List<ProductSku> productSkuList = productMap.getProductSkuList();

        //更新product里面的基本价格信息
        for (ProductSku productSku : productSkuList) {
            //设置product_id值
            productSku.setProduct_id(productMap.getProduct_id());

            if (productSku.getProduct_attribute_value().size() == 0) {
                productMap.setProduct_price(productSku.getProduct_price());
                productMap.setProduct_stock(productSku.getProduct_stock());
            }
        }

        List<String> productSkuIdDeleteList = new ArrayList<String>();
        List<ProductSku> productSkuSaveList = new ArrayList<ProductSku>();
        List<ProductSku> productSkuUpdateList = new ArrayList<ProductSku>();

        for (ProductSku pk : pkList) {
            //判断商品SKU属性和商品价格是否修改
            Boolean isExit = false;

            for (ProductSku productSku : productSkuList) {
                if (pk.getProduct_attribute_value().toJSONString().equals(productSku.getProduct_attribute_value().toJSONString()) && pk.getProduct_price().compareTo(productSku.getProduct_price()) == 0 && pk.getMember_level_price().toJSONString().equals(productSku.getMember_level_price().toJSONString())) {
                    isExit = true;

                    //判断商品库存是否修改
                    if (!pk.getProduct_stock().equals(productSku.getProduct_stock())) {
                        productSkuUpdateList.add(productSku);
                    }

                    break;
                }
            }

            if (!isExit) {
                productSkuIdDeleteList.add(pk.getProduct_sku_id());
            }
        }

        for (ProductSku productSku : productSkuList) {
            Boolean isExit = false;

            for (ProductSku pk : pkList) {
                if (pk.getProduct_attribute_value().toJSONString().equals(productSku.getProduct_attribute_value().toJSONString()) && pk.getProduct_price().compareTo(productSku.getProduct_price()) == 0 && pk.getMember_level_price().toJSONString().equals(productSku.getMember_level_price().toJSONString())) {
                    isExit = true;

                    break;
                }
            }

            if (!isExit) {
                productSkuSaveList.add(productSku);
            }
        }

        //删除多余的SKU
        productSkuService.delete(productSkuIdDeleteList, request_user_id);
        //新增SKU
        productSkuService.save(productMap.getProduct_id(), productSkuSaveList, request_user_id);
        //更新SKU的库存
        productSkuService.update(productSkuUpdateList, request_user_id);

        productDao.update(productMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));

        categoryAttributeValueService.saveByObject_idAndObject_typeAndCategoryAttributeList(productMap.getProduct_id(), CatetoryEnum.PRODUCT.getKey(), productMap.getCategoryAttributeValueList());
    }

    public void delete(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        productDao.delete(productMap.getProduct_id(), request_user_id);
    }



    public List<Map<String, Object>> getList(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<Product> productList = productDao.listByCategory_id(productMap.getCategory_id(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        return packageList(productList, request_user_id);
    }

    public List<Map<String, Object>> getHotList(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<Product> productList = productDao.listByHot(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        return packageList(productList, request_user_id);
    }

    public List<Map<String, Object>> getBrandList(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<Product> productList = productDao.listByBrand_id(productMap.getBrand_id(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        return packageList(productList, request_user_id);
    }

    public List<Map<String, Object>> getMarketList(JSONObject jsonObject) {
        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<Product> productList = productDao.listByUser_id(request_user_id, Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        return packageList(productList, request_user_id);
    }

    public Map<String, Object> get(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        Product product = productDao.findByProduct_id(productMap.getProduct_id());

        List<ProductSku> productSkuList = productSkuService.listByProduct_id(productMap.getProduct_id());

        Iterator<ProductSku> iterator = productSkuList.iterator();

        ProductSku ps = null;

        List<String> productSkuIdList = new ArrayList<String>();

        //去掉基本价格等信息
        while (iterator.hasNext()) {
            ProductSku productSku = iterator.next();

            productSkuIdList.add(productSku.getProduct_sku_id());

            if (productSku.getProduct_attribute_value().size() == 0) {
                ps = productSku;

                iterator.remove();
            }
        }

        if (ps == null) {
            throw new RuntimeException("该商品基本价格信息不存在");
        }

        List<ProductLockStock> productLockStockList = productLockStockService.listByProductSkuIdList(productSkuIdList);

        if(productLockStockList == null) {
            productLockStockList = new ArrayList<ProductLockStock>();
        }

        List<Map<String, Object>> productAllSkuList = new ArrayList<Map<String, Object>>();
        for (ProductSku productSku : productSkuList) {
            for (int i = 0; i < productSku.getProduct_attribute_value().size(); i++) {
                JSONObject object = productSku.getProduct_attribute_value().getJSONObject(i);

                String attribute_id = object.getString(Attribute.COLUMN_ATTRIBUTE_ID);
                String attribute_name = object.getString(Attribute.KEY_ATTRIBUTE_NAME);
                String attribute_value = object.getString(CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE);

                int index = -1;
                for (int j = 0; j < productAllSkuList.size(); j++) {
                    Map<String, Object> map = productAllSkuList.get(j);

                    if (map.get(Attribute.COLUMN_ATTRIBUTE_ID).equals(attribute_id)) {
                        index = j;

                        break;
                    }
                }

                if (index > -1) {
                    Map<String, Object> map = productAllSkuList.get(index);

                    @SuppressWarnings("unchecked")
                    List<String> array = (List<String>) map.get(CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE);

                    if (!array.contains(attribute_value)) {
                        array.add(attribute_value);
                    }
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(Attribute.COLUMN_ATTRIBUTE_ID, attribute_id);
                    map.put(Attribute.KEY_ATTRIBUTE_NAME, attribute_name);

                    List<String> array = new ArrayList<String>();
                    array.add(attribute_value);

                    map.put(CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE, array);

                    productAllSkuList.add(map);
                }
            }
        }

        List<Map<String, Object>> productSkuStringList = new ArrayList<Map<String, Object>>();
        for (ProductSku productSku : productSkuList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ProductSku.COLUMN_PRODUCT_SKU_ID, productSku.getProduct_sku_id());
            map.put(ProductSku.COLUMN_PRODUCT_PRICE, productSku.getProduct_price());
            map.put(ProductSku.COLUMN_PRODUCT_STOCK, productSku.getProduct_stock());

            //商品库存减去锁定库存数
            for (ProductLockStock productLockStock : productLockStockList) {
                if (productLockStock.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
                    int productStock = productSku.getProduct_stock() - productLockStock.getProduct_lock_stock();

                    if (productStock < 0) {
                        throw new RuntimeException("该商品库存有异常");
                    }
                    map.put(ProductSku.COLUMN_PRODUCT_STOCK, productStock);
                }
            }

            String attribute_value = "";
            for (int i = 0; i < productSku.getProduct_attribute_value().size(); i++) {
                JSONObject object = productSku.getProduct_attribute_value().getJSONObject(i);

                if (i > 0) {
                    attribute_value += "_";
                }

                attribute_value += object.getString(Attribute.COLUMN_ATTRIBUTE_ID) + "_" + object.getString(CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE);
            }

            map.put(ProductSku.COLUMN_PRODUCT_ATTRIBUTE_VALUE, attribute_value);

            productSkuStringList.add(map);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Product.COLUMN_PRODUCT_ID, product.getProduct_id());
        map.put(Product.COLUMN_PRODUCT_NAME, product.getProduct_name());
        map.put(Product.COLUMN_PRODUCT_IMAGE, product.getProduct_image());
        map.put(Product.COLUMN_PRODUCT_SKU_LIST, productSkuStringList);
        map.put(Product.COLUMN_PRODUCT_ALL_SKU_LIST, productAllSkuList);
        map.put(Product.COLUMN_PRODUCT_CONTENT, product.getProduct_content());
        map.put(Product.COLUMN_PRODUCT_PRICE, ps.getProduct_price());
        map.put(Product.COLUMN_PRODUCT_IS_APPLY, brandService.checkIsApply(product.getBrand_id(), request_user_id));

        Brand brand = brandService.findByBrand_id(product.getBrand_id());
        map.put(Brand.COLUMN_BRAND_ID, brand.getBrand_id());
        map.put(Brand.COLUMN_BRAND_NAME, brand.getBrand_name());
        map.put(Brand.COLUMN_BRAND_LOGO, brand.getBrand_logo());
        map.put(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS, BrandApplyReviewEnum.NONE.getKey());

        List<BrandApply> brandApplyList = brandApplyService.listByUser_id(request_user_id);
        for(BrandApply brandApply : brandApplyList) {
            if(brandApply.getBrand_id().equals(brand.getBrand_id())) {
                map.put(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS, brandApply.getBrand_apply_review_status());

                break;
            }
        }

        List<Map<String, Object>> memberPriceList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < ps.getMember_level_price().size(); i++) {
            JSONObject object = ps.getMember_level_price().getJSONObject(i);

            memberPriceList.add(object);
        }
        map.put(Product.COLUMN_MEMBER_LEVEL_LIST, memberPriceList);

        map.put(Product.COLUMN_PRODUCT_STOCK, ps.getProduct_stock());
        map.put(ProductSku.COLUMN_PRODUCT_SKU_ID, ps.getProduct_sku_id());

        //商品库存减去锁定库存数
        for (ProductLockStock productLockStock : productLockStockList) {
            if (productLockStock.getProduct_sku_id().equals(ps.getProduct_sku_id())) {
                int productStock = ps.getProduct_stock() - productLockStock.getProduct_lock_stock();

                if (productStock < 0) {
                    throw new RuntimeException("该商品库存有异常");
                }
                map.put(Product.COLUMN_PRODUCT_STOCK, productStock);
            }
        }

        return map;
    }

    private List<Map<String, Object>> packageList(List<Product> productList, String request_user_id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Product product : productList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Product.COLUMN_PRODUCT_ID, product.getProduct_id());
            map.put(Product.COLUMN_PRODUCT_NAME, product.getProduct_name());
            map.put(Product.COLUMN_PRODUCT_PRICE, product.getProduct_price());
            map.put(Product.COLUMN_PRODUCT_IMAGE, product.getProduct_image().get(0));
            map.put(Product.COLUMN_PRODUCT_IS_APPLY, brandService.checkIsApply(product.getBrand_id(), request_user_id));

            list.add(map);
        }

        return list;
    }

    public Map<String, Object> listCollect(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        Integer count = productCollectService.count();

        List<ProductCollect> productCollectList = productCollectService.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (ProductCollect productCollect : productCollectList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ProductCollect.COLUMN_PRODUCT_COLLECT_ID, productCollect.gettProduct_collect_id());
            map.put(ProductCollect.COLUMN_PRODUCT_ID, productCollect.getProduct_id());
            map.put(Product.COLUMN_PRODUCT_NAME, productCollect.getProduct().getProduct_name());
            map.put(Member.COLUMN_MEMBER_NAME, productCollect.getMember().getMember_name());
            map.put(ProductCollect.COLUMN_SYSTEM_CREATE_TIME, productCollect.getSystem_create_time());

            list.add(map);
        }

        return Utility.setResultMap(count, list);
    }

    public List<Map<String, Object>> getCollectList(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        List<ProductCollect> productCollectList = productCollectService.listByUser_id(request_user_id, Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (ProductCollect productCollect : productCollectList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ProductCollect.COLUMN_PRODUCT_COLLECT_ID, productCollect.gettProduct_collect_id());
            map.put(ProductCollect.COLUMN_PRODUCT_ID, productCollect.getProduct_id());
            map.put(Product.COLUMN_PRODUCT_NAME, productCollect.getProduct().getProduct_name());
            map.put(Product.COLUMN_PRODUCT_PRICE, productCollect.getProduct().getProduct_price());
            map.put(Product.COLUMN_PRODUCT_IMAGE, productCollect.getProduct().getProduct_image().get(0));

            list.add(map);
        }

        return list;
    }

    public void saveCollect(JSONObject jsonObject) {
        ProductCollect productCollect = jsonObject.toJavaObject(ProductCollect.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        productCollectService.save(productCollect, request_user_id);
    }

    public void deleteCollect(JSONObject jsonObject) {
        ProductCollect productCollect = jsonObject.toJavaObject(ProductCollect.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        productCollectService.delete(productCollect.gettProduct_collect_id(), request_user_id);
    }

}

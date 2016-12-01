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
import com.hongluomeng.type.CatetoryEnum;

public class ProductService {

    private ProductDao productDao = new ProductDao();
    private CategoryService categoryService = new CategoryService();
    private BrandService brandService = new BrandService();
    private CategoryAttributeService categoryAttributeService = new CategoryAttributeService();
    private CategoryAttributeValueService categoryAttributeValueService = new CategoryAttributeValueService();
    private MemberLevelService memberLevelService = new MemberLevelService();
    private ProductSkuService productSkuService = new ProductSkuService();
    private ProductLockStockService productLockStockService = new ProductLockStockService();

    public Map<String, Object> list(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        Integer count = productDao.count(productMap.getProduct_name());

        List<Product> productList = productDao.list(productMap.getProduct_name(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Product product : productList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Product.KEY_PRODUCT_ID, product.getProduct_id());
            map.put(Product.KEY_PRODUCT_NAME, product.getProduct_name());
            map.put(Product.KEY_PRODUCT_IS_HOT, product.getProduct_is_hot());
            map.put(Product.KEY_PRODUCT_PRICE, product.getProduct_price());
            map.put(Product.KEY_PRODUCT_STOCK, product.getProduct_stock());

            list.add(map);
        }

        Map<String, Object> resultMap = Utility.setResultMap(count, list);

        return resultMap;
    }

    public Product find(JSONObject jsonObject) {
        Product productMap = jsonObject.toJavaObject(Product.class);

        Product product;

        List<Category> categoryList = categoryService.listByCategory_key(CatetoryEnum.PRODUCT.getKey());

        List<Brand> brandList = brandService.listAll();

        List<MemberLevel> memberLevelList = memberLevelService.listAll();

        List<ProductSku> productSkuList = new ArrayList<ProductSku>();

        if (Utility.isNullOrEmpty(productMap.getProduct_id())) {
            product = new Product();
        } else {
            product = productDao.findByProduct_id(productMap.getProduct_id());

            List<CategoryAttributeValue> categoryAttributeValueList = categoryAttributeValueService.listByObject_idAndObject_typeAndCategory_id(product.getProduct_id(), CatetoryEnum.PRODUCT.getKey(), product.getCategory_id());

            product.setCategoryAttributeValueList(categoryAttributeValueList);

            productSkuList = productSkuService.listByProduct_id(productMap.getProduct_id());
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

    public Map<String, Object> listCategory(JSONObject jsonObject) {
        return categoryService.treeByCategory_key(CatetoryEnum.PRODUCT.getKey());
    }

    public Category findCategory(JSONObject jsonObject) {
        return categoryService.find(jsonObject);
    }

    public void saveCategory(JSONObject jsonObject) {
        categoryService.save(jsonObject);
    }

    public void updateCategory(JSONObject jsonObject) {
        categoryService.update(jsonObject);
    }

    public void deleteCategory(JSONObject jsonObject) {
        categoryService.delete(jsonObject);
    }

    public List<Map<String, Object>> listCategoryAttribute(JSONObject jsonObject) {
        return categoryAttributeService.list(jsonObject);
    }

    public Map<String, Object> findCategoryAttribute(JSONObject jsonObject) {
        return categoryAttributeService.find(jsonObject);
    }

    public void saveCategoryAttribute(JSONObject jsonObject) {
        categoryAttributeService.save(jsonObject);
    }

    public void updateCategoryAttribute(JSONObject jsonObject) {
        categoryAttributeService.update(jsonObject);
    }

    public void deleteCategoryAttribute(JSONObject jsonObject) {
        categoryAttributeService.delete(jsonObject);
    }

    public List<Map<String, Object>> getCategoryList(JSONObject jsonObject) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<Category> categoryList = categoryService.listByCategory_key(CatetoryEnum.PRODUCT.getKey());

        for (Category category : categoryList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Category.KEY_CATEGORY_ID, category.getCategory_id());
            map.put(Category.KEY_CATEGORY_NAME, category.getCategory_name());
            list.add(map);
        }

        return list;
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

        List<Map<String, Object>> productAllSkuList = new ArrayList<Map<String, Object>>();
        for (ProductSku productSku : productSkuList) {
            for (int i = 0; i < productSku.getProduct_attribute_value().size(); i++) {
                JSONObject object = productSku.getProduct_attribute_value().getJSONObject(i);

                String attribute_id = object.getString(Attribute.KEY_ATTRIBUTE_ID);
                String attribute_name = object.getString(Attribute.KEY_ATTRIBUTE_NAME);
                String attribute_value = object.getString(CategoryAttributeValue.KEY_ATTRIBUTE_VALUE);

                int index = -1;
                for (int j = 0; j < productAllSkuList.size(); j++) {
                    Map<String, Object> map = productAllSkuList.get(j);

                    if (map.get(Attribute.KEY_ATTRIBUTE_ID).equals(attribute_id)) {
                        index = j;

                        break;
                    }
                }

                if (index > -1) {
                    Map<String, Object> map = productAllSkuList.get(index);

                    @SuppressWarnings("unchecked")
                    List<String> array = (List<String>) map.get(CategoryAttributeValue.KEY_ATTRIBUTE_VALUE);

                    if (!array.contains(attribute_value)) {
                        array.add(attribute_value);
                    }
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(Attribute.KEY_ATTRIBUTE_ID, attribute_id);
                    map.put(Attribute.KEY_ATTRIBUTE_NAME, attribute_name);

                    List<String> array = new ArrayList<String>();
                    array.add(attribute_value);

                    map.put(CategoryAttributeValue.KEY_ATTRIBUTE_VALUE, array);

                    productAllSkuList.add(map);
                }
            }
        }

        List<Map<String, Object>> productSkuStringList = new ArrayList<Map<String, Object>>();
        for (ProductSku productSku : productSkuList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ProductSku.KEY_PRODUCT_SKU_ID, productSku.getProduct_sku_id());
            map.put(ProductSku.KEY_PRODUCT_PRICE, productSku.getProduct_price());
            map.put(ProductSku.KEY_PRODUCT_STOCK, productSku.getProduct_stock());

            //商品库存减去锁定库存数
            for (ProductLockStock productLockStock : productLockStockList) {
                if (productLockStock.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
                    int productStock = productSku.getProduct_stock() - productLockStock.getProduct_lock_stock();

                    if (productStock < 0) {
                        throw new RuntimeException("该商品库存有异常");
                    }
                    map.put(ProductSku.KEY_PRODUCT_STOCK, productStock);
                }
            }

            String attribute_value = "";
            for (int i = 0; i < productSku.getProduct_attribute_value().size(); i++) {
                JSONObject object = productSku.getProduct_attribute_value().getJSONObject(i);

                if (i > 0) {
                    attribute_value += "_";
                }

                attribute_value += object.getString(Attribute.KEY_ATTRIBUTE_ID) + "_" + object.getString(CategoryAttributeValue.KEY_ATTRIBUTE_VALUE);
            }

            map.put(ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE, attribute_value);

            productSkuStringList.add(map);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Product.KEY_PRODUCT_ID, product.getProduct_id());
        map.put(Product.KEY_PRODUCT_NAME, product.getProduct_name());
        map.put(Product.KEY_PRODUCT_IMAGE, product.getProduct_image());
        map.put(Product.KEY_PRODUCT_SKU_LIST, productSkuStringList);
        map.put(Product.KEY_PRODUCT_ALL_SKU_LIST, productAllSkuList);
        map.put(Product.KEY_PRODUCT_CONTENT, product.getProduct_content());
        map.put(Product.KEY_PRODUCT_PRICE, ps.getProduct_price());
        map.put(Product.KEY_PRODUCT_IS_APPLY, checkIsApply(product.getBrand_id(), request_user_id));

        List<Map<String, Object>> memberPriceList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < ps.getMember_level_price().size(); i++) {
            JSONObject object = ps.getMember_level_price().getJSONObject(i);

            memberPriceList.add(object);
        }
        map.put(Product.KEY_MEMBER_LEVEL_LIST, memberPriceList);

        map.put(Product.KEY_PRODUCT_STOCK, ps.getProduct_stock());
        map.put(ProductSku.KEY_PRODUCT_SKU_ID, ps.getProduct_sku_id());

        //商品库存减去锁定库存数
        for (ProductLockStock productLockStock : productLockStockList) {
            if (productLockStock.getProduct_sku_id().equals(ps.getProduct_sku_id())) {
                int productStock = ps.getProduct_stock() - productLockStock.getProduct_lock_stock();

                if (productStock < 0) {
                    throw new RuntimeException("该商品库存有异常");
                }
                map.put(Product.KEY_PRODUCT_STOCK, productStock);
            }
        }

        return map;
    }

    private List<Map<String, Object>> packageList(List<Product> productList, String request_user_id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Product product : productList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Product.KEY_PRODUCT_ID, product.getProduct_id());
            map.put(Product.KEY_PRODUCT_NAME, product.getProduct_name());
            map.put(Product.KEY_PRODUCT_PRICE, product.getProduct_price());
            map.put(Product.KEY_PRODUCT_IMAGE, product.getProduct_image().get(0));
            map.put(Product.KEY_PRODUCT_IS_APPLY, checkIsApply(product.getBrand_id(), request_user_id));

            list.add(map);
        }

        return list;
    }

    public Boolean checkIsApply(String brand_id, String request_user_id) {
        Boolean result = false;

        List<Brand> brandList = brandService.listByUser_idForMyListFromCache(request_user_id);

        for (Brand brand : brandList) {
            if (brand.getBrand_id().equals(brand_id)) {
                result = true;

                break;
            }
        }

        return result;
    }

}

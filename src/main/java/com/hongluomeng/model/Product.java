package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.List;

import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.alibaba.fastjson.JSONArray;

public class Product extends Base<Product> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_PRODUCT = "table_product";
	public static final String COLUMN_PRODUCT_ID = "product_id";
	public static final String COLUMN_CATEGORY_ID = "category_id";
	public static final String COLUMN_BRAND_ID = "brand_id";
	public static final String COLUMN_PRODUCT_NAME = "product_name";
	public static final String COLUMN_PRODUCT_IMAGE = "product_image";
	public static final String COLUMN_PRODUCT_PRICE = "product_price";
	public static final String COLUMN_PRODUCT_STOCK = "product_stock";
	public static final String COLUMN_PRODUCT_IS_NEW = "product_is_new";
	public static final String COLUMN_PRODUCT_IS_RECOMMEND = "product_is_recommend";
	public static final String COLUMN_PRODUCT_IS_BARGAIN = "product_is_bargain";
	public static final String COLUMN_PRODUCT_IS_HOT = "product_is_hot";
	public static final String COLUMN_PRODUCT_IS_SELL_OUT = "product_is_sell_out";
	public static final String COLUMN_PRODUCT_IS_SALE = "product_is_sale";
	public static final String COLUMN_PRODUCT_CONTENT = "product_content";
	public static final String COLUMN_PRODUCT_SKU_VALUE = "product_sku_value";;
	public static final String COLUMN_MEMBER_LEVEL_LIST = "memberLevelList";
	public static final String COLUMN_PRODUCT_SKU_LIST = "productSkuList";
	public static final String COLUMN_PRODUCT_ALL_SKU_LIST = "productAllSkuList";
    public static final String KEY_PRODUCT_IS_APPLY = "product_is_apply";
    public static final String KEY_PRODUCT_IS_COLLECT = "product_is_collect";
    public static final String KEY_PRODUCT_URL = "product_url";

	private List<Category> categoryList;
	private List<Brand> brandList;
	private List<CategoryAttributeValue> categoryAttributeValueList;
	private List<MemberLevel> memberLevelList;
	private List<ProductSku> productSkuList;

	public String getProduct_id() {
		return getStr(COLUMN_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(COLUMN_PRODUCT_ID, product_id);
	}

	public void checkProduct_id() {
		Utility.checkStringLength(getProduct_id(), 32, "商品编号");
	}

	public String getCategory_id() {
		return getStr(COLUMN_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(COLUMN_CATEGORY_ID, category_id);
	}

	public void checkCategory_id() {
		Utility.checkStringLength(getCategory_id(), 32, "分类编号");
	}

	public String getBrand_id() {
		return getStr(COLUMN_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(COLUMN_BRAND_ID, brand_id);
	}

	public void checkBrand_id() {
		Utility.checkStringLength(getBrand_id(), 32, "品牌编号");
	}

	public String getProduct_name() {
		return getStr(COLUMN_PRODUCT_NAME);
	}

	public void setProduct_name(String product_name) {
		set(COLUMN_PRODUCT_NAME, product_name);
	}

	public void checkProduct_name() {
		Utility.checkStringLength(getProduct_name(), 2, 255, "商品名称");
	}

	public JSONArray getProduct_image() {
        if(Utility.isNullOrEmpty(getStr(COLUMN_PRODUCT_IMAGE))) {
            return JSONArray.parseArray(Const.ARRAY_EMPTY);
        } else {
            return JSONArray.parseArray(getStr(COLUMN_PRODUCT_IMAGE));
        }
	}

	public void setProduct_image(String product_image) {
		set(COLUMN_PRODUCT_IMAGE, product_image);
	}

	public void checkProduct_image() {
		Utility.checkStringLength(getProduct_image().toJSONString(), 0, 1000, "商品图片");
	}

	public BigDecimal getProduct_price() {
		return getBigDecimal(COLUMN_PRODUCT_PRICE);
	}

	public void setProduct_price(BigDecimal product_price) {
		set(COLUMN_PRODUCT_PRICE, product_price);
	}

	public void checkProduct_price() {
		Utility.checkDecimalLength(getProduct_price(), 11, 2, "商品价格");
	}

	public Integer getProduct_stock() {
		return Utility.getIntegerValue(get(COLUMN_PRODUCT_STOCK));
	}

	public void setProduct_stock(Integer product_stock) {
		set(COLUMN_PRODUCT_STOCK, product_stock);
	}

	public void checkProduct_stock() {
		Utility.checkIntegerLength(getProduct_stock(), 1, 11, "商品库存");
	}

	public Boolean getProduct_is_new() {
		return getBoolean(COLUMN_PRODUCT_IS_NEW);
	}

	public void setProduct_is_new(Boolean product_is_new) {
		set(COLUMN_PRODUCT_IS_NEW, product_is_new);
	}

	public void checkProduct_is_new() {
		Utility.checkNullOrEmpty(getProduct_is_new(), "是否新品");
	}

	public Boolean getProduct_is_recommend() {
		return getBoolean(COLUMN_PRODUCT_IS_RECOMMEND);
	}

	public void setProduct_is_recommend(Boolean product_is_recommend) {
		set(COLUMN_PRODUCT_IS_RECOMMEND, product_is_recommend);
	}

	public void checkProduct_is_recommend() {
		Utility.checkNullOrEmpty(getProduct_is_recommend(), "是否推荐");
	}

	public Boolean getProduct_is_bargain() {
		return getBoolean(COLUMN_PRODUCT_IS_BARGAIN);
	}

	public void setProduct_is_bargain(Boolean product_is_bargain) {
		set(COLUMN_PRODUCT_IS_BARGAIN, product_is_bargain);
	}

	public void checkProduct_is_bargain() {
		Utility.checkNullOrEmpty(getProduct_is_bargain(), "是否特价");
	}

	public Boolean getProduct_is_hot() {
		return getBoolean(COLUMN_PRODUCT_IS_HOT);
	}

	public void setProduct_is_hot(Boolean product_is_hot) {
		set(COLUMN_PRODUCT_IS_HOT, product_is_hot);
	}

	public void checkProduct_is_hot() {
		Utility.checkNullOrEmpty(getProduct_is_hot(), "是否热销");
	}

	public Boolean getProduct_is_sell_out() {
		return getBoolean(COLUMN_PRODUCT_IS_SELL_OUT);
	}

	public void setProduct_is_sell_out(Boolean product_is_sell_out) {
		set(COLUMN_PRODUCT_IS_SELL_OUT, product_is_sell_out);
	}

	public void checkProduct_is_sell_out() {
		Utility.checkNullOrEmpty(getProduct_is_sell_out(), "是否买完");
	}

	public Boolean getProduct_is_sale() {
		return getBoolean(COLUMN_PRODUCT_IS_SALE);
	}

	public void setProduct_is_sale(Boolean product_is_sale) {
		set(COLUMN_PRODUCT_IS_SALE, product_is_sale);
	}

	public void checkProduct_is_sale() {
		Utility.checkNullOrEmpty(getProduct_is_sale(), "是否上架");
	}

	public String getProduct_content() {
		return getStr(COLUMN_PRODUCT_CONTENT);
	}

	public void checkProduct_content() {
		Utility.checkNull(getProduct_content(), "商品介绍");
	}

	public void setProduct_content(String product_content) {
		set(COLUMN_PRODUCT_CONTENT, product_content);
	}

	public JSONArray getProduct_sku_value() {
		return JSONArray.parseArray(getStr(COLUMN_PRODUCT_SKU_VALUE));
	}

	public void setProduct_sku_value(String product_sku_value) {
		if(Utility.isNullOrEmpty(product_sku_value)) {
			product_sku_value = "[]";
		}
		set(COLUMN_PRODUCT_SKU_VALUE, product_sku_value);
	}

	public void checkProduct_sku_value() {
		Utility.checkStringLength(getProduct_sku_value().toJSONString(), 0, 1000, "商品SKU值");
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public List<CategoryAttributeValue> getCategoryAttributeValueList() {
		return categoryAttributeValueList;
	}

	public void setCategoryAttributeValueList(List<CategoryAttributeValue> categoryAttributeValueList) {
		this.categoryAttributeValueList = categoryAttributeValueList;
	}

	public List<MemberLevel> getMemberLevelList() {
		return memberLevelList;
	}

	public void setMemberLevelList(List<MemberLevel> memberLevelList) {
		this.memberLevelList = memberLevelList;
	}

	public List<ProductSku> getProductSkuList() {
		return productSkuList;
	}

	public void setProductSkuList(List<ProductSku> productSkuList) {
		this.productSkuList = productSkuList;
	}

}

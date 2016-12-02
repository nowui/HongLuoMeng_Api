package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.service.CategoryService;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Product;
import com.hongluomeng.service.ProductService;

public class ProductController extends BaseController {

	private ProductService productService = new ProductService();
	private CategoryService categoryService = new CategoryService();

	@ActionKey(Url.URL_PRODUCT_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Product productValidator = jsonObject.toJavaObject(Product.class);

		Utility.checkNull(productValidator.getProduct_name(), "商品名称");

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = productService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_PRODUCT_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Product produc = productService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(produc));
	}

	@ActionKey(Url.URL_PRODUCT_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Product productValidator = productService.find(jsonObject);

		productValidator.checkCategory_id();

		productValidator.checkBrand_id();

		productValidator.checkProduct_name();

		productValidator.checkProduct_is_new();

		productValidator.checkProduct_is_recommend();

		productValidator.checkProduct_is_bargain();

		productValidator.checkProduct_is_hot();

		productValidator.checkProduct_is_sell_out();

		productValidator.checkProduct_is_sale();

		productValidator.checkProduct_content();

		productService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Product productValidator = productService.find(jsonObject);

		productValidator.checkProduct_id();

		productValidator.checkCategory_id();

		productValidator.checkBrand_id();

		productValidator.checkProduct_name();

		productValidator.checkProduct_is_new();

		productValidator.checkProduct_is_recommend();

		productValidator.checkProduct_is_bargain();

		productValidator.checkProduct_is_hot();

		productValidator.checkProduct_is_sell_out();

		productValidator.checkProduct_is_sale();

		productValidator.checkProduct_content();

		productService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Product productValidator = productService.find(jsonObject);

		productValidator.checkProduct_id();

		productService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORY_LIST)
	public void listCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = productService.listCategory(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORY_FIND)
	public void findCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_id();

		Category category = categoryService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(category));
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORY_SAVE)
	public void saveCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkParent_id();

		categoryValidator.checkCategory_name();

		categoryValidator.checkCategory_sort();

		categoryService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORYT_UPDATE)
	public void updateCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_id();

		categoryValidator.checkCategory_name();

		categoryValidator.checkCategory_sort();

		productService.updateCategory(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORYT_DELETE)
	public void deleteCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_id();

		productService.deleteCategory(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_LIST)
	public void listCategoryAttribute() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		CategoryAttribute categoryAttributeValidator = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeValidator.checkCategory_id();

		List<Map<String, Object>> resultList = productService.listCategoryAttribute(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_FIND)
	public void findCategoryAttribute() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		CategoryAttribute categoryAttributeValidator = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeValidator.checkCategory_id();

		Map<String, Object> resultMap = productService.findCategoryAttribute(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_SAVE)
	public void saveCategoryAttribute() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		CategoryAttribute categoryAttributeValidator = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeValidator.checkCategory_id();

		categoryAttributeValidator.checkAttribute_id();

		categoryAttributeValidator.checkCategory_attribute_sort();

		productService.saveCategoryAttribute(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORYT_ATTRIBUTE_UPDATE)
	public void updateCategoryAttribute() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		CategoryAttribute categoryAttributeValidator = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeValidator.checkCategory_id();

		categoryAttributeValidator.checkAttribute_id();

		categoryAttributeValidator.checkCategory_attribute_sort();

		productService.updateCategoryAttribute(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORYT_ATTRIBUTE_DELETE)
	public void deleteCategoryAttribute() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		CategoryAttribute categoryAttributeValidator = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeValidator.checkCategory_id();

		categoryAttributeValidator.checkAttribute_id();

		productService.deleteCategoryAttribute(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PRODUCT_CATEGORY_LIST_GET)
	public void getCategoryList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = productService.getCategoryList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_PRODUCT_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		Utility.checkNull(categoryValidator.getCategory_id(), "分类编号");

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = productService.getList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_PRODUCT_HOT_LIST_GET)
	public void getHotList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = productService.getHotList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_PRODUCT_BRAND_LIST_GET)
	public void getBrandList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Product productValidator = jsonObject.toJavaObject(Product.class);

		productValidator.checkBrand_id();

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = productService.getBrandList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_PRODUCT_MARKET_LIST_GET)
	public void getMarketList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = productService.getMarketList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_PRODUCT_GET)
	public void get() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Product productValidator = jsonObject.toJavaObject(Product.class);

		productValidator.checkProduct_id();

		Map<String, Object> resultMap = productService.get(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

}

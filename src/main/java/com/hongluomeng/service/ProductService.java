package com.hongluomeng.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.ProductDao;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.Product;
import com.hongluomeng.type.CatetoryEnum;

public class ProductService {

	private ProductDao productDao = new ProductDao();
	private CategoryService categoryService = new CategoryService();
	private BrandService brandService = new BrandService();
	private CategoryAttributeService categoryAttributeService = new CategoryAttributeService();
	private ProductAttributeService productAttributeService = new ProductAttributeService();

	public Integer count(JSONObject jsonObject) {
		//Product productMap = jsonObject.toJavaObject(Product.class);

		return productDao.count();
	}

	public List<Product> list(JSONObject jsonObject) {
		//Product productMap = jsonObject.toJavaObject(Product.class);

		return productDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public Product find(JSONObject jsonObject) {
		Product productMap = jsonObject.toJavaObject(Product.class);

		Product product;

		List<Category> categorieList = categoryService.listByCategory_key(CatetoryEnum.PRODUCT.getKey());
		List<Brand> brandList = brandService.listByUser_id(jsonObject.getString(Const.KEY_REQUEST_USER_ID));

		if (Utility.isNullOrEmpty(productMap.getProduct_id())) {
			product = new Product();
			product.setProduct_price(BigDecimal.valueOf(0.00));
			product.setProduct_stock(0);
			product.setProduct_image("[]");
			product.setCategoryAttributeList(new ArrayList<CategoryAttribute>());
		} else {
			product = productDao.findByProduct_id(productMap.getProduct_id());

			List<CategoryAttribute> categoryAttributeList = categoryAttributeService.listByProduct_idAndCategory_id(product.getProduct_id(), product.getCategory_id());

			product.setCategoryAttributeList(categoryAttributeList);
		}

		product.setCategoryList(categorieList);
		product.setBrandList(brandList);

		return product;
	}

	public void save(JSONObject jsonObject) {
		Product productMap = jsonObject.toJavaObject(Product.class);

		String product_id = productDao.save(productMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));

		productAttributeService.saveByProduct_idAndCategory_Attribute(product_id, productMap.getCategoryAttributeList());
	}

	public void update(JSONObject jsonObject) {
		Product productMap = jsonObject.toJavaObject(Product.class);

		productDao.update(productMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));

		productAttributeService.saveByProduct_idAndCategory_Attribute(productMap.getProduct_id(), productMap.getCategoryAttributeList());
	}

	public void delete(JSONObject jsonObject) {
		Product productMap = jsonObject.toJavaObject(Product.class);

		productDao.delete(productMap);
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

	public List<CategoryAttribute> listCategoryAttribute(JSONObject jsonObject) {
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

}

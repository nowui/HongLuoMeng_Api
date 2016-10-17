package com.hongluomeng.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.ProductDao;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.MemberLevel;
import com.hongluomeng.model.Product;
import com.hongluomeng.model.ProductSku;
import com.hongluomeng.type.CatetoryEnum;

public class ProductService {

	private ProductDao productDao = new ProductDao();
	private CategoryService categoryService = new CategoryService();
	private BrandService brandService = new BrandService();
	private CategoryAttributeService categoryAttributeService = new CategoryAttributeService();
	private ProductAttributeService productAttributeService = new ProductAttributeService();
	private MemberLevelService memberLevelService = new MemberLevelService();
	private ProductSkuService productSkuService = new ProductSkuService();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Product productMap = jsonObject.toJavaObject(Product.class);

		Integer count = productDao.count();

		List<Product> productList = productDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, productList);

		return resultMap;
	}

	public Product find(JSONObject jsonObject) {
		Product productMap = jsonObject.toJavaObject(Product.class);

		Product product;

		List<Category> categoryList = categoryService.listByCategory_key(CatetoryEnum.PRODUCT.getKey());

		List<Brand> brandList = brandService.listByUser_id(jsonObject.getString(Const.KEY_REQUEST_USER_ID));

		List<MemberLevel> memberLevelList = memberLevelService.listAll();

		List<ProductSku> productSkuList = productSkuService.listByProduct_id(productMap.getProduct_id());

		for(MemberLevel memberLevel : memberLevelList) {
			memberLevel.setMember_level_price(BigDecimal.valueOf(0.01));
		}

		if (Utility.isNullOrEmpty(productMap.getProduct_id())) {
			product = new Product();
		} else {
			product = productDao.findByProduct_id(productMap.getProduct_id());

			List<CategoryAttribute> categoryAttributeList = categoryAttributeService.listByProduct_idAndCategory_id(product.getProduct_id(), product.getCategory_id());

			product.setCategoryAttributeList(categoryAttributeList);
		}

		product.setCategoryList(categoryList);
		product.setBrandList(brandList);
		product.setMemberLevelList(memberLevelList);
		product.setProductSkuList(productSkuList);

		return product;
	}

	public void save(JSONObject jsonObject) {
		Product productMap = jsonObject.toJavaObject(Product.class);

		String product_id = productDao.save(productMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));

		productAttributeService.saveByProduct_idAndCategory_Attribute(product_id, productMap.getCategoryAttributeList());
	}

	public void update(JSONObject jsonObject) {
		Product productMap = jsonObject.toJavaObject(Product.class);

		//productDao.update(productMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));

		//productAttributeService.saveByProduct_idAndCategory_Attribute(productMap.getProduct_id(), productMap.getCategoryAttributeList());

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<ProductSku> pkList = productSkuService.listByProduct_id(productMap.getProduct_id());

		List<ProductSku> productSkuList = productMap.getProductSkuList();

		for(ProductSku pk : pkList) {
			Boolean isExit = false;

			for(ProductSku productSku : productSkuList) {
				if(pk.getProduct_attribute_value().equals(productSku.getProduct_attribute_value()) && pk.getProduct_price().equals(productSku.getProduct_price()) && pk.getMember_level_price().equals(productSku.getMember_level_price())) {
					isExit = true;

					break;
				}
			}

			if(! isExit) {
				productSkuService.delete(pk.getProduct_sku_id(), request_user_id);
			}
		}

		for(ProductSku productSku : productSkuList) {
			Boolean isExit = false;

			for(ProductSku pk : pkList) {
				if(pk.getProduct_attribute_value().equals(productSku.getProduct_attribute_value()) && pk.getProduct_price().equals(productSku.getProduct_price()) && pk.getMember_level_price().equals(productSku.getMember_level_price())) {
					isExit = true;

					break;
				}
			}

			if(! isExit) {
				productSku.setProduct_id(productMap.getProduct_id());

				productSkuService.save(productSku, request_user_id);
			}
		}
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

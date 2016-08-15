package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.ProductAttributeDao;
import com.hongluomeng.model.CategoryAttribute;

public class ProductAttributeService {

	private ProductAttributeDao productAttributeDao = new ProductAttributeDao();

	/*public List<ProductAttribute> listByProduct_id(String product_id) {
		return productAttributeDao.listByProduct_id(product_id);
	}*/

	public void saveByProduct_idAndCategory_Attribute(String product_id, List<CategoryAttribute> categoryAttributeList) {
		productAttributeDao.deleteByProduct_id(product_id);

		productAttributeDao.saveByProduct_idAndCategory_Attribute(product_id, categoryAttributeList);
	}

	/*public void deleteByProduct_id(List<ProductAttribute> productAttributeList, String product_id) {
		productAttributeDao.deleteByProduct_id(productAttributeList, product_id);
	}*/

	public void deleteByAttribute_id(String attribute_id) {
		productAttributeDao.deleteByAttribute_id(attribute_id);
	}

}

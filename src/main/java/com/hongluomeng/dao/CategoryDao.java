package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;

public class CategoryDao {

	private Integer count(Category category) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Category.KEY_TABLE_CATEGORY + " ");
		dynamicSQL.append("WHERE " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Category category = new Category();

		return count(category);
	}

	public Integer countByCategory_keyNotEqualCategory_id(String category_id, String category_key) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Category.KEY_TABLE_CATEGORY + " ");
		dynamicSQL.append("WHERE " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.append("AND " + Category.KEY_CATEGORY_ID + " != ? ", category_id);
		dynamicSQL.append("AND " + Category.KEY_CATEGORY_KEY + " = ? ", category_key);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	private List<Category> list(Category category) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Category.KEY_TABLE_CATEGORY + " ");
		dynamicSQL.append("WHERE " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmptyForLike("AND " + Category.KEY_CATEGORY_PATH + " LIKE ? ", category.getCategory_path());
		dynamicSQL.append("ORDER BY " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_SORT + " ASC ");

		return new Category().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Category> list() {
		Category category = new Category();

		return list(category);
	}

	public List<Category> listByCategory_path(String category_path) {
		Category category = new Category();
		category.setCategory_path(category_path);

		category.checkCategory_path();

		return list(category);
	}

	public List<Category> listTopCategory() {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Category.KEY_TABLE_CATEGORY + " WHERE " + Category.KEY_PARENT_ID + " = '' ");
		dynamicSQL.append("AND " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_SORT + " ASC ");

		return new Category().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	private Category find(Category category) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Category.KEY_TABLE_CATEGORY + " ");
		dynamicSQL.append("WHERE " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Category.KEY_CATEGORY_ID + " = ? ", category.getCategory_id());
		dynamicSQL.isNullOrEmpty("AND " + Category.KEY_CATEGORY_KEY + " = ? ", category.getCategory_key());

		List<Category> categoryList = new Category().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(categoryList.size() == 0) {
			return null;
		} else {
			return categoryList.get(0);
		}
	}

	public Category findByCategory_id(String category_id) {
		Category category = new Category();
		category.setCategory_id(category_id);

		category.checkCategory_id();

		return find(category);
	}

	public Category findByCategory_key(String category_key) {
		Category category = new Category();
		category.setCategory_key(category_key);

		category.checkCategory_key();

		return find(category);
	}

	public Category save(Category category, String request_user_id) {
		category.setCategory_id(Utility.getUUID());

		category.initForSave(request_user_id);

		category.save();

		return category;
	}

	public void update(Category category, String request_user_id) {
		category.remove(Category.KEY_PARENT_ID);
		category.remove(Category.KEY_CATEGORY_PATH);

		category.initForUpdate(request_user_id);

		category.update();
	}

	public void deleteByCategory_id(String category_id, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + Category.KEY_TABLE_CATEGORY + " ");
		dynamicSQL.append("SET " + Category.KEY_SYSTEM_STATUS + " = 0, ");
		dynamicSQL.append(Category.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(Category.KEY_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + Category.KEY_CATEGORY_ID + " = ? ", category_id);
		dynamicSQL.isNullOrEmptyForLike("OR " + Category.KEY_CATEGORY_PATH + " LIKE ? ", category_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}

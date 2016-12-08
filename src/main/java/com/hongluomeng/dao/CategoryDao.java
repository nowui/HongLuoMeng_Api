package com.hongluomeng.dao;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;

public class CategoryDao extends BaseDao {

	private Integer count(Category category) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Category.TABLE_CATEGORY + " ");
		myDynamicSQL.append("WHERE " + Category.TABLE_CATEGORY + "." + Category.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Category category = new Category();

		return count(category);
	}

	public Integer countByCategory_keyNotEqualCategory_id(String category_id, String category_key) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Category.TABLE_CATEGORY + " ");
		myDynamicSQL.append("WHERE " + Category.TABLE_CATEGORY + "." + Category.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND " + Category.COLUMN_CATEGORY_ID + " != ? ", category_id);
		myDynamicSQL.append("AND " + Category.COLUMN_CATEGORY_KEY + " = ? ", category_key);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	private List<Category> list(Category category) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Category.TABLE_CATEGORY + " ");
		myDynamicSQL.append("WHERE " + Category.TABLE_CATEGORY + "." + Category.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmptyForLike("AND " + Category.COLUMN_CATEGORY_PATH + " LIKE ? ", category.getCategory_path());
		myDynamicSQL.append("ORDER BY " + Category.TABLE_CATEGORY + "." + Category.COLUMN_CATEGORY_SORT + " ASC ");

		return new Category().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Category.TABLE_CATEGORY + " WHERE " + Category.COLUMN_PARENT_ID + " = '' ");
		myDynamicSQL.append("AND " + Category.TABLE_CATEGORY + "." + Category.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + Category.TABLE_CATEGORY + "." + Category.COLUMN_CATEGORY_SORT + " ASC ");

		return new Category().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	private Category find(Category category) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Category.TABLE_CATEGORY + " ");
		myDynamicSQL.append("WHERE " + Category.TABLE_CATEGORY + "." + Category.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Category.COLUMN_CATEGORY_ID + " = ? ", category.getCategory_id());
		myDynamicSQL.isNullOrEmpty("AND " + Category.COLUMN_CATEGORY_KEY + " = ? ", category.getCategory_key());

		List<Category> categoryList = new Category().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
		category.remove(Category.COLUMN_PARENT_ID);
		category.remove(Category.COLUMN_CATEGORY_PATH);

		category.initForUpdate(request_user_id);

		category.update();
	}

	public void deleteByCategory_id(String category_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + Category.TABLE_CATEGORY + " ");
		myDynamicSQL.append("SET " + Category.COLUMN_SYSTEM_STATUS + " = 0, ");
		myDynamicSQL.append(Category.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(Category.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + Category.COLUMN_CATEGORY_ID + " = ? ", category_id);
		myDynamicSQL.isNullOrEmptyForLike("OR " + Category.COLUMN_CATEGORY_PATH + " LIKE ? ", category_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}

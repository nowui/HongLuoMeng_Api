package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;

public class CategoryDao {

	private Integer count(Category category) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Category.KEY_CATEGORY + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(category.getCategory_key())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append("(" + Category.KEY_CATEGORY_ID + " != ? AND " + Category.KEY_CATEGORY_KEY + " = ? ) ");
			parameterList.add(category.getCategory_id());
			parameterList.add(category.getCategory_key());

			isExit = true;
		}

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Category category = new Category();

		return count(category);
	}

	public Integer countByCategory_idAndCategory_key(String category_id, String category_key) {
		Category category = new Category();
		category.setCategory_key(category_key);

		return count(category);
	}

	private List<Category> list(Category category) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Category.KEY_CATEGORY + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(category.getCategory_path())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Category.KEY_CATEGORY_PATH + " LIKE ? ");
			parameterList.add("%'" + category.getCategory_path() + "'%");

			isExit = true;
		}

		sql.append("ORDER BY " + Category.KEY_CATEGORY_SORT + " ASC ");

		List<Category> categoryList = category.find(sql.toString(), parameterList.toArray());
		return categoryList;
	}

	public List<Category> list() {
		Category category = new Category();

		return list(category);
	}

	public List<Category> listByCategory_path(String category_path) {
		Category category = new Category();
		category.setCategory_path(category_path);

		return list(category);
	}

	public List<Category> listTopCategory() {
		Category category = new Category();
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Category.KEY_CATEGORY + " WHERE " + Category.KEY_PARENT_ID + " = '' ");

		List<Category> categoryList = category.find(sql.toString(), parameterList.toArray());
		return categoryList;
	}

	/*public List<Category> listByParentKey(String parent_key) {
		List<Object> parameterList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT * FROM " + Category.KEY_CATEGORY + " WHERE " + Category.KEY_CATEGORY_PATH + " LIKE (SELECT CONCAT('%\\'', " + Category.KEY_CATEGORY_ID + ", '\\'%') FROM " + Category.KEY_CATEGORY + " WHERE " + Category.KEY_CATEGORY_KEY + " = ?) ");
		parameterList.add(parent_key);

		List<Category> categoryList = new Category().find(sql.toString(), parameterList.toArray());
		return categoryList;
	}*/

	private Category find(Category category) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Category.KEY_CATEGORY + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(category.getCategory_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Category.KEY_CATEGORY_ID + " = ? ");
			parameterList.add(category.getCategory_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(category.getCategory_key())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Category.KEY_CATEGORY_KEY + " = ? ");
			parameterList.add(category.getCategory_key());

			isExit = true;
		}

		if(! isExit) {
			return null;
		}

		List<Category> categoryList = category.find(sql.toString(), parameterList.toArray());
		if(categoryList.size() == 0) {
			return null;
		} else {
			return categoryList.get(0);
		}
	}

	public Category findByCategory_id(String category_id) {
		Category category = new Category();
		category.setCategory_id(category_id);

		return find(category);
	}

	public Category findByCategory_key(String category_key) {
		Category category = new Category();
		category.setCategory_key(category_key);

		return find(category);
	}

	public void save(Category category, String user_id) {
		category.setCategory_id(Utility.getUUID());
		category.setCategory_create_user_id(user_id);
		category.setCategory_create_time(new Date());
		category.setCategory_update_user_id(user_id);
		category.setCategory_update_time(new Date());

		category.save();
	}

	public void update(Category category, String user_id) {
		category.remove(Category.KEY_PARENT_ID);
		category.remove(Category.KEY_CATEGORY_PATH);
		category.remove(Category.KEY_CATEGORY_CREATE_USER_ID);
		category.remove(Category.KEY_CATEGORY_CREATE_TIME);
		category.setCategory_update_user_id(user_id);
		category.setCategory_update_time(new Date());

		category.update();
	}

	public void deleteByCategory_id(String category_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + Category.KEY_CATEGORY + " WHERE ");

		sql.append(Category.KEY_CATEGORY_ID + " = ? ");
		parameterList.add(category_id);

		sql.append("OR " + Category.KEY_CATEGORY_PATH + " LIKE ? ");
		parameterList.add("%'" + category_id + "'%");

		Db.update(sql.toString(), parameterList.toArray());
	}

}

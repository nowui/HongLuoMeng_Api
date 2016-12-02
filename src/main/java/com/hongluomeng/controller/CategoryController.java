package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.service.CategoryService;
import com.hongluomeng.type.CatetoryEnum;

public class CategoryController extends BaseController {

	private CategoryService categoryService = new CategoryService();

	@ActionKey(Url.URL_CATEGORY_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = categoryService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_CATEGORY_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_id();

		Category category = categoryService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(category));
	}

	@ActionKey(Url.URL_CATEGORY_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = jsonObject.toJavaObject(Category.class);

		category.checkCategory_name();

		category.checkCategory_sort();

		categoryService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_CATEGORY_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = jsonObject.toJavaObject(Category.class);

		category.checkCategory_id();

		category.checkCategory_name();

		category.checkCategory_sort();

		categoryService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_CATEGORY_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = jsonObject.toJavaObject(Category.class);

		category.checkCategory_id();

		categoryService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_CATEGORY_CHINA)
	public void china() {
		Map<String, Object> resultMap = categoryService.treeByCategory_key(CatetoryEnum.CHINA.getKey());

		renderJson(Utility.setSuccessResponse(resultMap));
	}

}

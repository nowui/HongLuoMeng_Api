package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Url;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.service.CategoryService;
import com.hongluomeng.type.CatetoryEnum;
import com.jfinal.core.ActionKey;

public class GroupController extends BaseController {

	private CategoryService categoryService = new CategoryService();

	@ActionKey(Url.URL_GROUP_LIST)
	public void list() {
		Map<String, Object> resultMap = categoryService.treeByCategory_key(CatetoryEnum.GROUP.getKey());

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_GROUP_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_id();

		Category category = categoryService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(category));
	}

	@ActionKey(Url.URL_GROUP_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_name();

		categoryValidator.checkCategory_sort();

		categoryService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_GROUP_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_id();

		categoryValidator.checkCategory_name();

		categoryValidator.checkCategory_sort();

		categoryService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_GROUP_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		categoryValidator.checkCategory_id();

		categoryService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

}

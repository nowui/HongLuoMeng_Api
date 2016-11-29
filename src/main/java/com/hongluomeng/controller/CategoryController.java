package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.service.CategoryService;
import com.hongluomeng.type.CatetoryEnum;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.CategoryValidator;

@Before(CategoryValidator.class)
public class CategoryController extends BaseController {

	private CategoryService categoryService = new CategoryService();

	@ActionKey(Url.URL_CATEGORY_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = categoryService.list(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

	@ActionKey(Url.URL_CATEGORY_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = categoryService.find(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", category));
	}

	@ActionKey(Url.URL_CATEGORY_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.save(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_CATEGORY_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.update(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_CATEGORY_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.delete(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_CATEGORY_CHINA)
	public void china() {
		Map<String, Object> map = categoryService.treeByCategory_key(CatetoryEnum.CHINA.getKey());

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

}

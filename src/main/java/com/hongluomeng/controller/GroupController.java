package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.service.CategoryService;
import com.hongluomeng.type.CatetoryEnum;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.GroupValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;

public class GroupController extends BaseController {

	private CategoryService categoryService = new CategoryService();

	@Before(GroupValidator.class)
	@ActionKey(Const.URL_GROUP_LIST)
	public void list() {
		Map<String, Object> resultMap = categoryService.treeByCategory_key(CatetoryEnum.GROUP.getKey());

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(GroupValidator.class)
	@ActionKey(Const.URL_GROUP_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = categoryService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", category));
    }

	@Before(GroupValidator.class)
	@ActionKey(Const.URL_GROUP_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(GroupValidator.class)
	@ActionKey(Const.URL_GROUP_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(GroupValidator.class)
	@ActionKey(Const.URL_GROUP_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}

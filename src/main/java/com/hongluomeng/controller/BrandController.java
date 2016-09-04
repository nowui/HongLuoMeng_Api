package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.service.BrandService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.BrandValidator;

public class BrandController extends BaseController {

	private BrandService brandService = new BrandService();

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = brandService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brand = brandService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", brand));
    }

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}

package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Category;
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

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_CATEGORY_LIST)
	public void listCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = brandService.listCategory(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_CATEGORY_FIND)
	public void findCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = brandService.findCategory(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", category));
    }

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_CATEGORY_SAVE)
	public void saveCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.saveCategory(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_CATEGORYT_UPDATE)
	public void updateCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.updateCategory(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_CATEGORYT_DELETE)
	public void deleteCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.deleteCategory(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_CATEGORY_LIST_GET)
	public void getCategoryList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = brandService.getCategoryList(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultList));
    }

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = brandService.getList(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultList));
    }

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_GET)
	public void get() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brand = brandService.get(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", brand));
    }

	@Before(BrandValidator.class)
	@ActionKey(Const.URL_BRAND_APPLY)
	public void apply() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.apply(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

}

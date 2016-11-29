package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Category;
import com.hongluomeng.service.BrandService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.BrandValidator;

@Before(BrandValidator.class)
public class BrandController extends BaseController {

	private BrandService brandService = new BrandService();

	@ActionKey(Url.URL_BRAND_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = brandService.list(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_BRAND_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brand = brandService.find(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", brand));
	}

	@ActionKey(Url.URL_BRAND_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.save(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.update(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.delete(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_LIST)
	public void listCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = brandService.listCategory(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_FIND)
	public void findCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = brandService.findCategory(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", category));
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_SAVE)
	public void saveCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.saveCategory(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_CATEGORYT_UPDATE)
	public void updateCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.updateCategory(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_CATEGORYT_DELETE)
	public void deleteCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.deleteCategory(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_LIST_GET)
	public void getCategoryList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = brandService.getCategoryList(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultList));
	}

	@ActionKey(Url.URL_BRAND_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = brandService.getList(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultList));
	}

	@ActionKey(Url.URL_BRAND_GET)
	public void get() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = brandService.get(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_BRAND_APPLY_SAVE)
	public void apply() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.apply(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_MY_LIST_GET)
	public void getMyList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = brandService.getMyList(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultList));
	}

	@ActionKey(Url.URL_BRAND_APPLY_LIST)
	public void listApply() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = brandService.listApply(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_BRAND_APPLYY_FIND)
	public void findApply() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		BrandApply brandApply = brandService.findApply(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", brandApply));
	}

	@ActionKey(Url.URL_BRAND_APPLYY_PASS)
	public void reviewPass() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.reviewPass(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_APPLYY_REFUSE)
	public void reviewRefuse() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.reviewRefuse(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_BRAND_APPLYY_CANCEL)
	public void reviewCancel() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		brandService.reviewCancel(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}

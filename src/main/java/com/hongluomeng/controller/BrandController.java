package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.Member;
import com.hongluomeng.service.CategoryService;
import com.hongluomeng.type.CatetoryEnum;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Category;
import com.hongluomeng.service.BrandService;
import com.hongluomeng.type.CodeEnum;

public class BrandController extends BaseController {

	private BrandService brandService = new BrandService();
	private CategoryService categoryService = new CategoryService();

	@ActionKey(Url.URL_BRAND_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = brandService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_BRAND_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brandValidator = jsonObject.toJavaObject(Brand.class);

		Brand brand = brandService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(brand));
	}

	@ActionKey(Url.URL_BRAND_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brandValidator = jsonObject.toJavaObject(Brand.class);

		brandValidator.checkBrand_name();

		brandValidator.checkBrand_logo();

		brandValidator.checkBrand_background();

		brandValidator.checkBrand_introduce();

		brandValidator.checkBrand_agreement();

		brandService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brandValidator = jsonObject.toJavaObject(Brand.class);

		brandValidator.checkBrand_id();

		brandValidator.checkCategory_id();

		brandValidator.checkBrand_name();

		brandValidator.checkBrand_logo();

		brandValidator.checkBrand_background();

		brandValidator.checkBrand_introduce();

		brandValidator.checkBrand_agreement();

		brandService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brandValidator = jsonObject.toJavaObject(Brand.class);

		brandValidator.checkBrand_id();

		brandService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_LIST)
	public void listCategory() {
		Map<String, Object> resultMap = categoryService.treeByCategory_key(CatetoryEnum.BRAND.getKey());

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_FIND)
	public void findCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category category = categoryService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(category));
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_SAVE)
	public void saveCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_CATEGORYT_UPDATE)
	public void updateCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_CATEGORYT_DELETE)
	public void deleteCategory() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		categoryService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_CATEGORY_LIST_GET)
	public void getCategoryList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = categoryService.list(CatetoryEnum.BRAND.getKey());

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_BRAND_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		Utility.checkNull(categoryValidator.getCategory_id(), "分类编号");

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = brandService.getList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_BRAND_MY_LIST_GET)
	public void getMyList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Category categoryValidator = jsonObject.toJavaObject(Category.class);

		Utility.checkNull(categoryValidator.getCategory_id(), "分类编号");

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = brandService.getMyList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_BRAND_GET)
	public void get() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brandValidator = jsonObject.toJavaObject(Brand.class);

		brandValidator.checkBrand_id();

		Map<String, Object> resultMap = brandService.get(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_BRAND_APPLY_SAVE)
	public void apply() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Brand brandValidator = jsonObject.toJavaObject(Brand.class);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		brandValidator.checkBrand_id();

		memberValidator.checkMember_real_name();

		memberValidator.checkMember_identity_card();

		memberValidator.checkMember_identity_card_front_image();

		memberValidator.checkMember_identity_card_back_image();

		brandService.apply(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_APPLY_LIST)
	public void listApply() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = brandService.listApply(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_BRAND_APPLYY_FIND)
	public void findApply() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		BrandApply brandApplyValidator = jsonObject.toJavaObject(BrandApply.class);

		brandApplyValidator.checkBrand_id();

		brandApplyValidator.checkUser_id();

		BrandApply brandApply = brandService.findApply(jsonObject);

		renderJson(Utility.setSuccessResponse(brandApply));
	}

	@ActionKey(Url.URL_BRAND_APPLYY_PASS)
	public void reviewPass() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		BrandApply brandApplyValidator = jsonObject.toJavaObject(BrandApply.class);

		brandApplyValidator.checkBrand_id();

		brandApplyValidator.checkUser_id();

		brandService.reviewPass(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_APPLYY_REFUSE)
	public void reviewRefuse() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		BrandApply brandApplyValidator = jsonObject.toJavaObject(BrandApply.class);

		brandApplyValidator.checkBrand_id();

		brandApplyValidator.checkUser_id();

		brandService.reviewRefuse(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_BRAND_APPLYY_CANCEL)
	public void reviewCancel() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		BrandApply brandApplyValidator = jsonObject.toJavaObject(BrandApply.class);

		brandApplyValidator.checkBrand_id();

		brandService.reviewCancel(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

}

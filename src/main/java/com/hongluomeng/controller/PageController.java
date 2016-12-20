package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.service.PageService;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Page;

public class PageController extends BaseController {

	private PageService pageService = new PageService();

	@ActionKey(Url.URL_PAGE_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = pageService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_PAGE_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Page pageValidator = jsonObject.toJavaObject(Page.class);

		pageValidator.checkPage_id();

		Page page = pageService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(page));
	}

	@ActionKey(Url.URL_PAGE_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Page pageValidator = jsonObject.toJavaObject(Page.class);

		pageValidator.checkPage_name();

		pageValidator.checkPage_key();

		pageValidator.checkPage_sort();

		pageValidator.checkPage_content();

		pageService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PAGE_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Page pageValidator = jsonObject.toJavaObject(Page.class);

		pageValidator.checkPage_id();

		pageValidator.checkPage_name();

		pageValidator.checkPage_key();

		pageValidator.checkPage_sort();

		pageValidator.checkPage_content();

		pageService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PAGE_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Page pageValidator = jsonObject.toJavaObject(Page.class);

		pageValidator.checkPage_id();

		pageService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_PAGE_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = pageService.getList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

    @ActionKey(Url.URL_PAGE_GET)
    public void get() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Page pageValidator = jsonObject.toJavaObject(Page.class);

        pageValidator.checkPage_id();

        Map<String, Object> resultMap = pageService.get(jsonObject);

        renderJson(Utility.setSuccessResponse(resultMap));
    }

    @ActionKey(Url.URL_PAGE_RANKING)
    public void ranking() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Page page = pageService.ranking(jsonObject);

        if (page == null) {
            renderHtml("");
        } else {
            renderHtml(page.getPage_content());
        }
    }

}

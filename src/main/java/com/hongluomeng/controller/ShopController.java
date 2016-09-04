package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Shop;
import com.hongluomeng.service.ShopService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.ShopValidator;

public class ShopController extends BaseController {

	private ShopService shopService = new ShopService();

	@Before(ShopValidator.class)
	@ActionKey(Const.URL_SHOP_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = shopService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(ShopValidator.class)
	@ActionKey(Const.URL_SHOP_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Shop shop = shopService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", shop));
    }

	@Before(ShopValidator.class)
	@ActionKey(Const.URL_SHOP_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		shopService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(ShopValidator.class)
	@ActionKey(Const.URL_SHOP_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		shopService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(ShopValidator.class)
	@ActionKey(Const.URL_SHOP_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		shopService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}

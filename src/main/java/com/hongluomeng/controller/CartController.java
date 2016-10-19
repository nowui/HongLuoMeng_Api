package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Cart;
import com.hongluomeng.service.CartService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.CartValidator;

public class CartController extends BaseController {

	private CartService cartService = new CartService();

	@Before(CartValidator.class)
	@ActionKey(Const.URL_SHOP_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = cartService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(CartValidator.class)
	@ActionKey(Const.URL_SHOP_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Cart cart = cartService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", cart));
    }

	@Before(CartValidator.class)
	@ActionKey(Const.URL_SHOP_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		cartService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(CartValidator.class)
	@ActionKey(Const.URL_SHOP_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		cartService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(CartValidator.class)
	@ActionKey(Const.URL_SHOP_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		cartService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}

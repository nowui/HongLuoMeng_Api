package com.hongluomeng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Order;
import com.hongluomeng.service.OrderService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.OrderValidator;

@Before(OrderValidator.class)
public class OrderController extends BaseController {

	private OrderService orderService = new OrderService();

	@ActionKey(Url.URL_ORDER_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = orderService.list(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_ORDER_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Order order = orderService.find(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", order));
	}

	@ActionKey(Url.URL_ORDER_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = orderService.save(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_ORDER_CART_SAVE)
	public void saveCart() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = orderService.saveCart(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_ORDER_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		orderService.update(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_ORDER_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		orderService.delete(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_ORDER_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> orderList = orderService.getList(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", orderList));
	}

	public void pay() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = orderService.pay(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Url.URL_ORDER_PAYED)
	public void payed() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		orderService.payed(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@ActionKey(Url.URL_ORDER_NOTIFY)
	public void notifyUrl() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, String[]> paramsMap = getParaMap();

		Map<String, String> parameterMap = new HashMap<String, String>();

		for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
			parameterMap.put(entry.getKey(), entry.getValue()[0]);
		}

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String result = orderService.notify(parameterMap, request_user_id);

		renderHtml(result);
	}

}

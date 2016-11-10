package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Order;
import com.hongluomeng.service.OrderService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.OrderValidator;

public class OrderController extends BaseController {

	private OrderService orderService = new OrderService();

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = orderService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Order order = orderService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", order));
    }

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		orderService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		orderService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		orderService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> orderList = orderService.getList(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", orderList));
    }

}

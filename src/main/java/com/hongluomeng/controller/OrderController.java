package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.StringCodec;
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

		Map<String, Object> resultMap = orderService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_CART_SAVE)
	public void saveCart() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = orderService.saveCart(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
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

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_PAYED)
	public void payed() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		orderService.payed(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

	@Before(OrderValidator.class)
	@ActionKey(Const.URL_ORDER_NOTIFY)
	public void notifyUrl() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String order_no = getPara("out_trade_no");
		String order_trade_no = getPara("trade_no");
		String order_trade_account = getPara("buyer_id");
		String order_trade_price = getPara("receipt_amount");

		String result = orderService.notify(order_no, order_trade_no, order_trade_account, order_trade_price);

        renderHtml(result);
    }

}

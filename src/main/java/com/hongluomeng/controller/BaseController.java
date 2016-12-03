package com.hongluomeng.controller;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;

public class BaseController extends Controller {

	public void renderJson(Object object) {
		String response = JSON.toJSONString(object);

		setAttr(Const.KEY_RESPONSE, response);

		super.renderJson(response);
	}

}

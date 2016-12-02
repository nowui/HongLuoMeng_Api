package com.hongluomeng.controller;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;

public class BaseController extends Controller {

	public void renderJson(Object object) {
		String response = JSON.toJSONString(object);

		setAttr(Const.KEY_RESPONSE, response);

//		@SuppressWarnings("unchecked")
//		Map<String, Object> map = (Map<String, Object>) object;
//
//		if ((int) map.get(Const.KEY_CODE) == CodeEnum.CODE_500.getKey()) {
//			map.put(Const.KEY_MESSAGE, "网络错误");
//		}

		super.renderJson(JSON.toJSONString(object));
	}

}

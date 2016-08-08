package com.hongluomeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.SmsService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.ApplicationValidator;
import com.jfinal.aop.Before;
import com.taobao.api.ApiException;

public class SmsController extends BaseController {

	private SmsService smsService = new SmsService();

	@Before(ApplicationValidator.class)
	public void sms() throws ApiException {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String ip_address = Utility.getIpAddress(getRequest());

		smsService.save(jsonObject, ip_address);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}

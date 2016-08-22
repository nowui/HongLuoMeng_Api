package com.hongluomeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.SmsService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.type.SmsEnum;
import com.hongluomeng.validator.SmsValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.taobao.api.ApiException;

public class SmsController extends BaseController {

	private SmsService smsService = new SmsService();

	@Before(SmsValidator.class)
	@ActionKey(Const.URL_SMS_REGISTER)
	public void smsRegister() throws ApiException {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String ip_address = Utility.getIpAddress(getRequest());

		smsService.save(SmsEnum.REGISTER.getKey() ,jsonObject, ip_address);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(SmsValidator.class)
	@ActionKey(Const.URL_SMS_RESET_PASSWORD)
	public void smsResetPassword() throws ApiException {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String ip_address = Utility.getIpAddress(getRequest());

		smsService.save(SmsEnum.RESET_PASSWORD.getKey() ,jsonObject, ip_address);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}

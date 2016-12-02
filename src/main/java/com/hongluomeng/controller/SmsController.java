package com.hongluomeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Url;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Sms;
import com.hongluomeng.service.SmsService;
import com.hongluomeng.type.SmsEnum;
import com.jfinal.core.ActionKey;
import com.taobao.api.ApiException;

public class SmsController extends BaseController {

	private SmsService smsService = new SmsService();

	@ActionKey(Url.URL_SMS_REGISTER)
	public void smsRegister() throws ApiException {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Sms smsValidator = jsonObject.toJavaObject(Sms.class);

		smsValidator.checkSms_phone();

		String ip_address = Utility.getIpAddress(getRequest());

		smsService.save(SmsEnum.REGISTER.getKey(), jsonObject, ip_address);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_SMS_PASSWORD)
	public void smsPassword() throws ApiException {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Sms smsValidator = jsonObject.toJavaObject(Sms.class);

		smsValidator.checkSms_phone();

		String ip_address = Utility.getIpAddress(getRequest());

		smsService.save(SmsEnum.PASSWORD.getKey(), jsonObject, ip_address);

		renderJson(Utility.setSuccessResponse());
	}

}

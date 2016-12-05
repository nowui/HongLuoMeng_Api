package com.hongluomeng.service;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.dao.SmsDao;
import com.hongluomeng.model.Sms;
import com.hongluomeng.type.SmsEnum;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsService {

	private SmsDao smsDao = new SmsDao();
	private UserService userService = new UserService();

	public Integer countBySms_phoneAndSms_codeAndMinute(String sms_type, String sms_phone, String sms_code, Integer minute) {
		return smsDao.countBySms_phoneAndSms_codeAndSms_statusAndMinute(sms_type, sms_phone, sms_code, minute);
	}

	public void save(String sms_type, JSONObject jsonObject, String ip_address) throws ApiException {
		Sms smsMap = jsonObject.toJavaObject(Sms.class);

		Integer count = userService.countByUser_phone(smsMap.getSms_phone());

		if (count > 0 && sms_type.equals(SmsEnum.REGISTER.getKey())) {
			throw new RuntimeException("该手机号码已经注册过");
		}

		if (count == 0 && sms_type.equals(SmsEnum.PASSWORD.getKey())) {
			throw new RuntimeException("该手机号码还没有注册");
		}

		count = smsDao.countBySms_phoneAndMinute(sms_type, smsMap.getSms_phone(), 1);

		if (count == 0) {
			String sms_code = getCode();

			TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23448276", "2ad2e9b7e8d9cf7ca0410f0dbba14b91");
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend("");
			req.setSmsType("normal");
			req.setSmsFreeSignName("红萝梦");
			req.setSmsParamString("{code:'" + sms_code + "',product:'红萝梦'}");
			req.setRecNum(smsMap.getSms_phone());
			if (sms_type.equals(SmsEnum.REGISTER.getKey())) {
				req.setSmsTemplateCode("SMS_14251382");
			} else {
				req.setSmsTemplateCode("SMS_14236225");
			}
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

			JSONObject json = JSONObject.parseObject(rsp.getBody());
			if (json.containsKey("alibaba_aliqin_fc_sms_num_send_response")) {
				smsMap.setSms_type(sms_type);
				smsMap.setSms_code(sms_code);
				smsMap.setSms_ip_address(ip_address);

				smsDao.save(smsMap);
			} else {
				throw new RuntimeException(((JSONObject) json.get("error_response")).getString("sub_code"));
			}
		} else {
			throw new RuntimeException("一分钟后再重试");
		}
	}

	public String saveForWeixin() {
		String sms_code = getCode();

		Boolean isExit = true;

		while (isExit) {
			Integer count = smsDao.countBySms_typeAndSms_codeAndMinute(SmsEnum.WEIXIN.getKey(), sms_code, Const.SMS_TIMEOUT_EXPRESS);
			if (count == 0) {
				isExit = false;
			} else {
				sms_code = getCode();
			}
		}

		Sms sms = new Sms();
		sms.setSms_type(SmsEnum.WEIXIN.getKey());
		sms.setSms_phone("");
		sms.setSms_code(sms_code);
		sms.setSms_ip_address("");

		smsDao.save(sms);

		return sms_code;
	}

	public void updateSms_statusBySms_phone(String sms_type, String sms_phone, String sms_code) {
		smsDao.updateSms_statusBySms_phone(sms_type, sms_phone, sms_code);
	}

	private String getCode() {
		String sms_code = "";
		Random random = new Random();

		for (int i = 0; i < 6; i++) {
			sms_code += String.valueOf(random.nextInt(10));
		}

		return sms_code;
	}

}

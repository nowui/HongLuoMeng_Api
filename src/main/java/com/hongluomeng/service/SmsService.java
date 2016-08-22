package com.hongluomeng.service;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;
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
		return smsDao.countBySms_phoneAndSms_codeAndSms_statusAndMinute(sms_type, sms_phone, sms_code, false, minute);
	}

	public void save(String sms_type, JSONObject jsonObject, String ip_address) throws ApiException {
		Sms smsMap = jsonObject.toJavaObject(Sms.class);

		Integer count = userService.countByUser_phone(smsMap.getSms_phone());

		if (count > 0 && sms_type.equals(SmsEnum.REGISTER.getKey())) {
			throw new RuntimeException("该手机号码已经注册过");
		}

		if (count == 0 && sms_type.equals(SmsEnum.RESET_PASSWORD.getKey())) {
			throw new RuntimeException("该手机号码还没有注册");
		}

		count = smsDao.countBySms_phoneAndMinute(sms_type, smsMap.getSms_phone(), 1);

		if (count == 0) {
			String sms_code = "";
			Random random=new Random();

			for (int i = 0; i < 4; i++) {
				sms_code += String.valueOf(random.nextInt(10));
			}

			TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23410942", "7ce98686c9450ccc540b7802140ec659");
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend("");
			req.setSmsType( "normal" );
			req.setSmsFreeSignName( "大鱼测试" );
			req.setSmsParamString( "{code:'" + sms_code + "',product:'红萝梦'}" );
			req.setRecNum(smsMap.getSms_phone());
			req.setSmsTemplateCode( "SMS_12525470" );
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

			JSONObject json = JSONObject.parseObject(rsp.getBody());
			if (json.containsKey("alibaba_aliqin_fc_sms_num_send_response")) {
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

	public void updateSms_statusBySms_phone(String sms_type, String sms_phone, String sms_code) {
		smsDao.updateSms_statusBySms_phone(true, sms_type, sms_phone, sms_code);
	}

}

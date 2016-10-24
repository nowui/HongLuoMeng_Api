package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.MemberDelivery;
import com.hongluomeng.type.CodeEnum;

public class MemberDeliveryValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		MemberDelivery memberDelivery = jsonObject.toJavaObject(MemberDelivery.class);

		if(actionKey.equals(Const.URL_MEMBER_DELIVERY_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_MEMBER_DELIVERY_FIND)) {
			isExit = true;

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_MEMBER_DELIVERY_SAVE) || actionKey.equals(Const.URL_MEMBER_DELIVERY_UPDATE)) {
			isExit = true;

			if(actionKey.equals(Const.URL_MEMBER_DELIVERY_UPDATE) && Utility.isNullOrEmpty(memberDelivery.getMember_delivery_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_name())) {
				message += "名称为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_phone())) {
				message += "电话为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_province())) {
				message += "省份为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_city())) {
				message += "城市为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_area())) {
				message += "地区为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_address())) {
				message += "详细地址为空";
				message += Const.LINE_FEED;
			}

			if(memberDelivery.getMember_delivery_zip().length() > 6) {
				message += "邮政编码过长";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_MEMBER_DELIVERY_DELETE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_MEMBER_DELIVERY_LIST_GET)) {
			isExit = true;

		} else if(actionKey.equals(Const.URL_MEMBER_DELIVERY_GET)) {
			isExit = true;

			if(Utility.isNullOrEmpty(memberDelivery.getMember_delivery_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.URL_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

	protected void handleError(Controller c) {
		c.renderJson(Utility.setResponse(CodeEnum.CODE_400, c.getAttr(Const.KEY_MESSAGE), null));
	}

}

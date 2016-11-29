package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.MemberDelivery;
import com.jfinal.validate.Validator;

public class MemberDeliveryValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		MemberDelivery memberDelivery = jsonObject.toJavaObject(MemberDelivery.class);

		switch (actionKey) {
			case Url.URL_MEMBER_DELIVERY_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_MEMBER_DELIVERY_FIND:
				isExit = true;

				memberDelivery.checkMember_delivery_id();

				break;
			case Url.URL_MEMBER_DELIVERY_SAVE: {
				isExit = true;

				memberDelivery.checkMember_delivery_name();

				memberDelivery.checkMember_delivery_phone();

				memberDelivery.checkMember_delivery_province();

				memberDelivery.checkMember_delivery_city();

				memberDelivery.checkMember_delivery_area();

				memberDelivery.checkMember_delivery_address();

				memberDelivery.checkMember_delivery_zip();

				break;
			}
			case Url.URL_MEMBER_DELIVERY_UPDATE:
				isExit = true;

				memberDelivery.checkMember_delivery_id();

				memberDelivery.checkMember_delivery_name();

				memberDelivery.checkMember_delivery_phone();

				memberDelivery.checkMember_delivery_province();

				memberDelivery.checkMember_delivery_city();

				memberDelivery.checkMember_delivery_area();

				memberDelivery.checkMember_delivery_address();

				memberDelivery.checkMember_delivery_zip();

				break;
			case Url.URL_MEMBER_DELIVERY_DELETE:
				isExit = true;

				memberDelivery.checkMember_delivery_id();

				break;
			case Url.URL_MEMBER_DELIVERY_LIST_GET:
				isExit = true;

				break;
			case Url.URL_MEMBER_DELIVERY_GET:
				isExit = true;

				memberDelivery.checkMember_delivery_id();

				break;
		}

		if (!isExit) {
			addError(Const.KEY_ERROR, Const.URL_DENIED);
		}
	}

	protected void handleError(Controller controller) {
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttr(Const.KEY_ERROR), null));
	}

}

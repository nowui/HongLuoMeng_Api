package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.Sms;
import com.hongluomeng.model.User;
import com.jfinal.validate.Validator;

public class MemberValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Member member = jsonObject.toJavaObject(Member.class);

		User user = jsonObject.toJavaObject(User.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_MEMBER_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_MEMBER_FIND: {
				isExit = true;

				member.checkMember_id();

				break;
			}
			case Url.URL_MEMBER_DELETE: {
				isExit = true;

				member.checkMember_id();

				break;
			}
			case Url.URL_MEMBER_LOGIN: {
				isExit = true;

				user.checkUser_phone();

				user.checkUser_password();

				break;
			}
			case Url.URL_MEMBER_REGISTER:
			case Url.URL_MEMBER_PASSWORD_UPDATE: {
				isExit = true;

				user.checkUser_phone();

				user.checkUser_password();

				Sms sms = jsonObject.toJavaObject(Sms.class);

				sms.checkSms_code();

				break;
			}
			case Url.URL_MEMBER_WEIBO_OAUTH:
			case Url.URL_MEMBER_WEIBO_BIND: {
				isExit = true;

				member.checkMember_name();

				member.checkMember_avatar();

				member.checkMember_avatar_small(jsonObject.getString(Member.KEY_MEMBER_AVATAR_SMALL));

				member.checkMember_avatar_large(jsonObject.getString(Member.KEY_MEMBER_AVATAR_LARGE));

				user.checkWeibo_uid();

				user.checkWeibo_access_token();

				member.checkMember_weibo_fans();

				member.checkMember_weibo_friend();

				break;
			}
			case Url.URL_MEMBER_WECHAT_OAUTH:
			case Url.URL_MEMBER_WECHAT_BIND: {
				isExit = true;

				member.checkMember_name();

				member.checkMember_avatar();

				member.checkMember_avatar_small(jsonObject.getString(Member.KEY_MEMBER_AVATAR_SMALL));

				member.checkMember_avatar_large(jsonObject.getString(Member.KEY_MEMBER_AVATAR_LARGE));

				user.checkWechat_uid();

				user.checKWechat_access_token();

				break;
			}
			case Url.URL_MEMBER_AVATAR_UPLOAD:
				isExit = true;

				break;
			case Url.URL_MEMBER_NAME_UPDATE: {
				isExit = true;

				member.checkMember_name();

				break;
			}
		}

		if (!isExit) {
			controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, Const.URL_DENIED, null));
		}
	}

	protected void handleError(Controller controller) {

	}

}

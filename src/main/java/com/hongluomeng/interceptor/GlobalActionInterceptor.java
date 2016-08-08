package com.hongluomeng.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.DbKit;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Log;
import com.hongluomeng.model.Operation;
import com.hongluomeng.service.OperationService;
import com.hongluomeng.type.CodeEnum;

public class GlobalActionInterceptor implements Interceptor {

	private Logger logger = LogManager.getLogger(GlobalActionInterceptor.class.getName());
	private OperationService operationService = new OperationService();

	@Override
	public void intercept(Invocation inv) {
		Date start = new Date();

		String memberLoginUrl = "/member/login";
		String memberRegisterUrl = "/member/register";
		String adminLoginUrl = "/admin/login";
		String adminRegisterUrl = "/admin/register";
		String smsUrl = "/sms";
		String uploadUrl = "/upload/image";

		Set<String> urlSet = new HashSet<String>();
		urlSet.add(memberLoginUrl);
		urlSet.add(memberRegisterUrl);
		urlSet.add(adminLoginUrl);
		urlSet.add(adminRegisterUrl);
		urlSet.add(smsUrl);
		urlSet.add("/user/menu");
		urlSet.add("/category/tree");
		urlSet.add("/category/find");
		urlSet.add("/category/save");
		urlSet.add("/category/update");
		urlSet.add("/category/delete");
		urlSet.add("/upload/list");
		urlSet.add("/upload/image");

		Connection connection = null;

		Controller controller = inv.getController();

		String url = controller.getRequest().getRequestURI();

		CodeEnum code = CodeEnum.CODE_200;

		String platform = "";

		String version = "";

		String user_id = "";

		String authorization_id = "";

		String request = "";

		try {
			connection = DbKit.getConfig().getDataSource().getConnection();
			DbKit.getConfig().setThreadLocalConnection(connection);
			connection.setAutoCommit(false);

			Key key = new SecretKeySpec(Const.PRIVATE_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

			boolean isAuthorization = true;

			if (url.equals(memberLoginUrl) || url.equals(memberRegisterUrl) || url.equals(adminLoginUrl) || url.equals(adminRegisterUrl) || url.equals(smsUrl)) {

			} else {
				String token = controller.getRequest().getHeader(Const.KEY_TOKEN);

				if(Utility.isNullOrEmpty(token)) {
					isAuthorization = false;
				} else {
					try {
						Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

						user_id = claims.get(Const.KEY_USER_ID).toString();

						authorization_id = claims.get(Const.KEY_AUTHORIZATION_ID).toString();
					} catch (Exception e) {
						isAuthorization = false;
					}
				}
			}

			if (isAuthorization) {
				/*//记录请求参数
				JSONObject jsonObject = new JSONObject();
				Enumeration<?> paramKeys = controller.getRequest().getParameterNames();

				while (paramKeys.hasMoreElements()) {
					String paramKey = (String) paramKeys.nextElement();
					String[] paramValues = controller.getRequest().getParameterValues(paramKey);
					if (paramValues.length == 1) {
						String paramValue = paramValues[0];
						//if (paramValue.length() != 0) {
							jsonObject.put(paramKey, paramValue);
						//}
					}
				}*/

				if (url.equals(uploadUrl)) {
					request = "{}";
				} else {
					request = HttpKit.readData(controller.getRequest());
				}

				JSONObject jsonObject = JSONObject.parseObject(request);

				controller.setAttr(Const.KEY_REQUEST, jsonObject);

				jsonObject.put(Const.KEY_REQUEST_USER_ID, user_id);

				boolean isNotMatch = true;

				for (String value : urlSet) {
					if (value.equals(url)) {
						isNotMatch = false;

						break;
					}
				}

				/*if (isNotMatch) {
					isAuthorization = false;

					List<Operation> operationList = operationService.listByUser_id(user_id);

					for (Operation operation : operationList) {
						if (url.equals(operation.getOperation_value())) {
							isAuthorization = true;

							break;
						}
					}
				}*/
			}

			if (isAuthorization) {
				platform = controller.getRequest().getHeader(Const.KEY_PLATFORM);
				version = controller.getRequest().getHeader(Const.KEY_VERSION);

				String message = "";

				if(Utility.isNullOrEmpty(platform)) {
					message += "没有platform参数";
					message += "<br />";
				}

				if(Utility.isNullOrEmpty(version)) {
					message += "没有version参数";
					message += "<br />";
				}

				if (Utility.isNullOrEmpty(message)) {
					inv.invoke();
				} else {
					code = CodeEnum.CODE_400;

					controller.renderJson(Utility.setResponse(code, message, null));
				}
			} else {
				code = CodeEnum.CODE_401;

				controller.renderJson(Utility.setResponse(code, Const.PERMISSION_DENIED, null));
			}

			connection.commit();
		} catch (RuntimeException | SQLException e) {
			e.printStackTrace();

			try {
                if (null != connection) {
                	connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

			code = CodeEnum.CODE_500;

			String message = e.toString();
			String value = "java.lang.RuntimeException: ";

			if (message.indexOf(value) > -1) {
				code = CodeEnum.CODE_400;

				message.replaceAll(value, "");
			}

			controller.renderJson(Utility.setResponse(code, message, null));
		} finally {
			try {
                if (null != connection){
                	connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                DbKit.getConfig().removeThreadLocalConnection();
            }

			ThreadContext.put(Log.KEY_LOG_ID, Utility.getUUID());

			ThreadContext.put(Log.KEY_LOG_URL, url);

			ThreadContext.put(Log.KEY_LOG_REQUEST, request);

			ThreadContext.put(Log.KEY_LOG_RESPONSE, controller.getAttrForStr(Const.KEY_RESPONSE));

			ThreadContext.put(Log.KEY_AUTHORIZATION_ID, authorization_id);

			ThreadContext.put(Log.KEY_USER_ID, user_id);

			ThreadContext.put(Log.KEY_LOG_CODE, code.getKey() + "");

			ThreadContext.put(Log.KEY_LOG_PLATFORM, platform);

			ThreadContext.put(Log.KEY_LOG_VERSION, version);

			ThreadContext.put(Log.KEY_LOG_IP_ADDRESS, Utility.getIpAddress(controller.getRequest()));

			ThreadContext.put(Log.KEY_LOG_CREATE_TIME, Utility.getDateTimeString(start));

			ThreadContext.put(Log.KEY_LOG_RUN_TIME, (new Date().getTime() - start.getTime()) + "");

			if (url.contains("/log/")) {

			} else {
				logger.error("");
			}
		}

	}

}

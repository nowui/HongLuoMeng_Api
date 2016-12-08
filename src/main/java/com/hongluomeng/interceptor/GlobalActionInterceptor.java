package com.hongluomeng.interceptor;

import com.hongluomeng.common.Private;
import com.hongluomeng.common.Url;
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

        //不检查token的url列表
        Set<String> uncheckTokenSet = new HashSet<String>();
        uncheckTokenSet.add(Url.URL_MEMBER_LOGIN);
        uncheckTokenSet.add(Url.URL_MEMBER_REGISTER);
        uncheckTokenSet.add(Url.URL_MEMBER_PASSWORD_UPDATE);
        uncheckTokenSet.add(Url.URL_ADMIN_LOGIN);
        uncheckTokenSet.add(Url.URL_SMS_REGISTER);
        uncheckTokenSet.add(Url.URL_SMS_PASSWORD);
        uncheckTokenSet.add(Url.URL_MEMBER_WEIBO_OAUTH);
        uncheckTokenSet.add(Url.URL_MEMBER_WECHAT_OAUTH);
        uncheckTokenSet.add(Url.URL_ORDER_NOTIFY);
        uncheckTokenSet.add(Url.URL_WEIXIN_NOTIFY);

        //不检查request的url列表
        Set<String> uncheckRequestSet = new HashSet<String>();
        uncheckRequestSet.add(Url.URL_UPLOAD_IMAGE);
        uncheckRequestSet.add(Url.URL_MEMBER_AVATAR_UPLOAD);
        uncheckRequestSet.add(Url.URL_ORDER_NOTIFY);
        uncheckRequestSet.add(Url.URL_WEIXIN_NOTIFY);

        //不保存request到log的url列表
        Set<String> unSaveLogSet = new HashSet<String>();
        unSaveLogSet.add(Url.URL_UPLOAD_BASE64);

        //不检查请求头的url列表
        Set<String> uncheckRequestHeaderSet = new HashSet<String>();
        uncheckRequestHeaderSet.add(Url.URL_ORDER_NOTIFY);
        uncheckRequestHeaderSet.add(Url.URL_WEIXIN_NOTIFY);

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

            Key key = new SecretKeySpec(Private.PRIVATE_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            Boolean isAuthorization = true;

            Boolean isCheckToken = true;

            for (String value : uncheckTokenSet) {
                if (value.equals(url)) {
                    isCheckToken = false;

                    break;
                }
            }

            //验证token
            if (isCheckToken) {
                String token = controller.getRequest().getHeader(Const.KEY_TOKEN);

                if (Utility.isNullOrEmpty(token)) {
                    isAuthorization = false;
                } else {
                    try {
                        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

                        user_id = claims.get(Const.KEY_USER_ID).toString();

                        authorization_id = claims.get(Const.COLUMN_AUTHORIZATION_ID).toString();
                    } catch (Exception e) {
                        isAuthorization = false;
                    }
                }
            }

            if (isAuthorization) {
                Boolean isChecRequest = true;

                for (String value : uncheckRequestSet) {
                    if (value.equals(url)) {
                        request = "{}";

                        isChecRequest = false;

                        break;
                    }
                }

                if (isChecRequest) {
                    request = HttpKit.readData(controller.getRequest());
                }

                if (Utility.isNullOrEmpty(request)) {
                    request = "{}";
                }

                JSONObject jsonObject = JSONObject.parseObject(request);

                controller.setAttr(Const.KEY_REQUEST, jsonObject);

                jsonObject.put(Const.KEY_REQUEST_USER_ID, user_id);

                //request内容不要保存到log里面
                for (String value : unSaveLogSet) {
                    if (value.equals(url)) {
                        request = "{}";

                        break;
                    }
                }

                //验证是否有权限
                if (isCheckToken) {
                    isAuthorization = false;

                    List<Operation> operationList = operationService.listByUser_id(user_id);

                    for (Operation operation : operationList) {
                        if (url.equals(operation.getOperation_value())) {
                            isAuthorization = true;

                            break;
                        }
                    }
                }
            }

            if (isAuthorization) {
                platform = controller.getRequest().getHeader(Const.KEY_PLATFORM);
                version = controller.getRequest().getHeader(Const.KEY_VERSION);

                String message = "";

                if (Utility.isNullOrEmpty(platform)) {
                    message += "没有platform参数";
                    message += "<br />";
                }

                if (Utility.isNullOrEmpty(version)) {
                    message += "没有version参数";
                    message += "<br />";
                }

                //不检查请求头
                for (String value : uncheckRequestHeaderSet) {
                    if (value.equals(url)) {
                        message = "";

                        break;
                    }
                }

                if (Utility.isNullOrEmpty(message)) {
                    inv.invoke();

                    //如果测试，抛出异常，事务回滚
                    if (Const.IS_TEST) {
                        throw new Exception(controller.getAttrForStr(Const.KEY_RESPONSE));
                    }
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

            if (message.contains(value)) {
                code = CodeEnum.CODE_400;

                message = message.replace(value, "");
            }

            controller.renderJson(Utility.setResponse(code, message, null));
        } catch (Exception e) {
            e.printStackTrace();

            try {
                if (null != connection) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String message = e.toString();
            String value = "java.lang.Exception: ";
            message = message.replace(value, "");

            controller.renderJson(message);
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                DbKit.getConfig().removeThreadLocalConnection();
            }

            ThreadContext.put(Log.COLUMN_LOG_ID, Utility.getUUID());

            ThreadContext.put(Log.COLUMN_LOG_URL, url);

            ThreadContext.put(Log.COLUMN_LOG_REQUEST, request);

            ThreadContext.put(Log.COLUMN_LOG_RESPONSE, controller.getAttrForStr(Const.KEY_RESPONSE));

            ThreadContext.put(Log.COLUMN_AUTHORIZATION_ID, authorization_id);

            ThreadContext.put(Log.COLUMN_USER_ID, user_id);

            ThreadContext.put(Log.COLUMN_LOG_CODE, code.getKey() + "");

            ThreadContext.put(Log.COLUMN_LOG_PLATFORM, platform);

            ThreadContext.put(Log.COLUMN_LOG_VERSION, version);

            ThreadContext.put(Log.COLUMN_LOG_IP_ADDRESS, Utility.getIpAddress(controller.getRequest()));

            ThreadContext.put(Log.COLUMN_LOG_CREATE_TIME, Utility.getDateTimeString(start));

            ThreadContext.put(Log.COLUMN_LOG_RUN_TIME, (new Date().getTime() - start.getTime()) + "");

            if (url.contains("/log/") || Const.IS_TEST) {

            } else {
                logger.error("");
            }
        }

    }

}

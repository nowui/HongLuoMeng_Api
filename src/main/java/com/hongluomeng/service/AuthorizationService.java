package com.hongluomeng.service;

import com.hongluomeng.common.Private;
import com.hongluomeng.model.Authorization;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.*;

import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.AuthorizationDao;

public class AuthorizationService extends BaseService {

	private AuthorizationDao authorizationDao = new AuthorizationDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		Integer count = authorizationDao.count();

		List<Authorization> authorizationList = authorizationDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Authorization authorization : authorizationList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Authorization.COLUMN_AUTHORIZATION_ID, authorization.getAuthorization_id());
			map.put(Authorization.COLUMN_USER_ID, authorization.getUser_id());
			map.put(Authorization.COLUMN_AUTHORIZATION_CREATE_TIME, authorization.getAuthorization_create_time());
			map.put(Authorization.COLUMN_AUTHORIZATION_EXPIRE_TIME, authorization.getAuthorization_expire_time());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public Authorization find(JSONObject jsonObject) {
		Authorization authorizationMap = jsonObject.toJavaObject(Authorization.class);

		Authorization authorization = authorizationDao.findByAuthorization_id(authorizationMap.getAuthorization_id());

		return authorization;
	}

	public String saveByUser_id(String user_id) {
		Key key = new SecretKeySpec(Private.PRIVATE_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		long expMillis = nowMillis + 1000 * 60 * 60 * 24 * 360;
		Date exp = new Date(expMillis);

		Authorization authorizationParameter = new Authorization();
		authorizationParameter.setAuthorization_token("");
		authorizationParameter.setUser_id(user_id);
		authorizationParameter.setAuthorization_create_time(now);
		authorizationParameter.setAuthorization_expire_time(exp);
		authorizationDao.save(authorizationParameter);

		String token = Jwts.builder().setIssuedAt(now).setExpiration(exp).claim(Const.COLUMN_AUTHORIZATION_ID, authorizationParameter.getAuthorization_id()).claim(Const.KEY_USER_ID, user_id).signWith(SignatureAlgorithm.HS512, key).compact();

		authorizationParameter.setAuthorization_token(token);
		authorizationDao.updateToken(authorizationParameter);

		return token;
	}

}

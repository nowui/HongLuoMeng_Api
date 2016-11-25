package com.hongluomeng.service;

import com.hongluomeng.common.Private;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.AuthorizationDao;
import com.hongluomeng.model.Authorization;

public class AuthorizationService {

	private AuthorizationDao authorizationDao = new AuthorizationDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Authorization authorizationMap = jsonObject.toJavaObject(Authorization.class);

		Integer count = authorizationDao.count();

		List<Authorization> authorizationList = authorizationDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, authorizationList);

		return resultMap;
	}

	public Authorization find(JSONObject jsonObject) {
		Authorization authorizationMap = jsonObject.toJavaObject(Authorization.class);

		return authorizationDao.findByAuthorization_id(authorizationMap.getAuthorization_id());
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

		String token = Jwts.builder().setIssuedAt(now).setExpiration(exp).claim(Const.KEY_AUTHORIZATION_ID, authorizationParameter.getAuthorization_id()).claim(Const.KEY_USER_ID, user_id).signWith(SignatureAlgorithm.HS512, key).compact();

		authorizationParameter.setAuthorization_token(token);
		authorizationDao.updateToken(authorizationParameter);

		return token;
	}

}

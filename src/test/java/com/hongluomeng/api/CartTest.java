package com.hongluomeng.api;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CartTest {

    private HttpPost httpRequst;


    @Before
    public void init() {
        httpRequst = new HttpPost("http://localhost:8080/cart/update");
        httpRequst.addHeader("Accept", "application/json");
        httpRequst.addHeader("Content-Type", "application/json");
        httpRequst.addHeader("token", "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE0ODAxODIyODYsImV4cCI6MTQ4MTIyMTUxNSwiYXV0aG9yaXphdGlvbl9pZCI6ImFjZDRiNDAwODU0MTQxMThiZDk4MmRkNmNjZjliZWE1IiwidXNlcl9pZCI6IjVjMTI2ODAxMzI3MjQ5YTFhODY3ZWU0NjZmYWI4YTBiIn0.J5pjwyb-lrlLGcqPltu75E-386VrqHbbeHlam72oucb5sCNJ3mq5UfVI8h7m78MQDFO79eFoRcT-5ZhPBTxE0g");
        httpRequst.addHeader("platform", "JUNIT");
        httpRequst.addHeader("version", "1.0.0");
    }

    @Test
    public void Update() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cart_id", "ad724fd0c0494b80a8f8e9092e718d01");
        jsonObject.put("product_amount", 5);

        StringEntity stringEntity = new StringEntity(jsonObject.toJSONString());
        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("UTF-8");

        httpRequst.setEntity(stringEntity);
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);

        HttpEntity httpEntity = httpResponse.getEntity();
        System.out.println(EntityUtils.toString(httpEntity));
    }

}

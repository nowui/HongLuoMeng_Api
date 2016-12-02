package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.Cart;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.CartService;

public class CartController extends BaseController {

    private CartService cartService = new CartService();

    @ActionKey(Url.URL_CART_LIST)
    public void list() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Utility.checkPageAndLimit(jsonObject);

        Map<String, Object> resultMap = cartService.list(jsonObject);

        renderJson(Utility.setSuccessResponse(resultMap));
    }

    @ActionKey(Url.URL_CART_FIND)
    public void find() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Cart cartValidator = jsonObject.toJavaObject(Cart.class);

        cartValidator.checkCart_id();

        Cart cart = cartService.find(jsonObject);

        renderJson(Utility.setSuccessResponse(cart));
    }

    @ActionKey(Url.URL_CART_SAVE)
    public void save() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Cart cartValidator = jsonObject.toJavaObject(Cart.class);

        cartValidator.checkProduct_sku_id();

        cartValidator.checkProduct_amount();

        cartService.save(jsonObject);

        renderJson(Utility.setSuccessResponse());
    }

    @ActionKey(Url.URL_CART_UPDATE)
    public void update() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Cart cartValidator = jsonObject.toJavaObject(Cart.class);

        cartValidator.checkCart_id();

        cartValidator.checkProduct_amount();

        cartService.update(jsonObject);

        renderJson(Utility.setSuccessResponse());
    }

    @ActionKey(Url.URL_CART_DELETE)
    public void delete() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Cart cartValidator = jsonObject.toJavaObject(Cart.class);

        cartValidator.checkCart_id();

        cartService.delete(jsonObject);

        renderJson(Utility.setSuccessResponse());
    }

    @ActionKey(Url.URL_CART_LIST_GET)
    public void getList() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        List<Map<String, Object>> cartList = cartService.getList(jsonObject);

        renderJson(Utility.setSuccessResponse(cartList));
    }

}

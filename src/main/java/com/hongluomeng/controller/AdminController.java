package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Admin;
import com.hongluomeng.service.AdminService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.AdminValidator;

public class AdminController extends BaseController {

    private AdminService adminService = new AdminService();

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_LIST)
    public void list() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Map<String, Object> resultMap = adminService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_FIND)
    public void find() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Admin admin = adminService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", admin));
    }

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_SAVE)
    public void save() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        adminService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_UPDATE)
    public void update() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        adminService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_DELETE)
    public void delete() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        adminService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_OPERATION_LIST)
    public void listOperation() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        List<Map<String, Object>> list = adminService.listRole(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", list));
    }

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_OPERATION_UPDATE)
    public void updateOperation() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        adminService.updateRole(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

    @Before(AdminValidator.class)
    @ActionKey(Const.URL_ADMIN_LOGIN)
    public void login() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Map<String, Object> map = adminService.login(jsonObject);

        if (map == null) {
            renderJson(Utility.setResponse(CodeEnum.CODE_400, "用户名或者密码不正确", null));
        } else {
            renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
        }
    }

}

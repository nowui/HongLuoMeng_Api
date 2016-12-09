package com.hongluomeng.common;

import com.hongluomeng.controller.*;
import com.hongluomeng.model.*;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.hongluomeng.interceptor.GlobalActionInterceptor;

public class AppConfig extends JFinalConfig {

    public void configConstant(Constants constants) {
        constants.setDevMode(false);
        constants.setViewType(ViewType.JSP);
        constants.setError404View("/error.jsp");
    }

    public void configRoute(Routes routes) {
        routes.add("/", ApplicationController.class);
        routes.add("/user", UserController.class);
        routes.add("/category", CategoryController.class);
        routes.add("/role", RoleController.class);
        routes.add("/operation", OperationController.class);
        routes.add("/authorization", AuthorizationController.class);
        routes.add("/admin", AdminController.class);
        routes.add("/log", LogController.class);
        routes.add("/attribute", AttributeController.class);
        routes.add("/brand", BrandController.class);
        routes.add("/product", ProductController.class);
        routes.add("/upload", UploadController.class);
        routes.add("/member", MemberController.class);
        routes.add("/group", GroupController.class);
        routes.add("/menu", MenuController.class);
        routes.add("/sms", SmsController.class);
        routes.add("/member/level", MemberLevelController.class);
        routes.add("/cart", CartController.class);
        routes.add("/member/delivery", MemberDeliveryController.class);
        routes.add("/order", OrderController.class);
        routes.add("/activity", ActivityController.class);
        routes.add("/topic", TopicController.class);
        routes.add("/weixin/notify", WeixinController.class);
    }

    public void configPlugin(Plugins plugins) {
        PropKit.use("Jdbc.properties");

        final String URL = PropKit.get("jdbcUrl");
        final String USERNAME = PropKit.get("user");
        final String PASSWORD = PropKit.get("password");
        final Integer INITIALSIZE = PropKit.getInt("initialSize");
        final Integer MIDIDLE = PropKit.getInt("minIdle");
        final Integer MAXACTIVEE = PropKit.getInt("maxActivee");

//        Slf4jLogFilter sql_log_filter = new Slf4jLogFilter();
//        StatFilter sql_stat_filter = new StatFilter();
//
//        sql_log_filter.setConnectionLogEnabled(false);
//        sql_log_filter.setStatementLogEnabled(false);
//        sql_log_filter.setStatementExecutableSqlLogEnable(true);
//        sql_log_filter.setResultSetLogEnabled(false);
//
//        sql_stat_filter.setSlowSqlMillis(3 * 1000);
//        sql_stat_filter.setLogSlowSql(true);

        DruidPlugin druidPlugin = new DruidPlugin(URL, USERNAME, PASSWORD);
        druidPlugin.set(INITIALSIZE, MIDIDLE, MAXACTIVEE);
        druidPlugin.setFilters("stat,wall");
//        druidPlugin.addFilter(sql_log_filter);
//        druidPlugin.addFilter(sql_stat_filter);
        plugins.add(druidPlugin);

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
        activeRecordPlugin.addMapping("table_user", "user_id", User.class);
        activeRecordPlugin.addMapping("table_category", "category_id", Category.class);
        activeRecordPlugin.addMapping("table_role", "role_id", Role.class);
        activeRecordPlugin.addMapping("table_operation", "operation_id", Operation.class);
        activeRecordPlugin.addMapping("table_role_operation", "role_operation_id", RoleOperation.class);
        activeRecordPlugin.addMapping("table_authorization", "authorization_id", Authorization.class);
        activeRecordPlugin.addMapping("table_admin", "admin_id", Admin.class);
        activeRecordPlugin.addMapping("table_user_role", "user_role_id", UserRole.class);
        activeRecordPlugin.addMapping("table_log", "log_id", Log.class);
        activeRecordPlugin.addMapping("table_attribute", "attribute_id", Attribute.class);
        activeRecordPlugin.addMapping("table_category_attribute", "category_attribute_id", CategoryAttribute.class);
        activeRecordPlugin.addMapping("table_brand", "brand_id", Brand.class);
        activeRecordPlugin.addMapping("table_product", "product_id", Product.class);
        activeRecordPlugin.addMapping("table_member", "member_id", Member.class);
        activeRecordPlugin.addMapping("table_category_attribute_value", "category_attribute_value_id", CategoryAttributeValue.class);
        activeRecordPlugin.addMapping("table_sms", "sms_id", Sms.class);
        activeRecordPlugin.addMapping("table_member_level", "member_level_id", MemberLevel.class);
        activeRecordPlugin.addMapping("table_brand_apply", "brand_apply_id", BrandApply.class);
        activeRecordPlugin.addMapping("table_product_sku", "product_sku_id", ProductSku.class);
        activeRecordPlugin.addMapping("table_cart", "cart_id", Cart.class);
        activeRecordPlugin.addMapping("table_member_delivery", "member_delivery_id", MemberDelivery.class);
        activeRecordPlugin.addMapping("table_order", "order_id", Order.class);
        activeRecordPlugin.addMapping("table_order_product", "order_product_id", OrderProduct.class);
        activeRecordPlugin.addMapping("table_product_lock_stock", "product_lock_stock_id", ProductLockStock.class);
        activeRecordPlugin.addMapping("table_activity", "activity_id", Activity.class);
        activeRecordPlugin.addMapping("table_topic", "topic_id", Topic.class);
        activeRecordPlugin.addMapping("table_topic_comment", "topic_comment_id", TopicComment.class);
        activeRecordPlugin.addMapping("table_topic_like", "topic_like_id", TopicLike.class);
        plugins.add(activeRecordPlugin);
    }

    public void configInterceptor(Interceptors interceptors) {
        interceptors.addGlobalActionInterceptor(new GlobalActionInterceptor());
    }

    public void configHandler(Handlers handlers) {

    }

}

package com.hongluomeng.common;

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
import com.hongluomeng.controller.AdminController;
import com.hongluomeng.controller.ApplicationController;
import com.hongluomeng.controller.AttributeController;
import com.hongluomeng.controller.AuthorizationController;
import com.hongluomeng.controller.BrandController;
import com.hongluomeng.controller.CartController;
import com.hongluomeng.controller.CategoryController;
import com.hongluomeng.controller.GroupController;
import com.hongluomeng.controller.LogController;
import com.hongluomeng.controller.MemberController;
import com.hongluomeng.controller.MemberDeliveryController;
import com.hongluomeng.controller.MemberLevelController;
import com.hongluomeng.controller.MenuController;
import com.hongluomeng.controller.OrderController;
import com.hongluomeng.controller.ProductController;
import com.hongluomeng.controller.RoleController;
import com.hongluomeng.controller.ShopController;
import com.hongluomeng.controller.SmsController;
import com.hongluomeng.controller.UploadController;
import com.hongluomeng.controller.UserController;
import com.hongluomeng.controller.OperationController;
import com.hongluomeng.interceptor.GlobalActionInterceptor;
import com.hongluomeng.model.Admin;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.model.Authorization;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.Log;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.MemberDelivery;
import com.hongluomeng.model.MemberLevel;
import com.hongluomeng.model.Operation;
import com.hongluomeng.model.Order;
import com.hongluomeng.model.OrderProduct;
import com.hongluomeng.model.Product;
import com.hongluomeng.model.ProductAttribute;
import com.hongluomeng.model.ProductSku;
import com.hongluomeng.model.Role;
import com.hongluomeng.model.RoleOperation;
import com.hongluomeng.model.Shop;
import com.hongluomeng.model.Sms;
import com.hongluomeng.model.User;
import com.hongluomeng.model.UserRole;

public class AppConfig extends JFinalConfig {

	public void configConstant(Constants constants) {
		constants.setDevMode(false);
		constants.setViewType(ViewType.JSP);
		constants.setError404View("index.jsp");
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
		routes.add("/shop", ShopController.class);
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
	}

	public void configPlugin(Plugins plugins) {
		PropKit.use("Jdbc.properties");

		final String URL = PropKit.get("jdbcUrl");
	    final String USERNAME = PropKit.get("user");
	    final String PASSWORD = PropKit.get("password");
	    final Integer INITIALSIZE = PropKit.getInt("initialSize");
	    final Integer MIDIDLE = PropKit.getInt("minIdle");
	    final Integer MAXACTIVEE = PropKit.getInt("maxActivee");

	    DruidPlugin druidPlugin = new DruidPlugin(URL, USERNAME, PASSWORD);
	    druidPlugin.set(INITIALSIZE, MIDIDLE, MAXACTIVEE);
	    druidPlugin.setFilters("stat,wall");
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
	    activeRecordPlugin.addMapping("table_shop", "shop_id", Shop.class);
	    activeRecordPlugin.addMapping("table_brand", "brand_id", Brand.class);
	    activeRecordPlugin.addMapping("table_product", "product_id", Product.class);
	    activeRecordPlugin.addMapping("table_member", "member_id", Member.class);
	    activeRecordPlugin.addMapping("table_product_attribute", "product_attribute_id", ProductAttribute.class);
	    activeRecordPlugin.addMapping("table_sms", "sms_id", Sms.class);
	    activeRecordPlugin.addMapping("table_member_level", "member_level_id", MemberLevel.class);
	    activeRecordPlugin.addMapping("table_brand_apply", "brand_apply_id", BrandApply.class);
	    activeRecordPlugin.addMapping("table_product_sku", "product_sku_id", ProductSku.class);
	    activeRecordPlugin.addMapping("table_cart", "cart_id", Cart.class);
	    activeRecordPlugin.addMapping("table_member_delivery", "member_delivery_id", MemberDelivery.class);
	    activeRecordPlugin.addMapping("table_order", "order_id", Order.class);
	    activeRecordPlugin.addMapping("table_order_product", "order_product_id", OrderProduct.class);
	    plugins.add(activeRecordPlugin);
	}

	public void configInterceptor(Interceptors interceptors) {
		interceptors.addGlobalActionInterceptor(new GlobalActionInterceptor());
	}

	public void configHandler(Handlers handlers) {

	}

}

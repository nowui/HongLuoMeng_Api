package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.OrderProduct;

public class OrderProductDao {

	private Integer count(OrderProduct orderProduct) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + OrderProduct.KEY_TABLE_ORDER_PRODUCT + " ");
		dynamicSQL.append("WHERE " + OrderProduct.KEY_ORDER_PRODUCT_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		OrderProduct orderProduct = new OrderProduct();

		return count(orderProduct);
	}

	private List<OrderProduct> list(OrderProduct orderProduct, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + OrderProduct.KEY_TABLE_ORDER_PRODUCT + " ");
		dynamicSQL.append("WHERE " + OrderProduct.KEY_ORDER_PRODUCT_STATUS + " = 1 ");
		if (!Utility.isNullOrEmpty(orderProduct.getOrderIdList())) {
			for (int i = 0; i < orderProduct.getOrderIdList().size(); i++) {
				String order_id = orderProduct.getOrderIdList().get(i);

				if(i == 0) {
					dynamicSQL.append("AND(");
				} else {
					dynamicSQL.append("OR ");
				}

				dynamicSQL.isNullOrEmpty(OrderProduct.KEY_ORDER_ID + " = ? ", order_id);
			}
			dynamicSQL.append(") ");
		}
		dynamicSQL.append("ORDER BY " + OrderProduct.KEY_ORDER_PRODUCT_CREATE_TIME + " ASC ");
		dynamicSQL.appendPagination(m, n);

		return new OrderProduct().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<OrderProduct> listByOrder_id(String order_id) {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrder_id(order_id);

		return list(orderProduct, 0, 0);
	}

	public List<OrderProduct> listByOrderIdList(List<String> orderIdList) {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrderIdList(orderIdList);

		if(orderIdList.size() == 0) {
			return new ArrayList<OrderProduct>();
		}

		return list(orderProduct, 0, 0);
	}

	private OrderProduct find(OrderProduct orderProduct) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + OrderProduct.KEY_TABLE_ORDER_PRODUCT + " ");
		dynamicSQL.append("WHERE " + OrderProduct.KEY_ORDER_PRODUCT_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + OrderProduct.KEY_ORDER_ID + " = ? ", orderProduct.getOrder_id());

		List<OrderProduct> orderProductList = new OrderProduct().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(orderProductList == null) {
			return null;
		} else {
			return orderProductList.get(0);
		}
	}

	public OrderProduct findByOrder_id(String order_id) {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrder_id(order_id);

		orderProduct.checkOrder_id();

		return find(orderProduct);
	}

	public void save(OrderProduct orderProduct, String request_user_id) {
		orderProduct.setOrder_product_create_user_id(request_user_id);
		orderProduct.setOrder_product_create_time(new Date());
		orderProduct.setOrder_product_update_user_id(request_user_id);
		orderProduct.setOrder_product_update_time(new Date());
		orderProduct.setOrder_product_status(true);

		orderProduct.save();
	}

	public void saveByOrderProductList(List<OrderProduct> orderProductList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + OrderProduct.KEY_TABLE_ORDER_PRODUCT + " ( ");
		sql.append(OrderProduct.KEY_ORDER_ID + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_ID + ", ");
		sql.append(OrderProduct.KEY_CATEGORY_ID + ", ");
		sql.append(OrderProduct.KEY_CATEGORY_NAME + ", ");
		sql.append(OrderProduct.KEY_BRAND_ID + ", ");
		sql.append(OrderProduct.KEY_BRAND_NAME + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_NAME + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_IMAGE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_IS_NEW + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_IS_RECOMMEND + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_IS_BARGAIN + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_IS_HOT + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_IS_SELL_OUT + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_IS_SALE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_CONTENT + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_SKU_VALUE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_SKU_ID + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_ATTRIBUTE_VALUE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_PRICE + ", ");
		sql.append(OrderProduct.KEY_MEMBER_LEVEL_PRICE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_PAY_PRICE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_PAY_AMOUNT + ", ");
		sql.append(OrderProduct.KEY_ORDER_PRODUCT_CREATE_USER_ID + ", ");
		sql.append(OrderProduct.KEY_ORDER_PRODUCT_CREATE_TIME + ", ");
		sql.append(OrderProduct.KEY_ORDER_PRODUCT_UPDATE_USER_ID + ", ");
		sql.append(OrderProduct.KEY_ORDER_PRODUCT_UPDATE_TIME + ", ");
		sql.append(OrderProduct.KEY_ORDER_PRODUCT_STATUS + " ");
		sql.append(") VALUES ( ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("? ");
		sql.append(") ");

		for(OrderProduct orderProduct : orderProductList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(orderProduct.getOrder_id());
			objectList.add(orderProduct.getProduct_id());
			objectList.add(orderProduct.getCategory_id());
			objectList.add(orderProduct.getCategory_name());
			objectList.add(orderProduct.getBrand_id());
			objectList.add(orderProduct.getBrand_name());
			objectList.add(orderProduct.getProduct_name());
			objectList.add(orderProduct.getProduct_image().toJSONString());
			objectList.add(orderProduct.getProduct_is_new());
			objectList.add(orderProduct.getProduct_is_recommend());
			objectList.add(orderProduct.getProduct_is_bargain());
			objectList.add(orderProduct.getProduct_is_hot());
			objectList.add(orderProduct.getProduct_is_sell_out());
			objectList.add(orderProduct.getProduct_is_sale());
			objectList.add(orderProduct.getProduct_content());
			objectList.add(orderProduct.getProduct_sku_value().toJSONString());
			objectList.add(orderProduct.getProduct_sku_id());
			objectList.add(orderProduct.getProduct_attribute_value().toJSONString());
			objectList.add(orderProduct.getProduct_price());
			objectList.add(orderProduct.getMember_level_price().toJSONString());
			objectList.add(orderProduct.getProduct_pay_price());
			objectList.add(orderProduct.getProduct_pay_amount());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(true);

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void update(OrderProduct orderProduct, String request_user_id) {
		orderProduct.remove(OrderProduct.KEY_ORDER_PRODUCT_CREATE_USER_ID);
		orderProduct.remove(OrderProduct.KEY_ORDER_PRODUCT_CREATE_TIME);
		orderProduct.setOrder_product_update_user_id(request_user_id);
		orderProduct.setOrder_product_update_time(new Date());

		orderProduct.update();
	}

	public void delete(String order_id, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + OrderProduct.KEY_TABLE_ORDER_PRODUCT + " ");
		dynamicSQL.append("SET " + OrderProduct.KEY_ORDER_PRODUCT_STATUS + " = 0, ");
		dynamicSQL.append(OrderProduct.KEY_ORDER_PRODUCT_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(OrderProduct.KEY_ORDER_PRODUCT_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + OrderProduct.KEY_ORDER_ID + " = ? ", order_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}

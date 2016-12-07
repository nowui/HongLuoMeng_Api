package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.OrderProduct;

public class OrderProductDao {

	private Integer count(OrderProduct orderProduct) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + OrderProduct.TABLE_ORDER_PRODUCT + " ");
		myDynamicSQL.append("WHERE " + OrderProduct.TABLE_ORDER_PRODUCT + "." + OrderProduct.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		OrderProduct orderProduct = new OrderProduct();

		return count(orderProduct);
	}

	private List<OrderProduct> list(OrderProduct orderProduct, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + OrderProduct.TABLE_ORDER_PRODUCT + " ");
		myDynamicSQL.append("WHERE " + OrderProduct.TABLE_ORDER_PRODUCT + "." + OrderProduct.COLUMN_SYSTEM_STATUS + " = 1 ");
		if (!Utility.isNullOrEmpty(orderProduct.getOrderIdList())) {
			for (int i = 0; i < orderProduct.getOrderIdList().size(); i++) {
				String order_id = orderProduct.getOrderIdList().get(i);

				if(i == 0) {
					myDynamicSQL.append("AND(");
				} else {
					myDynamicSQL.append("OR ");
				}

				myDynamicSQL.isNullOrEmpty(OrderProduct.COLUMN_ORDER_ID + " = ? ", order_id);
			}
			myDynamicSQL.append(") ");
		}
		myDynamicSQL.append("ORDER BY " + OrderProduct.COLUMN_SYSTEM_CREATE_TIME + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return new OrderProduct().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + OrderProduct.TABLE_ORDER_PRODUCT + " ");
		myDynamicSQL.append("WHERE " + OrderProduct.TABLE_ORDER_PRODUCT + "." + OrderProduct.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + OrderProduct.COLUMN_ORDER_ID + " = ? ", orderProduct.getOrder_id());

		List<OrderProduct> orderProductList = new OrderProduct().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
		orderProduct.initForSave(request_user_id);

		orderProduct.save();
	}

	public void saveByOrderProductList(List<OrderProduct> orderProductList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + OrderProduct.TABLE_ORDER_PRODUCT + " ( ");
		sql.append(OrderProduct.COLUMN_ORDER_ID + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_ID + ", ");
		sql.append(OrderProduct.COLUMN_CATEGORY_ID + ", ");
		sql.append(OrderProduct.COLUMN_CATEGORY_NAME + ", ");
		sql.append(OrderProduct.COLUMN_BRAND_ID + ", ");
		sql.append(OrderProduct.COLUMN_BRAND_NAME + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_NAME + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_IMAGE + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_IS_NEW + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_IS_RECOMMEND + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_IS_BARGAIN + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_IS_HOT + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_IS_SELL_OUT + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_IS_SALE + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_CONTENT + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_SKU_VALUE + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_SKU_ID + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_ATTRIBUTE_VALUE + ", ");
		sql.append(OrderProduct.COLUMN_PRODUCT_PRICE + ", ");
		sql.append(OrderProduct.COLUMN_MEMBER_LEVEL_PRICE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_PAY_PRICE + ", ");
		sql.append(OrderProduct.KEY_PRODUCT_PAY_AMOUNT + ", ");
		sql.append(OrderProduct.COLUMN_SYSTEM_CREATE_USER_ID + ", ");
		sql.append(OrderProduct.COLUMN_SYSTEM_CREATE_TIME + ", ");
		sql.append(OrderProduct.COLUMN_SYSTEM_UPDATE_USER_ID + ", ");
		sql.append(OrderProduct.COLUMN_SYSTEM_UPDATE_TIME + ", ");
		sql.append(OrderProduct.COLUMN_SYSTEM_STATUS + " ");
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
		orderProduct.initForUpdate(request_user_id);

		orderProduct.update();
	}

	public void delete(String order_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + OrderProduct.TABLE_ORDER_PRODUCT + " ");
		myDynamicSQL.append("SET " + OrderProduct.COLUMN_SYSTEM_STATUS + " = 0, ");
		myDynamicSQL.append(OrderProduct.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(OrderProduct.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + OrderProduct.COLUMN_ORDER_ID + " = ? ", order_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}

package com.hongluomeng.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Const;
import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.model.Category;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Order;
import com.hongluomeng.type.OrderEnum;

public class OrderDao {

    private Integer count(Order order) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT COUNT(*) FROM " + Order.TABLE_ORDER + " ");
        myDynamicSQL.append("WHERE " + Order.TABLE_ORDER + "." + Order.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Order.COLUMN_ORDER_NO + " = ? ", order.getOrder_no());

        Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
        return count.intValue();
    }

    public Integer count() {
        Order order = new Order();

        return count(order);
    }

    private List<Order> list(Order order, Integer m, Integer n) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        String order_flow_status = order.getOrder_status();
        if (order_flow_status.equals(OrderEnum.WAIT.getKey())) {
            order_flow_status += "," + OrderEnum.CONFIRM.getKey();
        }

        myDynamicSQL.append("SELECT * FROM " + Order.TABLE_ORDER + " ");
        myDynamicSQL.append("WHERE " + Order.TABLE_ORDER + "." + Order.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Order.COLUMN_USER_ID + " = ? ", order.getUser_id());
        myDynamicSQL.isNullOrEmptyForSplit(Order.COLUMN_ORDER_STATUS + " = ? ", order_flow_status);
        myDynamicSQL.append("ORDER BY " + Order.TABLE_ORDER + "." + Order.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
        myDynamicSQL.appendPagination(m, n);

        return new Order().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

    public List<Order> list(Integer m, Integer n) {
        Order order = new Order();
        order.setOrder_status("");

        return list(order, m, n);
    }

    public List<Order> listByUser_idAndOrder_flow_status(String user_id, String order_flow_status, Integer m, Integer n) {
        Order order = new Order();
        order.setUser_id(user_id);
        order.setOrder_status(order_flow_status);

        return list(order, m, n);
    }

    private Order find(Order order) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT " + Order.TABLE_ORDER + ".*, ");
        myDynamicSQL.append("province." + Category.COLUMN_CATEGORY_NAME + " AS " + Order.COLUMN_ORDER_DELIVERY_PROVINCE + ", ");
        myDynamicSQL.append("city." + Category.COLUMN_CATEGORY_NAME + " AS " + Order.COLUMN_ORDER_DELIVERY_CITY + ", ");
        myDynamicSQL.append("area." + Category.COLUMN_CATEGORY_NAME + " AS " + Order.COLUMN_ORDER_DELIVERY_AREA + " ");
        myDynamicSQL.append("FROM " + Order.TABLE_ORDER + " ");
        myDynamicSQL.append("LEFT JOIN " + Category.TABLE_CATEGORY + " AS province ON province." + Category.COLUMN_CATEGORY_ID + " = " + Order.TABLE_ORDER + "." + Order.COLUMN_ORDER_DELIVERY_PROVINCE + " ");
        myDynamicSQL.append("LEFT JOIN " + Category.TABLE_CATEGORY + " AS city ON city." + Category.COLUMN_CATEGORY_ID + " = " + Order.TABLE_ORDER + "." + Order.COLUMN_ORDER_DELIVERY_CITY + " ");
        myDynamicSQL.append("LEFT JOIN " + Category.TABLE_CATEGORY + " AS area ON area." + Category.COLUMN_CATEGORY_ID + " = " + Order.TABLE_ORDER + "." + Order.COLUMN_ORDER_DELIVERY_AREA + " ");
        myDynamicSQL.append("WHERE " + Order.TABLE_ORDER + "." + Order.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Order.COLUMN_ORDER_ID + " = ? ", order.getOrder_id());

        System.out.println(myDynamicSQL.sql.toString());

        List<Order> orderList = new Order().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
        if (orderList.size() == 0) {
            return null;
        } else {
            return orderList.get(0);
        }
    }

    public Order findByOrder_id(String order_id) {
        Order order = new Order();
        order.setOrder_id(order_id);

        order.checkOrder_id();

        return find(order);
    }

    private Boolean checkOrder_no(String order_no) {
        Order order = new Order();
        order.setOrder_no(order_no);

        order.checkOrder_no();

        Integer count = count(order);

        return count == 0;
    }

    private String getOrder_no() {
        return Const.ORDER_NO_HEADER + Utility.getDateString(new Date()).replaceAll("-", "") + Utility.getFixLenthString(6);
    }

    public void save(Order order, String member_level_id, String member_level_name, Integer member_level_value, String request_user_id) {
        String order_no = getOrder_no();

        Boolean isExit = true;

        while (isExit) {
            if (checkOrder_no(order_no)) {
                isExit = false;
            } else {
                order_no = getOrder_no();
            }
        }

        order.setOrder_id(Utility.getUUID());
        order.setUser_id(request_user_id);
        order.setOrder_no(order_no);
        if (Utility.isNullOrEmpty(order.getOrder_message())) {
            order.setOrder_message("");
        }
        if (Utility.isNullOrEmpty(order.getOrder_delivery_zip())) {
            order.setOrder_delivery_zip("");
        }
        order.setOrder_is_pay(false);
        order.setOrder_trade_no("");
        order.setOrder_trade_account("");
        order.setOrder_trade_price(BigDecimal.ZERO);
        order.setOrder_trade_time("");
        order.setOrder_trade_result("");
        order.setMember_level_id(member_level_id);
        order.setMember_level_name(member_level_name);
        order.setMember_level_value(member_level_value);
        order.setOrder_status(OrderEnum.WAIT.getKey());

        order.initForSave(request_user_id);

        order.save();
    }

    public void update(Order order, String request_user_id) {
        order.initForUpdate(request_user_id);

        order.update();
    }

    public void payed(String order_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("UPDATE " + Order.TABLE_ORDER + " ");
        myDynamicSQL.append("SET " + Order.COLUMN_ORDER_STATUS + " = ? ", OrderEnum.CONFIRM.getKey());
        myDynamicSQL.append("WHERE " + Order.COLUMN_ORDER_ID + " = ? ", order_id);
        myDynamicSQL.append("AND " + Order.COLUMN_ORDER_STATUS + " = ? ", OrderEnum.WAIT.getKey());

        Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

    public int updateTrade(String order_no, String order_trade_no, String order_trade_account, String order_trade_price, String order_pay_result) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("UPDATE " + Order.TABLE_ORDER + " ");
        myDynamicSQL.append("SET " + Order.COLUMN_ORDER_IS_PAY + " = 1, ");
        myDynamicSQL.append(Order.COLUMN_ORDER_TRADE_NO + " = ?, ", order_trade_no);
        myDynamicSQL.append(Order.COLUMN_ORDER_TRADE_ACCOUNT + " = ?, ", order_trade_account);
        myDynamicSQL.append(Order.COLUMN_ORDER_TRADE_PRICE + " = ?, ", order_trade_price);
        myDynamicSQL.append(Order.COLUMN_ORDER_TRADE_TIME + " = ?, ", Utility.getDateTimeString(new Date()));
        myDynamicSQL.append(Order.COLUMN_ORDER_TRADE_RESULT + " = ?, ", order_pay_result);
        myDynamicSQL.append(Order.COLUMN_ORDER_STATUS + " = ? ", OrderEnum.PAYED.getKey());
        myDynamicSQL.append("WHERE " + Order.COLUMN_ORDER_NO + " = ? ", order_no);

        return Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

    public void delete(String order_id, String request_user_id) {
        Order order = new Order();
        order.setOrder_id(order_id);

        order.initForDelete(request_user_id);

        order.update();
    }

}

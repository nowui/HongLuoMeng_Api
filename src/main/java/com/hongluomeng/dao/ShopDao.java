package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Shop;

public class ShopDao {

	private Integer count(Shop shop) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Shop.KEY_TABLE_SHOP + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Shop.KEY_SHOP_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Shop shop = new Shop();

		return count(shop);
	}

	private List<Shop> list(Shop shop, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Shop.KEY_TABLE_SHOP + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Shop.KEY_SHOP_STATUS + " = 1 ");

		sql.append("ORDER BY " + Shop.KEY_SHOP_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Shop> shopList = shop.find(sql.toString(), parameterList.toArray());
		return shopList;
	}

	public List<Shop> list(Integer m, Integer n) {
		Shop shop = new Shop();

		return list(shop, m, n);
	}

	private Shop find(Shop shop) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Shop.KEY_TABLE_SHOP + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(shop.getShop_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Shop.KEY_SHOP_ID + " = ? ");
			parameterList.add(shop.getShop_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Shop.KEY_SHOP_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Shop> shopList = shop.find(sql.toString(), parameterList.toArray());
		if(shopList.size() == 0) {
			return null;
		} else {
			return shopList.get(0);
		}
	}

	public Shop findByShop_id(String shop_id) {
		Shop shop = new Shop();
		shop.setShop_id(shop_id);

		return find(shop);
	}

	public void save(Shop shop, String request_user_id) {
		shop.setShop_id(Utility.getUUID());
		shop.setShop_create_user_id(request_user_id);
		shop.setShop_create_time(new Date());
		shop.setShop_update_user_id(request_user_id);
		shop.setShop_update_time(new Date());
		shop.setShop_status(true);

		shop.save();
	}

	public void update(Shop shop, String request_user_id) {
		shop.remove(Shop.KEY_SHOP_CREATE_USER_ID);
		shop.remove(Shop.KEY_SHOP_CREATE_TIME);
		shop.setShop_update_user_id(request_user_id);
		shop.setShop_update_time(new Date());

		shop.update();
	}

	public void delete(String shop_id, String request_user_id) {
		Shop shop = new Shop();
		shop.setShop_id(shop_id);
		shop.setShop_update_user_id(request_user_id);
		shop.setShop_update_time(new Date());
		shop.setShop_status(false);

		shop.update();
	}

}

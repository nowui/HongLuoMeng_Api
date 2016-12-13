package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class ProductCollect extends Base<ProductCollect> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_PRODUCT_COLLECT = "table_product_collect";
	public static final String COLUMN_PRODUCT_COLLECT_ID = "product_collect_id";
	public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_USER_ID = "user_id";

	public String gettProduct_collect_id() {
		return getStr(COLUMN_PRODUCT_COLLECT_ID);
	}

	public void setProduct_collect_id(String product_collect_id) {
		set(COLUMN_PRODUCT_COLLECT_ID, product_collect_id);
	}

	public void checkProduct_collect_id() {
		Utility.checkStringLength(gettProduct_collect_id(), 32, "商品收藏编号");
	}

	public String getProduct_id() {
		return getStr(COLUMN_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(COLUMN_PRODUCT_ID, product_id);
	}

    public void checkProduct_id() {
        Utility.checkStringLength(getProduct_id(), 32, "商品编号");
    }

    public String getUser_id() {
        return getStr(COLUMN_USER_ID);
    }

    public void setUser_id(String user_id) {
        set(COLUMN_USER_ID, user_id);
    }

    public void checkUser_id() {
        Utility.checkStringLength(getUser_id(), 32, "用户编号");
    }

    public Member getMember() {
        return new Member().put(this);
    }

    public Product getProduct() {
        return new Product().put(this);
    }
}

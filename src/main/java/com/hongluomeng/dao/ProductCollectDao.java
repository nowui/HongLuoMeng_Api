package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.Product;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.ProductCollect;

public class ProductCollectDao extends BaseDao {

    private Integer count(ProductCollect productCollect) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT COUNT(*) FROM " + ProductCollect.TABLE_PRODUCT_COLLECT + " ");
        myDynamicSQL.append("WHERE " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_PRODUCT_ID + " = ? ", productCollect.getProduct_id());
        myDynamicSQL.isNullOrEmpty("AND " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_USER_ID + " = ? ", productCollect.getUser_id());

        Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
        return count.intValue();
    }

    public Integer count() {
        ProductCollect productCollect = new ProductCollect();

        return count(productCollect);
    }

    public Integer countByProduct_idAndUser_id(String product_id, String user_id) {
        ProductCollect productCollect = new ProductCollect();
        productCollect.setProduct_id(product_id);
        productCollect.setUser_id(user_id);

        return count(productCollect);
    }

	private List<ProductCollect> list(ProductCollect productCollect, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT " + ProductCollect.TABLE_PRODUCT_COLLECT + ".*, ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_ID + ", ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_NAME + ", ");
        myDynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_ID + ", ");
        myDynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_NAME + ", ");
        myDynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_PRICE + ", ");
        myDynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_IMAGE + " ");
        myDynamicSQL.append("FROM " + ProductCollect.TABLE_PRODUCT_COLLECT + " ");
        myDynamicSQL.append("LEFT JOIN " + Member.TABLE_MEMBER + " ON " + Member.TABLE_MEMBER + "." + Member.COLUMN_USER_ID + " = " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_USER_ID + " ");
        myDynamicSQL.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_ID + " = " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_PRODUCT_ID + " ");
		myDynamicSQL.append("WHERE " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_PRODUCT_ID + " = ? ", productCollect.getProduct_id());
        myDynamicSQL.isNullOrEmpty("AND " + ProductCollect.TABLE_PRODUCT_COLLECT + "." + ProductCollect.COLUMN_USER_ID + " = ? ", productCollect.getUser_id());
		myDynamicSQL.append("ORDER BY " + ProductCollect.COLUMN_SYSTEM_CREATE_TIME + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return new ProductCollect().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

    public List<ProductCollect> list(Integer m, Integer n) {
        ProductCollect productCollect = new ProductCollect();

        return list(productCollect, m, n);
    }

	public List<ProductCollect> listByUser_id(String user_id, Integer m, Integer n) {
		ProductCollect productCollect = new ProductCollect();
		productCollect.setUser_id(user_id);

        return list(productCollect, m, n);
	}

    private ProductCollect find(ProductCollect productCollect) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT * FROM " + ProductCollect.TABLE_PRODUCT_COLLECT + " ");
        myDynamicSQL.append("WHERE " + ProductCollect.COLUMN_PRODUCT_ID + " = ? ", productCollect.getProduct_id());
        myDynamicSQL.append("AND " + ProductCollect.COLUMN_USER_ID + " = ? ", productCollect.getUser_id());

        List<ProductCollect> productCollectList = productCollect.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
        if(productCollectList.size() == 0) {
            return null;
        } else {
            return productCollectList.get(0);
        }
    }

    public ProductCollect findByProduct_idAndUser_id(String product_id, String user_id) {
        ProductCollect productCollect = new ProductCollect();
        productCollect.setProduct_id(product_id);
        productCollect.setUser_id(user_id);

        return find(productCollect);
    }

	public void save(ProductCollect productCollect, String request_user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("INSERT INTO " + ProductCollect.TABLE_PRODUCT_COLLECT + " (");
        myDynamicSQL.append(ProductCollect.COLUMN_PRODUCT_COLLECT_ID + ", ");
        myDynamicSQL.append(ProductCollect.COLUMN_PRODUCT_ID + ", ");
        myDynamicSQL.append(ProductCollect.COLUMN_USER_ID + ", ");
        myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_CREATE_USER_ID + ", ");
        myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_CREATE_TIME + ", ");
        myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_UPDATE_USER_ID + ", ");
        myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_UPDATE_TIME + ", ");
        myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_STATUS);
        myDynamicSQL.append(") SELECT ");
        myDynamicSQL.append("?, ", Utility.getUUID());
        myDynamicSQL.append("?, ", productCollect.getProduct_id());
        myDynamicSQL.append("?, ", request_user_id);
        myDynamicSQL.append("?, ", request_user_id);
        myDynamicSQL.append("?, ", new Date());
        myDynamicSQL.append("?, ", request_user_id);
        myDynamicSQL.append("?, ", new Date());
        myDynamicSQL.append("? ", 1);
        myDynamicSQL.append("FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + ProductCollect.TABLE_PRODUCT_COLLECT + " WHERE " + ProductCollect.COLUMN_PRODUCT_ID + " = ? AND " + ProductCollect.COLUMN_USER_ID + " = ?) ", productCollect.getProduct_id(), request_user_id);

        Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

    public void delete(String topic_comment_id, String request_user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("UPDATE " + ProductCollect.TABLE_PRODUCT_COLLECT + " ");
        myDynamicSQL.append("SET " + ProductCollect.COLUMN_SYSTEM_STATUS + " = 0, ");
        myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
        myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
        myDynamicSQL.append("WHERE " + ProductCollect.COLUMN_PRODUCT_COLLECT_ID + " = ? ", topic_comment_id);
        myDynamicSQL.append("AND " + ProductCollect.COLUMN_SYSTEM_CREATE_USER_ID + " = ? ", request_user_id);

        Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

	public void deleteByProduct_id(String product_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + ProductCollect.TABLE_PRODUCT_COLLECT + " ");
		myDynamicSQL.append("SET " + ProductCollect.COLUMN_SYSTEM_STATUS + " = 0, ");
		myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(ProductCollect.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + ProductCollect.COLUMN_PRODUCT_ID + " = ? ", product_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}

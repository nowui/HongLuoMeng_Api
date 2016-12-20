package com.hongluomeng.dao;

import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Page;
import com.jfinal.plugin.activerecord.Record;

public class PageDao extends BaseDao {

	private Integer count(Page page) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + Page.TABLE_PAGE + " ");
		myDynamicSQL.append("WHERE " + Page.TABLE_PAGE + "." + Page.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Page page = new Page();

		return count(page);
	}

	private List<Page> list(Page page, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Page.TABLE_PAGE + " ");
		myDynamicSQL.append("WHERE " + Page.TABLE_PAGE + "." + Page.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + Page.TABLE_PAGE + "." + Page.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		List<Page> pageList = new Page().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

		return pageList;
	}

	public List<Page> list(Integer m, Integer n) {
		Page page = new Page();

		return list(page, m, n);
	}

	private Page find(Page page) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Page.TABLE_PAGE + " ");
		myDynamicSQL.append("WHERE " + Page.TABLE_PAGE + "." + Page.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Page.COLUMN_PAGE_ID + " = ? ", page.getPage_id());
        myDynamicSQL.isNullOrEmpty("AND " + Page.COLUMN_PAGE_KEY + " = ? ", page.getPage_key());

		List<Record> recordList = Db.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (recordList.size() == 0) {
			return null;
		} else {
			Page pageModel = new Page().put(recordList.get(0));
			return pageModel;
		}
	}

    public Page findByPage_id(String page_id) {
        Page page = new Page();
        page.setPage_id(page_id);

        page.checkPage_id();

        return find(page);
    }

    public Page findByPage_key(String page_key) {
        Page page = new Page();
        page.setPage_key(page_key);

        return find(page);
    }

	public void save(Page page, String request_user_id) {
		page.setPage_id(Utility.getUUID());

		page.initForSave(request_user_id);

		page.save();
	}

	public void update(Page page, String request_user_id) {
		page.initForUpdate(request_user_id);

		page.update();
	}

	public void delete(String page_id, String request_user_id) {
		Page page = new Page();
		page.setPage_id(page_id);

        page.initForDelete(request_user_id);

		page.update();
	}

}

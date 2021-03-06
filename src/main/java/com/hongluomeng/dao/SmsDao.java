package com.hongluomeng.dao;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Sms;

public class SmsDao extends BaseDao {

	private Integer count(Sms sms, Integer minute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Sms.TABLE_SMS + " ");
		myDynamicSQL.append("WHERE " + Sms.COLUMN_SYSTEM_CREATE_TIME + " > DATE_SUB(NOW(), INTERVAL " + minute + " MINUTE) ");
		myDynamicSQL.isNullOrEmpty("AND " + Sms.COLUMN_SMS_TYPE + " = ? ", sms.getSms_type());
		myDynamicSQL.isNullOrEmpty("AND " + Sms.COLUMN_SMS_PHONE + " = ? ", sms.getSms_phone());
		myDynamicSQL.isNullOrEmpty("AND " + Sms.COLUMN_SMS_CODE + " = ? ", sms.getSms_code());
		myDynamicSQL.append("AND " + Sms.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countBySms_phoneAndMinute(String sms_type, String sms_phone, Integer minute) {
		Sms sms = new Sms();
		sms.setSms_type(sms_type);
		sms.setSms_phone(sms_phone);

		return count(sms, minute);
	}

	public Integer countBySms_phoneAndSms_codeAndSms_statusAndMinute(String sms_type, String sms_phone, String sms_code, Integer minute) {
		Sms sms = new Sms();
		sms.setSms_type(sms_type);
		sms.setSms_phone(sms_phone);
		sms.setSms_code(sms_code);

		return count(sms, minute);
	}

	public Integer countBySms_typeAndSms_codeAndMinute(String sms_type, String sms_code, Integer minute) {
		Sms sms = new Sms();
		sms.setSms_type(sms_type);
		sms.setSms_code(sms_code);

		return count(sms, minute);
	}

	private List<Sms> list(Sms sms, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Sms.TABLE_SMS + " ");
		myDynamicSQL.append("ORDER BY " + Sms.TABLE_SMS + "." + Sms.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new Sms().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Sms> list(Integer m, Integer n) {
		Sms sms = new Sms();

		return list(sms, m, n);
	}

	private Sms find(Sms sms) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Sms.TABLE_SMS + " ");
		myDynamicSQL.append("WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Sms.COLUMN_SMS_ID + " = ? ", sms.getSms_id());

		List<Sms> smsList = new Sms().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if(smsList.size() == 0) {
			return null;
		} else {
			return smsList.get(0);
		}
	}

//	public Sms findBySms_id(String sms_id) {
//		Sms sms = new Sms();
//		sms.setSms_id(sms_id);
//
//		sms.checkSms_id();
//
//		return find(sms);
//	}

	public void save(Sms sms) {
		sms.setSms_id(Utility.getUUID());

		sms.initForSave("");

		sms.save();
	}

	public void updateSms_statusBySms_phone(String sms_type, String sms_phone, String sms_code) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + Sms.TABLE_SMS + " ");
		myDynamicSQL.append("SET " + Sms.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("SET " + Sms.COLUMN_SYSTEM_STATUS + " = ? ", 0);
		myDynamicSQL.append("WHERE " + Sms.COLUMN_SMS_TYPE + " = ? ", sms_type);
		myDynamicSQL.append("AND " + Sms.COLUMN_SMS_PHONE + " = ? ", sms_phone);
		myDynamicSQL.append("AND " + Sms.COLUMN_SMS_CODE + " = ? ", sms_code);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}

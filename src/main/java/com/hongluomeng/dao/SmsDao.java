package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Sms;

public class SmsDao {

	private Integer count(Sms sms, Integer minute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Sms.KEY_SMS + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(sms.getSms_type())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Sms.KEY_SMS_TYPE + " = ? ");
			parameterList.add(sms.getSms_type());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(sms.getSms_phone())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Sms.KEY_SMS_PHONE + " = ? ");
			parameterList.add(sms.getSms_phone());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(sms.getSms_code())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Sms.KEY_SMS_CODE + " = ? ");
			parameterList.add(sms.getSms_code());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(sms.getSms_status())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Sms.KEY_SMS_STATUS + " = ? ");
			if (sms.getSms_status()) {
				parameterList.add(1);
			} else {
				parameterList.add(0);
			}

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(minute)) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Sms.KEY_SMS_CREATE_TIME + " > DATE_SUB(NOW(), INTERVAL " + minute + " MINUTE) ");

			isExit = true;
		}

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer countBySms_phoneAndMinute(String sms_type, String sms_phone, Integer minute) {
		Sms sms = new Sms();
		sms.setSms_type(sms_type);
		sms.setSms_phone(sms_phone);

		return count(sms, minute);
	}

	public Integer countBySms_phoneAndSms_codeAndSms_statusAndMinute(String sms_type, String sms_phone, String sms_code, Boolean sms_status, Integer minute) {
		Sms sms = new Sms();
		sms.setSms_type(sms_type);
		sms.setSms_phone(sms_phone);
		sms.setSms_code(sms_code);
		sms.setSms_status(sms_status);

		return count(sms, minute);
	}

	private List<Sms> list(Sms sms, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Sms.KEY_SMS + " ");
		sql.append("ORDER BY " + Sms.KEY_SMS_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Sms> smsList = sms.find(sql.toString(), parameterList.toArray());
		return smsList;
	}

	public List<Sms> list(Integer m, Integer n) {
		Sms sms = new Sms();

		return list(sms, m, n);
	}

	private Sms find(Sms sms) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Sms.KEY_SMS + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(sms.getSms_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Sms.KEY_SMS_ID + " = ? ");
			parameterList.add(sms.getSms_id());

			isExit = true;
		}

		if(! isExit) {
			return null;
		}

		List<Sms> smsList = sms.find(sql.toString(), parameterList.toArray());
		if(smsList.size() == 0) {
			return null;
		} else {
			return smsList.get(0);
		}
	}

	public Sms findBySms_id(String sms_id) {
		Sms sms = new Sms();
		sms.setSms_id(sms_id);

		return find(sms);
	}

	public void save(Sms sms) {
		sms.setSms_id(Utility.getUUID());
		sms.setSms_create_time(new Date());
		sms.setSms_status(false);

		sms.save();
	}

	public void updateSms_statusBySms_phone(Boolean sms_status, String sms_type, String sms_phone, String sms_code) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + Sms.KEY_SMS + " ");
		sql.append("SET " + Sms.KEY_SMS_STATUS + " = ? ");
		sql.append("WHERE " + Sms.KEY_SMS_TYPE + " = ? ");
		sql.append("AND " + Sms.KEY_SMS_PHONE + " = ? ");
		sql.append("AND " + Sms.KEY_SMS_CODE + " = ? ");

		if (sms_status) {
			parameterList.add(1);
		} else {
			parameterList.add(0);
		}
		parameterList.add(sms_type);
		parameterList.add(sms_phone);
		parameterList.add(sms_code);

		Db.update(sql.toString(), parameterList.toArray());
	}

}

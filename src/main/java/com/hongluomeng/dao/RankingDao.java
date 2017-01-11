package com.hongluomeng.dao;

import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Ranking;
import com.jfinal.plugin.activerecord.Record;

public class RankingDao extends BaseDao {

	private Integer count(Ranking ranking) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + Ranking.TABLE_RANKING + " ");
		myDynamicSQL.append("WHERE " + Ranking.TABLE_RANKING + "." + Ranking.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Ranking.COLUMN_RANKING_TYPE + " = ? ", ranking.getRanking_type());

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByRanking_type(String ranking_type) {
		Ranking ranking = new Ranking();
        ranking.setRanking_type(ranking_type);

		return count(ranking);
	}

	private List<Ranking> list(Ranking ranking, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Ranking.TABLE_RANKING + " ");
		myDynamicSQL.append("WHERE " + Ranking.TABLE_RANKING + "." + Ranking.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Ranking.COLUMN_RANKING_TYPE + " = ? ", ranking.getRanking_type());
		myDynamicSQL.append("ORDER BY " + Ranking.TABLE_RANKING + "." + Ranking.COLUMN_RANKING_TYPE + " DESC, " + Ranking.TABLE_RANKING + "." + Ranking.COLUMN_RANKING_SORT + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		List<Ranking> rankingList = new Ranking().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

		return rankingList;
	}

    public List<Ranking> listByRanking_type(String ranking_type, Integer m, Integer n) {
        Ranking ranking = new Ranking();
        ranking.setRanking_type(ranking_type);

        return list(ranking, m, n);
    }

	private Ranking find(Ranking ranking) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Ranking.TABLE_RANKING + " ");
		myDynamicSQL.append("WHERE " + Ranking.TABLE_RANKING + "." + Ranking.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Ranking.COLUMN_RANKING_ID + " = ? ", ranking.getRanking_id());

		List<Record> recordList = Db.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (recordList.size() == 0) {
			return null;
		} else {
			Ranking rankingModel = new Ranking().put(recordList.get(0));
			return rankingModel;
		}
	}

	public Ranking findByRanking_id(String ranking_id) {
		Ranking ranking = new Ranking();
		ranking.setRanking_id(ranking_id);

		ranking.checkRanking_id();

		return find(ranking);
	}

	public void save(Ranking ranking, String request_user_id) {
		ranking.setRanking_id(Utility.getUUID());

		ranking.initForSave(request_user_id);

		ranking.save();
	}

	public void update(Ranking ranking, String request_user_id) {
		ranking.initForUpdate(request_user_id);

		ranking.update();
	}

    public void updateRanking_hits(String ranking_id, int ranking_hits, String request_user_id) {
        Ranking ranking = new Ranking();
        ranking.setRanking_id(ranking_id);

        ranking.initForUpdate(request_user_id);

        ranking.setRanking_hits(ranking_hits + 1);

        ranking.update();
    }

	public void delete(String ranking_id, String request_user_id) {
		Ranking ranking = new Ranking();
		ranking.setRanking_id(ranking_id);

        ranking.initForDelete(request_user_id);

		ranking.update();
	}

}

package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Ranking extends Base<Ranking> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_RANKING = "table_ranking";
	public static final String COLUMN_RANKING_ID = "ranking_id";
    public static final String COLUMN_RANKING_TYPE = "ranking_type";
    public static final String COLUMN_RANKING_NAME = "ranking_name";
    public static final String COLUMN_RANKING_TITLE = "ranking_title";
    public static final String COLUMN_RANKING_SOURCE = "ranking_source";
	public static final String COLUMN_RANKING_IMAGE = "ranking_image";
	public static final String COLUMN_RANKING_CONTENT = "ranking_content";
    public static final String COLUMN_RANKING_HITS= "ranking_hits";
    public static final String COLUMN_RANKING_SORT= "ranking_sort";

	public String getRanking_id() {
		return getStr(COLUMN_RANKING_ID);
	}

	public void setRanking_id(String ranking_id) {
		set(COLUMN_RANKING_ID, ranking_id);
	}

	public void checkRanking_id() {
		Utility.checkStringLength(getRanking_id(), 32, "排名编号");
	}

    public String getRanking_type() {
        return getStr(COLUMN_RANKING_TYPE);
    }

    public void setRanking_type(String ranking_type) {
        set(COLUMN_RANKING_TYPE, ranking_type);
    }

    public void checkRanking_type() {
        Utility.checkStringLength(getRanking_type(), 0, 20, "排名类型");
    }

    public String getRanking_name() {
        return getStr(COLUMN_RANKING_NAME);
    }

    public void setRanking_name(String ranking_name) {
        set(COLUMN_RANKING_NAME, ranking_name);
    }

    public void checkRanking_name() {
        Utility.checkStringLength(getRanking_name(), 1, 20, "排名名称");
    }

    public String getRanking_title() {
        return getStr(COLUMN_RANKING_TITLE);
    }

    public void setRanking_title(String ranking_title) {
        set(COLUMN_RANKING_TITLE, ranking_title);
    }

    public void checkRanking_title() {
        Utility.checkStringLength(getRanking_title(), 0, 30, "排名URL");
    }

    public String getRanking_source() {
        return getStr(COLUMN_RANKING_SOURCE);
    }

    public void setRanking_source(String ranking_source) {
        set(COLUMN_RANKING_SOURCE, ranking_source);
    }

    public void checkRanking_source() {
        Utility.checkStringLength(getRanking_source(), 0, 30, "排名来源");
    }

	public String getRanking_image() {
		return getStr(COLUMN_RANKING_IMAGE);
	}

	public void setRanking_image(String brand_image) {
		set(COLUMN_RANKING_IMAGE, brand_image);
	}

	public void checkRanking_image() {
		Utility.checkStringLength(getRanking_image(), 0, 100, "品牌图片");
	}

	public String getRanking_content() {
		return getStr(COLUMN_RANKING_CONTENT);
	}

	public void setRanking_content(String ranking_content) {
		set(COLUMN_RANKING_CONTENT, ranking_content);
	}

	public void checkRanking_content() {
		Utility.checkStringLength(getRanking_content(), 0, 0, "排名内容");
	}

    public Integer getRanking_hits() {
        return Utility.getIntegerValue(get(COLUMN_RANKING_HITS));
    }

    public void setRanking_hits(Integer ranking_hits) {
        set(COLUMN_RANKING_HITS, ranking_hits);
    }

    public void checkRanking_hits() {
        Utility.checkIntegerLength(getRanking_hits(), 1, 11, "排名点击量");
    }

    public Integer getRanking_sort() {
        return Utility.getIntegerValue(get(COLUMN_RANKING_SORT));
    }

    public void setRanking_sort(Integer ranking_sort) {
        set(COLUMN_RANKING_SORT, ranking_sort);
    }

    public void checkRanking_sort() {
        Utility.checkIntegerLength(getRanking_sort(), 1, 11, "排名");
    }

}

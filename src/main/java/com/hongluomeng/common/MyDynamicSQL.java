package com.hongluomeng.common;

import java.util.ArrayList;
import java.util.List;

public class MyDynamicSQL {
    public StringBuilder sql = new StringBuilder();

    public List<Object> parameterList = new ArrayList<Object>();

    public void append(String string) {
        this.sql.append(string);
    }

    public void append(String string, Object... objects) {
        this.sql.append(string);

        for (Object object : objects) {
            this.parameterList.add(object);
        }
    }

    public void appendPagination(Integer m, Integer n) {
        if (n > 0) {
            this.sql.append("LIMIT ?, ? ");
            this.parameterList.add(m);
            this.parameterList.add(n);
        }
    }

    public void isTrueOrFalse(String string, Boolean object) {
        if (object) {
            this.sql.append(string);

            this.parameterList.add(object);
        }
    }

    public void isNullOrEmpty(String string, Object object) {
        if (!Utility.isNullOrEmpty(object)) {
            this.sql.append(string);

            this.parameterList.add(object);
        }
    }

    public void isNullOrEmptyForLike(String string, Object object) {
        if (!Utility.isNullOrEmpty(object)) {
            this.sql.append(string);

            this.parameterList.add("%" + object + "%");
        }
    }

    public void isNullOrEmptyForOther(String string, Object object1, Object object2) {
        if (!Utility.isNullOrEmpty(object1)) {
            this.sql.append(string);

            this.parameterList.add(object2);
        }
    }

    public void isNullOrEmptyForSplit(String string, String object) {
        if (!Utility.isNullOrEmpty(object)) {
            String[] array = object.split(",");

            if(array.length == 1) {
                this.sql.append("AND " + string);

                this.parameterList.add(object);
            } else {
                for(int i = 0; i < array.length; i++) {
                    if(i == 0) {
                        this.sql.append("AND(");
                    } else {
                        this.sql.append("OR ");
                    }
                    this.sql.append(string);

                    this.parameterList.add(array[i]);
                }

                this.sql.append(") ");
            }
        }
    }

}

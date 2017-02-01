package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum Sex {
    //未知
    UNKNOWN(0),
    //男性
    MALE(1),
    //女性
    FEMALE(2);

    private Integer value;

    Sex(final Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return  value;
    }
}

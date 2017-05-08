package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum Gender {
    //未知
    UNKNOWN(0),
    //男性
    MALE(1),
    //女性
    FEMALE(2);

    private Integer value;

    Gender(final Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return  value;
    }

    public static Gender valueOf(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        return null;
    }
}

package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum UserStatus {
    //禁用
    DISABLED(0),
    //正常
    NORMAL(1);

    private Integer value;

    UserStatus(final Integer value) {
        this.value = value;
    }
}

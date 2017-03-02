package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum SmsSecurityCodeStatus {
    //禁用
    DISABLED(0),
    //正常
    NORMAL(1);

    private Integer value;

    SmsSecurityCodeStatus(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

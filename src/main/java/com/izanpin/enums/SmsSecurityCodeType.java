package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum SmsSecurityCodeType {
    //登录
    LOGIN(0),
    //找回密码
    RETRIEVE_PASSWORD(1);

    private Integer value;

    SmsSecurityCodeType(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum ResultStatus {
    //错误
    ERROR(-2),
    //失败
    FAILED(-1),
    //成功
    SUCCESSFUL(1);

    private Integer value;

    ResultStatus(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

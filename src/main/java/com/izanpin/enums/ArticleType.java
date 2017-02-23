package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum ArticleType {
    //无聊图
    PICTURE(0),
    //段子
    JOKE(1);

    private Integer value;

    ArticleType(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

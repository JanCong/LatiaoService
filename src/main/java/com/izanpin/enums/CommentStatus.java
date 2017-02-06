package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum CommentStatus {
    //禁用
    DISABLED(-2),
    //审核未通过
    REJECTED(-1),
    //审核中
    INREVIEW(0),
    //正常
    NORMAL(1),
    //置顶
    TOP(2);

    private Integer value;

    CommentStatus(final Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return  value;
    }
}

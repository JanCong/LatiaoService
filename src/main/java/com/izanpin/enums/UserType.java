package com.izanpin.enums;

/**
 * Created by pengyuancong on 2017/1/29.
 */
public enum UserType {
    //正常
    NORMAL(0),
    //机器人
    ROBOT(1),
    //系统
    SYSTEM(2),
    //管理员
    ADMIN(3);


    private Integer value;

    UserType(final Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return  value;
    }
}

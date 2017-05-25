package com.izanpin.enums;

/**
 * Created by St on 2017/5/5.
 */
public enum UserFriendStatus {
    //黑名单
    BLACKLIST(-2),
    //拒绝的
    REJECTIVE(-1),
    //未验证
    UNVERIFIED(0),
    //正常
    NORMAL(1),
    //特别关心
    SPECIAL(4);

    private Integer value;

    UserFriendStatus(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static UserFriendStatus valueOf(int value) {
        for (UserFriendStatus oauthPlatformType : UserFriendStatus.values()) {
            if (oauthPlatformType.getValue() == value) {
                return oauthPlatformType;
            }
        }
        return null;
    }
}

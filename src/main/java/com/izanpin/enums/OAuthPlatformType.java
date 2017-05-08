package com.izanpin.enums;

/**
 * Created by St on 2017/5/5.
 */
public enum OAuthPlatformType {
    //新浪
    SINA(0),
    //微信
    WECHAT(1),
    //QQ
    QQ(4);

    private Integer value;

    OAuthPlatformType(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static OAuthPlatformType valueOf(int value) {
        for (OAuthPlatformType oauthPlatformType : OAuthPlatformType.values()) {
            if (oauthPlatformType.getValue() == value) {
                return oauthPlatformType;
            }
        }
        return null;
    }
}

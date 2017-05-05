package com.izanpin.dto;

import com.izanpin.enums.OAuthPlatformType;

/**
 * Created by St on 2017/3/2.
 */
public class OAuthLoginDto {
    private String openId;
    private OAuthPlatformType platformType;
    private String nickname;
    private String iconUrl;
    private String gender;
    private String device;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public OAuthPlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(OAuthPlatformType platformType) {
        this.platformType = platformType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}

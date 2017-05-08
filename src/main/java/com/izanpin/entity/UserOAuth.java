package com.izanpin.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.izanpin.common.util.SnowFlake;

import java.util.Date;

/**
 * Created by St on 2017/5/8.
 */
public class UserOAuth {
    public UserOAuth() {
    }

    public UserOAuth(Long userId, String openId, Integer platformType) {
        SnowFlake snowFlake = new SnowFlake(0, 0);
        this.id = snowFlake.nextId();
        this.userId = userId;
        this.openId = openId;
        this.platformType = platformType;
        this.createTime = new Date();
    }

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    private String openId;
    private Integer platformType;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

package com.izanpin.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.izanpin.common.util.SnowFlake;

import java.util.Date;

/**
 * Created by St on 2017/5/25.
 */
public class Feedback {
    public Feedback() {
    }

    public Feedback(Long userId, String content, String device) {
        SnowFlake snowFlake = new SnowFlake(0, 0);
        this.id = snowFlake.nextId();
        this.userId = userId;
        this.content = content;
        this.device = device;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String content;

    private String device;

    private Date createTime;

    private Date updateTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

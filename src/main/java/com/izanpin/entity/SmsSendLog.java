package com.izanpin.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.izanpin.common.util.SnowFlake;

import java.util.Date;

/**
 * Created by St on 2017/3/3.
 */
public class SmsSendLog {
    public SmsSendLog() {
    }

    public SmsSendLog(String phone, String templateCode, String paramString) {
        this.phone = phone;
        this.templateCode = templateCode;
        this.paramString = paramString;

        SnowFlake snowFlake = new SnowFlake(0, 0);
        this.id = snowFlake.nextId();

        this.createTime = new Date();
    }

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String phone;
    private String templateCode;
    private String paramString;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getParamString() {
        return paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

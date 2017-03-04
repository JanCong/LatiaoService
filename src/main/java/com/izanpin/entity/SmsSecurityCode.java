package com.izanpin.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.izanpin.common.util.SnowFlake;
import com.izanpin.enums.SmsSecurityCodeStatus;
import com.izanpin.enums.SmsSecurityCodeType;

import java.util.Date;

/**
 * Created by St on 2017/3/2.
 */
public class SmsSecurityCode {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String phone;
    private String code;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Date dueTime;

    public SmsSecurityCode() {
        SnowFlake snowFlake = new SnowFlake(0, 0);
        this.setId(snowFlake.nextId());

        this.status = SmsSecurityCodeStatus.NORMAL.getValue();
        this.createTime = new Date();
        this.updateTime = new Date();
        this.dueTime = new Date(this.createTime.getTime() + 600000);
    }

    public SmsSecurityCode(String phone, String code, Integer type) {
        this();
        this.phone = phone;
        this.code = code;
        this.type = type;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }
}

package com.izanpin.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.izanpin.common.util.SnowFlake;
import com.izanpin.enums.UserStatus;

import java.util.Date;

public class User {
    public User() {
    }

    public User(String nickname, String phone, String email, String password,
                Integer sex, String avatar, Integer type) {
        SnowFlake snowFlake = new SnowFlake(0, 0);
        this.id = snowFlake.nextId();
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.avatar = avatar;
        this.type = type;
        this.status = UserStatus.NORMAL.getValue();
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String nickname;

    private String phone;

    private String email;

    private String password;

    private Integer sex;

    private String avatar;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
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
}
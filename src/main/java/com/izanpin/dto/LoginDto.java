package com.izanpin.dto;

import com.wordnik.swagger.annotations.ApiParam;

/**
 * Created by St on 2017/3/2.
 */
public class LoginDto {
    private String phone;
    private String password;
    private String device;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}

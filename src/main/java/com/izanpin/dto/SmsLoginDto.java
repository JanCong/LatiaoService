package com.izanpin.dto;

/**
 * Created by St on 2017/3/2.
 */
public class SmsLoginDto {
    private String phone;
    private String code;
    private String device;

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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}

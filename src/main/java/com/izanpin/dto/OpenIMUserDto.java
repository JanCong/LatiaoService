package com.izanpin.dto;

/**
 * Created by pengyuancong on 2017/6/3.
 */
public class OpenIMUserDto {
    private String userid;
    private String nick;
    private String password;

    public String getUserid() {
        return userid;
    }

    public OpenIMUserDto setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getNick() {
        return nick;
    }

    public OpenIMUserDto setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OpenIMUserDto setPassword(String password) {
        this.password = password;
        return this;
    }
}

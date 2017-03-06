package com.izanpin.dto;

import com.izanpin.enums.ResultStatus;

/**
 * Created by St on 2017/3/2.
 */
public class ResultDto<T> {
    public ResultDto(Integer status, String msg, T result) {
        this.setStatus(status);
        this.setMsg(msg);
        this.setResult(result);
    }

    private Integer status;
    private String msg;
    private T result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

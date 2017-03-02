package com.izanpin.dto;

import com.izanpin.enums.ResultStatus;

/**
 * Created by St on 2017/3/2.
 */
public class ResultDto<T> {
    public ResultDto(ResultStatus status, String msg, T result) {
        this.setStatus(status);
        this.setMsg(msg);
        this.setResult(result);
    }

    private ResultStatus status;
    private String msg;
    private T result;

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
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

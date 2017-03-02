package com.izanpin.service;

import com.taobao.api.ApiException;

/**
 * Created by St on 2017/3/2.
 */
public interface SmsService {
    void send(String number, String paramString, String templateCode) throws Exception;

    void sendLoginSecurityCode(String number) throws Exception;
}

package com.izanpin.service.impl;

import com.izanpin.entity.SmsSecurityCode;
import com.izanpin.enums.SmsSecurityCodeStatus;
import com.izanpin.enums.SmsSecurityCodeType;
import com.izanpin.repository.SmsSecurityCodeRepository;
import com.izanpin.service.SmsService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by St on 2017/3/2.
 */
@Service
@Transactional
public class SmsServiceImpl implements SmsService {
    @Autowired
    SmsSecurityCodeRepository smsSecurityCodeRepository;

    @Override
    public void send(String number, String paramString, String templateCode) throws Exception {
        String url = "http://gw.api.taobao.com/router/rest";
        String appKey = "23660602";
        String appSecret = "d9ec796563b3cbcb13037a5f168114dc";

        TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType("normal");
        req.setSmsFreeSignName("辣了个条");
        req.setSmsParamString(paramString);
        req.setRecNum(number);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        if (rsp.getErrorCode() != null && !rsp.getErrorCode().isEmpty()) {
            throw new Exception(rsp.getSubMsg());
        }
        System.out.println(rsp.getBody());
    }

    @Override
    public void sendLoginSecurityCode(String number) throws Exception {
        SmsSecurityCode lastSmsSecurityCode = smsSecurityCodeRepository.getByPhoneAndType(number, SmsSecurityCodeType.LOGIN.getValue());
        //todo 重复发送验证
        if (lastSmsSecurityCode != null
                && lastSmsSecurityCode.getStatus().equals(SmsSecurityCodeStatus.NORMAL.getValue())
                && lastSmsSecurityCode.getDueTime().after(new Date())) {
            throw new Exception("已发送验证码");
        }

        String securityCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        SmsSecurityCode smsSecurityCode = new SmsSecurityCode(number, securityCode, SmsSecurityCodeType.LOGIN.getValue());
        smsSecurityCodeRepository.add(smsSecurityCode);

        send(number, String.format("{\"number\":\"%s\"}", securityCode), "SMS_51055100");
    }
}

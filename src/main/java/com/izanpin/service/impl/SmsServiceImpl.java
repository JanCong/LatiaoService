package com.izanpin.service.impl;

import com.izanpin.entity.SmsSecurityCode;
import com.izanpin.entity.SmsSendLog;
import com.izanpin.enums.SmsSecurityCodeStatus;
import com.izanpin.enums.SmsSecurityCodeType;
import com.izanpin.repository.SmsSecurityCodeRepository;
import com.izanpin.repository.SmsSendLogRepository;
import com.izanpin.service.SmsService;
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
    @Autowired
    SmsSendLogRepository smsSendLogRepository;

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

        smsSendLogRepository.add(new SmsSendLog(number, templateCode, paramString));
    }

    @Override
    public void sendLoginSecurityCode(String number) throws Exception {
        SmsSecurityCode lastSmsSecurityCode = smsSecurityCodeRepository.getLastByPhoneAndType(number, SmsSecurityCodeType.LOGIN.getValue());
        SmsSendLog lastSmsSendLog = smsSendLogRepository.getLastByPhone(number);

        if (lastSmsSecurityCode != null && lastSmsSendLog != null
                && lastSmsSecurityCode.getStatus().equals(SmsSecurityCodeStatus.NORMAL.getValue())
                && new Date().before(new Date(lastSmsSendLog.getCreateTime().getTime() + 60000))) {
            throw new Exception("已发送验证码，1分钟内请勿重复发送");
        }

        String securityCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        send(number, String.format("{\"number\":\"%s\"}", securityCode), "SMS_51055100");

        SmsSecurityCode smsSecurityCode = new SmsSecurityCode(number, securityCode, SmsSecurityCodeType.LOGIN.getValue());
        smsSecurityCodeRepository.add(smsSecurityCode);
    }

    @Override
    public boolean verifyLoginSecurityCode(String number, String code) {
        SmsSecurityCode lastSmsSecurityCode = smsSecurityCodeRepository.getLastByPhoneAndType(number, SmsSecurityCodeType.LOGIN.getValue());

        if (lastSmsSecurityCode != null
                && lastSmsSecurityCode.getStatus().equals(SmsSecurityCodeStatus.NORMAL.getValue())
                && lastSmsSecurityCode.getDueTime().after(new Date())
                && lastSmsSecurityCode.getCode().equalsIgnoreCase(code)) {
            smsSecurityCodeRepository.updateStatus(lastSmsSecurityCode.getId(), SmsSecurityCodeStatus.DISABLED.getValue());
            return true;
        } else {
            return false;
        }

    }
}

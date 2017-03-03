package com.izanpin.repository;

import com.izanpin.entity.SmsSecurityCode;
import com.izanpin.entity.SmsSendLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsSendLogRepository {
    int add(SmsSendLog record);

    SmsSendLog getLastByPhone(String phone);
}
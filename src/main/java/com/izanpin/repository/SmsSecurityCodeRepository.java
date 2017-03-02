package com.izanpin.repository;

import com.izanpin.entity.SmsSecurityCode;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsSecurityCodeRepository {
    int add(SmsSecurityCode record);

    SmsSecurityCode getByPhoneAndType(String phone, Integer type);
}
package com.izanpin.repository;

import com.izanpin.entity.SmsSecurityCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsSecurityCodeRepository {
    int add(SmsSecurityCode record);

    SmsSecurityCode getLastByPhoneAndType(@Param("phone") String phone, @Param("type") Integer type);
}
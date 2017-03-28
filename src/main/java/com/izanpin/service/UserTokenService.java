package com.izanpin.service;

import com.izanpin.dto.LoginDto;
import com.izanpin.dto.SmsLoginDto;
import com.izanpin.entity.User;
import com.izanpin.entity.UserToken;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface UserTokenService {
    UserToken getUserTokenByUserId(Long userId);

    UserToken getUserTokenByToken(String token);
}

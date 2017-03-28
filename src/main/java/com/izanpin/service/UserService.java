package com.izanpin.service;

import com.izanpin.dto.LoginDto;
import com.izanpin.dto.SmsLoginDto;
import com.izanpin.entity.User;
import com.izanpin.entity.UserToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface UserService {
    List<User> getRobotUsers();

    User getUser(Long id);

    UserToken login(LoginDto dto) throws Exception;

    UserToken smsLogin(SmsLoginDto dto) throws Exception;
}

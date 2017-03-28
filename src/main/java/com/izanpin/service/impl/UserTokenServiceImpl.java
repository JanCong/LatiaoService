package com.izanpin.service.impl;

import com.izanpin.entity.UserToken;
import com.izanpin.repository.UserTokenRepository;
import com.izanpin.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by St on 2017/3/28.
 */
@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService {
    @Autowired
    UserTokenRepository userTokenRepository;

    @Override
    public UserToken getUserTokenByUserId(Long userId) {
        UserToken userToken = userTokenRepository.getByUserId(userId);
        if (userToken == null) {
            userToken = new UserToken(userId);
            userTokenRepository.add(userToken);
        }
        return userToken;
    }

    @Override
    public UserToken getUserTokenByToken(String token) {
        return userTokenRepository.getByToken(token);
    }
}

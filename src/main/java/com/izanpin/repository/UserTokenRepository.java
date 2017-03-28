package com.izanpin.repository;

import com.izanpin.entity.Like;
import com.izanpin.entity.UserToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by St on 2017/3/20.
 */
@Repository
public interface UserTokenRepository {
    UserToken getByUserId(Long userId);

    UserToken getByToken(String token);

    UserToken getByFreshToken(String refreshToken);

    void add(UserToken userToken);

    void updateToken(@Param("token") String token, @Param("id") Long id);
}

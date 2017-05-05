package com.izanpin.service;

import com.izanpin.dto.LoginDto;
import com.izanpin.dto.OAuthLoginDto;
import com.izanpin.dto.SmsLoginDto;
import com.izanpin.entity.User;
import com.izanpin.entity.UserToken;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface UserService {
    List<User> getRobotUsers();

    User getUser(Long id);

    void addUser(User user) throws Exception;

    void updateNickNmae(Long id, String nickname) throws Exception;

    void updatePassword(Long id, String password) throws Exception;

    void updateAvatar(Long id, MultipartFile avatar) throws Exception;

    UserToken login(LoginDto dto) throws Exception;

    UserToken smsLogin(SmsLoginDto dto) throws Exception;

    UserToken oauthLogin(OAuthLoginDto dto) throws Exception;
}

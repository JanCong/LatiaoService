package com.izanpin.service.impl;

import com.izanpin.common.util.StringEncrypt;
import com.izanpin.dto.LoginDto;
import com.izanpin.entity.User;
import com.izanpin.enums.UserType;
import com.izanpin.repository.UserRepository;
import com.izanpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> getRobotUsers() {
        return userRepository.getUsersByType(UserType.ROBOT.getValue());
    }

    @Override
    public User getUser(Long id) {
        return userRepository.selectByPrimaryKey(id);
    }

    @Override
    public User login(LoginDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("参数错误");
        }
        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
            throw new Exception("手机号不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new Exception("密码不能为空");
        }

        String encryptedPassword = StringEncrypt.Encrypt(dto.getPassword());
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            throw new Exception("参数错误");
        }

        User user = userRepository.getUserByPhone(dto.getPhone());
        if (user == null) {
            throw new Exception("用户不存在");
        }

        if (encryptedPassword.equalsIgnoreCase(user.getPassword())) {
            return user;
        } else {
            throw new Exception("手机号或密码错误");
        }
    }
}

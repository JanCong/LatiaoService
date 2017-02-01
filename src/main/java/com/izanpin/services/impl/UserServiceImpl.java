package com.izanpin.services.impl;

import com.izanpin.entity.User;
import com.izanpin.enums.UserType;
import com.izanpin.repository.UserRepository;
import com.izanpin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> getRobotUsers() {
        return userRepository.getUsersByType(UserType.ROBOT.getValue());
    }
}

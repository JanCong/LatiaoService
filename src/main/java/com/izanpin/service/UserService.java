package com.izanpin.service;

import com.izanpin.entity.User;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface UserService {
    List<User> getRobotUsers();

    User getUser(Long id);
}

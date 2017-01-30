package com.izanpin.repository;

import com.izanpin.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
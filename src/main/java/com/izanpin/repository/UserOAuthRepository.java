package com.izanpin.repository;

import com.izanpin.entity.UserOAuth;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by St on 2017/5/5.
 */
@Repository
public interface UserOAuthRepository {
    @Select("select id, user_id as userId, open_id as openId, platform_type as platformType, create_time as createTime " +
            "from user_oauth where open_id=#{openId};")
    UserOAuth getByOpenId(String openId);

    @Insert("INSERT INTO `user_oauth` (`id`,`user_id`,`open_id`,`platform_type`,`create_time`)" +
            "VALUES(#{id},#{userId},#{openId},#{platformType},#{createTime});")
    void add(UserOAuth userOAuth);
}

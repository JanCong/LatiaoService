package com.izanpin.repository;

import com.izanpin.entity.User;
import com.izanpin.entity.UserFriend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by St on 2017/5/26.
 */
@Repository
public interface UserFriendRepository {
    @Insert("INSERT INTO `user_friend`\n" +
            "(`id`,\n" +
            "`user_id`,\n" +
            "`friend_id`,\n" +
            "`status`,\n" +
            "`remark`,\n" +
            "`create_time`,\n" +
            "`update_time`)\n" +
            "VALUES\n" +
            "(#{id},\n" +
            "#{userId},\n" +
            "#{friendId},\n" +
            "#{status},\n" +
            "#{remark},\n" +
            "#{createTime},\n" +
            "#{updateTime});\n")
    void add(UserFriend userFriend);

    @Update("update user_friend set status = #{status} where user_id = #{userId} and friend_id = #{friendId}")
    void updateStatus(@Param("userId") Long userId,
                      @Param("friendId") Long friendId,
                      @Param("status") Integer status);


    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "friendId", column = "friend_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("select * from user_friend where user_id = #{userId} and friend_id = #{friendId} limit 1;")
    UserFriend get(@Param("userId") Long userId,
                   @Param("friendId") Long friendId);

    @Results({
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("select u.* from user_friend f join user u on f.user_id = u.id where f.user_id = #{userId}")
    List<User> getList(@Param("userId") Long userId);
}

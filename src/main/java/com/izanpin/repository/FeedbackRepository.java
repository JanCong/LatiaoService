package com.izanpin.repository;

import com.izanpin.entity.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Created by St on 2017/5/25.
 */
@Repository
public interface FeedbackRepository {
    @Insert("INSERT INTO `feedback`\n" +
            "(`id`,\n" +
            "`user_id`,\n" +
            "`content`,\n" +
            "`device`,\n" +
            "`create_time`,\n" +
            "`update_time`)\n" +
            "VALUES\n" +
            "(#{id},\n" +
            "#{userId},\n" +
            "#{content},\n" +
            "#{device},\n" +
            "#{createTime},\n" +
            "#{updateTime});\n")
    void add(Feedback feedback);
}

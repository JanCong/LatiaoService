package com.izanpin.repository;

import com.izanpin.entity.Like;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by St on 2017/3/20.
 */
@Repository
public interface LikeRepository {
    void add(Like like);

    List<Like> findByArticleId(Long articleId);

    Like getByArticleIdAndUserId(@Param("articleId") Long articleId, @Param("userId") Long userId);

    Like findByCommentId(Long commentId);

    List<Like> getByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}

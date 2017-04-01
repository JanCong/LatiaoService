package com.izanpin.repository;

import com.izanpin.entity.Hate;
import com.izanpin.entity.Like;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by St on 2017/3/20.
 */
@Repository
public interface HateRepository {
    void add(Hate hate);

    List<Hate> findByArticleId(Long articleId);

    Hate getByArticleIdAndUserId(@Param("articleId") Long articleId, @Param("userId") Long userId);

    Hate findByCommentId(Long commentId);

    List<Hate> getByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}

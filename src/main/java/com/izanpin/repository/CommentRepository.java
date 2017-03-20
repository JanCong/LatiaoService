package com.izanpin.repository;

import com.izanpin.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository {
    List<Comment> findByArticleId(Long articleId);

    void addComment(Comment comment);

    void addReply(Comment comment);

    Comment get(Long id);

    void increaseLikeCount(@Param("id") Long id, @Param("count") Integer count);

    void increaseHateCount(@Param("id") Long id, @Param("count") Integer count);
}
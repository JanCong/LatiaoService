package com.izanpin.repository;

import com.izanpin.entity.Article;
import com.izanpin.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository {
    List<Comment> findByArticleId(Long articleId);

    void addComment(Comment comment);

    void addReply(Comment comment);

    Comment get(Long id);

    void increaseLikeCount(Long id, Long userId, Integer count);

    void increaseHateCount(Long id, Long userId, Integer count);

}
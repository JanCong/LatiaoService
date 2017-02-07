package com.izanpin.service;

import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Article;
import com.izanpin.entity.Comment;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface CommentService {
    PageInfo getComments(Long articleId, Integer page, Integer size);

    void addComment(Long articleId, Long userId, String content);

    void reply(Long replyToId, Long userId, String content);

    Comment getById(Long id);

    void like(Long id, Long userId);

    void hate(Long id, Long userId);
}

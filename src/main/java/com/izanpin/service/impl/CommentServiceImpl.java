package com.izanpin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Article;
import com.izanpin.entity.Comment;
import com.izanpin.enums.ArticleType;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.repository.CommentRepository;
import com.izanpin.service.ArticleService;
import com.izanpin.service.CommentService;
import com.izanpin.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public PageInfo getArticles(Long articleId, Integer page, Integer size) {
        return null;
    }

    @Override
    public void addComment(Long articleId, Long userId, String content) {

    }

    @Override
    public void reply(Long commentId, Long userId, String content) {

    }

    @Override
    public Comment getById(Long id) {
        return null;
    }

    @Override
    public void like(Long id, Long userId) {

    }

    @Override
    public void hate(Long id, Long userId) {

    }
}

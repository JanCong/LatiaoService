package com.izanpin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Article;
import com.izanpin.entity.Comment;
import com.izanpin.enums.ArticleType;
import com.izanpin.enums.CommentStatus;
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
    public PageInfo getComments(Long articleId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo(commentRepository.findByArticleId(articleId));
    }

    @Override
    public void addComment(Long articleId, Long userId, String content) {
        SnowFlake snowFlake = new SnowFlake(0, 0);
        Comment comment = new Comment();
        comment.setId(snowFlake.nextId());
        comment.setUserId(userId);
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setStatus(CommentStatus.NORMAL.getValue());
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        commentRepository.addComment(comment);
    }

    @Override
    public void reply(Long replyToId, Long userId, String content) {
        Comment replyTo = commentRepository.get(replyToId);

        SnowFlake snowFlake = new SnowFlake(0, 0);

        Comment comment = new Comment();
        comment.setId(snowFlake.nextId());
        comment.setUserId(userId);
        comment.setReplyToId(replyToId);
        comment.setArticleId(replyTo.getArticleId());
        comment.setContent(content);
        comment.setStatus(CommentStatus.NORMAL.getValue());
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        commentRepository.addReply(comment);
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.get(id);
    }

    @Override
    public void like(Long id, Long userId) {
        commentRepository.increaseLikeCount(id, userId, 1);
    }

    @Override
    public void hate(Long id, Long userId) {
        commentRepository.increaseHateCount(id, userId, 1);
    }
}

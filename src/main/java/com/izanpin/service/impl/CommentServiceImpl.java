package com.izanpin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Comment;
import com.izanpin.entity.Like;
import com.izanpin.entity.User;
import com.izanpin.enums.CommentStatus;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.repository.CommentRepository;
import com.izanpin.repository.LikeRepository;
import com.izanpin.service.CommentService;
import com.izanpin.common.util.SnowFlake;
import com.izanpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    UserService userService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    LikeRepository likeRepository;

    @Override
    public PageInfo<Comment> getComments(Long articleId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo(commentRepository.findByArticleId(articleId));
    }

    @Override
    public void addComment(Long articleId, Long userId, String content) {
        User user = userService.getUser(userId);

        Comment comment = new Comment(
                content,
                user.getId(),
                user.getNickname(),
                user.getAvatar(),
                articleId,
                null,
                null);

        commentRepository.addComment(comment);

        articleRepository.increaseCommentCount(articleId, 1);
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
        commentRepository.increaseLikeCount(id, 1);
        likeRepository.add(new Like(userId, null, id));
    }

    @Override
    public void hate(Long id, Long userId) {
        commentRepository.increaseHateCount(id, 1);
    }
}

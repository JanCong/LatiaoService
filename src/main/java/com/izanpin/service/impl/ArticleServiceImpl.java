package com.izanpin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.izanpin.dto.AddPictureDto;
import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.entity.Article;
import com.izanpin.entity.User;
import com.izanpin.enums.ArticleStatus;
import com.izanpin.enums.ArticleType;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.service.ArticleService;
import com.izanpin.common.util.SnowFlake;
import com.izanpin.service.ImageService;
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
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;


    @Override
    public PageInfo getArticles(Integer page, Integer size, String keyword) {
        PageHelper.startPage(page, size);
        return new PageInfo(articleRepository.find(keyword));
    }

    @Override
    public void addArticle(Article article) throws Exception {
        long id = article.getId();
        if (id <= 0) {
            SnowFlake snowFlake = new SnowFlake(0, 0);
            article.setId(snowFlake.nextId());
        }

        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        articleRepository.insertSelective(article);
    }

    @Override
    public void addPicture(Article article, String imageUrl) throws Exception {
        addArticle(article);
        imageService.AddImage(imageUrl, article.getId());
    }

    @Override
    public void addPicture(AddPictureDto dto) throws Exception {
        User user = userService.getUser(dto.getUserId());
        if (user == null) {
            throw new Exception("用户不存在");
        }

        Article article = new Article();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        article.setId(snowFlake.nextId());

        article.setContent(dto.getContent());
        article.setCommentCount(0);
        article.setLikeCount(0);
        article.setHateCount(0);
        article.setType(ArticleType.PICTURE.getValue());
        article.setStatus(ArticleStatus.NORMAL.getValue());

        article.setAuthorId(user.getId());
        article.setAuthorName(user.getNickname());
        article.setAuthorAvatar(user.getAvatar());

        addArticle(article);

        dto.getImageUrls().forEach((url) -> {
            imageService.AddImage(url, article.getId());
        });
    }


    @Override
    public boolean existHashId(String hashId) {
        return articleRepository.getByHashId(hashId) != null;
    }

    @Override
    public PageInfo getPictures(Integer page, Integer size, String keyword) {
        PageHelper.startPage(page, size);
        return new PageInfo(articleRepository.findByType(ArticleType.PICTURE.getValue(), keyword));
    }

    @Override
    public PageInfo getJokes(Integer page, Integer size, String keyword) {
        PageHelper.startPage(page, size);
        return new PageInfo(articleRepository.findByType(ArticleType.JOKE.getValue(), keyword));
    }

    @Override
    public Article getById(Long id) {
        return articleRepository.get(id);
    }

    @Override
    public void like(Long id, Long userId) {
    }

    @Override
    public void hate(Long id, Long userId) {

    }

    @Override
    public PageInfo getArticlesByTimeline(Integer page, Integer size, RequestArticleTimelineDto dto) {
        if (page == null || page == 0) {
            page = 1;
        }
        if (size == null || size == 0) {
            size = 20;
        }
        if (dto.getSinceId() == null) {
            dto.setSinceId(0L);
        }
        if (dto.getMaxId() == null) {
            dto.setMaxId(0L);
        }
        if (dto.getAuthorId() == null) {
            dto.setAuthorId(0L);
        }

        PageHelper.startPage(page, size);
        return new PageInfo(articleRepository.findByTimeline(dto));
    }
}

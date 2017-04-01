package com.izanpin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.izanpin.dto.AddArticleDto;
import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.entity.Article;
import com.izanpin.entity.Hate;
import com.izanpin.entity.Like;
import com.izanpin.entity.User;
import com.izanpin.enums.ArticleType;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.repository.HateRepository;
import com.izanpin.repository.LikeRepository;
import com.izanpin.service.ArticleService;
import com.izanpin.common.util.SnowFlake;
import com.izanpin.service.ImageService;
import com.izanpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    HateRepository hateRepository;

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
        imageService.addImage(imageUrl, article.getId());
    }

    @Override
    public void addArticle(AddArticleDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("参数错误");
        }
        if (dto.getUserId() == null) {
            throw new Exception("用户ID为空");
        }
        User user = userService.getUser(dto.getUserId());
        if (user == null) {
            throw new Exception("用户不存在");
        }

        String content = dto.getContent();
        List<String> imageUrls = dto.getImageUrls();
        List<MultipartFile> images = dto.getImages();

        boolean hasImages = (imageUrls != null && !images.isEmpty()) || (images != null && !images.isEmpty());
        if ((content == null || content.isEmpty()) && (!hasImages)) {
            throw new Exception("内容为空且没有图片");
        }

        Article article = new Article(content, user.getId(), user.getNickname(), user.getAvatar(), dto.getDevice());

        if (hasImages) {
            article.setType(ArticleType.PICTURE.getValue());
        } else {
            article.setType(ArticleType.JOKE.getValue());
        }

        addArticle(article);

        if (hasImages) {
            if (imageUrls != null) {
                imageUrls.forEach((url) -> {
                    try {
                        imageService.addImage(url, article.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (images != null) {
                images.forEach((image) -> {
                    try {
                        imageService.addImage(image, article.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }


        }
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
    public void like(Long id, Long userId) throws Exception {
        Like like = likeRepository.getByArticleIdAndUserId(id, userId);
        if (like != null) {
            throw new Exception("不能重复点赞哦！");
        }

        articleRepository.increaseLikeCount(id, 1);
        likeRepository.add(new Like(userId, id, null));
    }

    @Override
    public void hate(Long id, Long userId) throws Exception {
        Hate hate = hateRepository.getByArticleIdAndUserId(id, userId);
        if (hate != null) {
            throw new Exception("不能重复点赞哦！");
        }

        articleRepository.increaseHateCount(id, 1);
        hateRepository.add(new Hate(userId, id, null));
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

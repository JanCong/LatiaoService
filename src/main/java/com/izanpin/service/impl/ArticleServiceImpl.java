package com.izanpin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Article;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.service.ArticleService;
import com.izanpin.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public PageInfo getArticles(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo(articleRepository.find());
    }

    @Override
    public void addArticle(Article article) throws Exception {
        SnowFlake snowFlake = new SnowFlake(0,0);
        article.setId(snowFlake.nextId());
        articleRepository.insertSelective(article);
    }

    @Override
    public boolean existHashId(String hashId) {
        return articleRepository.getByHashId(hashId) != null;
    }
}

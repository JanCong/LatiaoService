package com.izanpin.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Article;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return  new PageInfo( articleRepository.find());
    }
}

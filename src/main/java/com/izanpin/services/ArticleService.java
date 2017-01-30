package com.izanpin.services;

import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Article;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface ArticleService {
    PageInfo getArticles(Integer page, Integer size);
}

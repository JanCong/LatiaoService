package com.izanpin.service;

import com.github.pagehelper.PageInfo;
import com.izanpin.entity.Article;

/**
 * Created by Smart on 2017/1/30.
 */
public interface ArticleService {
    PageInfo getArticles(Integer page, Integer size);
    void addArticle(Article article) throws Exception;
    boolean existHashId(String hashId);
}

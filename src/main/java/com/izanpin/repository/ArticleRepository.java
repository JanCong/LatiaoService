package com.izanpin.repository;

import com.izanpin.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository {
    List<Article> find();

    int insert(Article record);

    int insertSelective(Article record);
}
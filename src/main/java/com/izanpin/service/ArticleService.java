package com.izanpin.service;

import com.github.pagehelper.PageInfo;
import com.izanpin.dto.AddArticleDto;
import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface ArticleService {
    PageInfo getArticles(Integer page, Integer size, String keyword);

    void addArticle(AddArticleDto dto) throws Exception;

    void addArticle(Article article) throws Exception;

    void addPicture(Article article, String imageUrl) throws Exception;

    boolean existHashId(String hashId);

    PageInfo getPictures(Integer page, Integer size, String keyword);

    PageInfo getJokes(Integer page, Integer size, String keyword);

    Article getById(Long id);

    void like(Long id, Long userId) throws Exception;

    void hate(Long id, Long userId);

    PageInfo getArticlesByTimeline(Integer page, Integer size, RequestArticleTimelineDto dto);
}

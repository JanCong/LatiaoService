package com.izanpin.service;

import com.github.pagehelper.PageInfo;
import com.izanpin.dto.AddArticleDto;
import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.dto.RequestFriendArticleTimelineDto;
import com.izanpin.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface ArticleService {
    PageInfo getArticles(Integer page, Integer size, String keyword, Long userId);

    void addArticle(AddArticleDto dto) throws Exception;

    void addArticle(Article article) throws Exception;

    void addPicture(Article article, String imageUrl, Boolean fromBSBDJ) throws Exception;

    void addPicture(Article article, String imageUrl) throws Exception;

    boolean existHashId(String hashId);

    PageInfo getPictures(Integer page, Integer size, String keyword, Long userId);

    PageInfo getJokes(Integer page, Integer size, String keyword, Long userId);

    Article getById(Long id);

    void like(Long id, Long userId) throws Exception;

    void hate(Long id, Long userId) throws Exception;

    PageInfo getArticlesByTimeline(Integer page, Integer size, RequestArticleTimelineDto dto);

    PageInfo<Article> getFriendArticlesByUserId(Integer page, Integer size, Long userId, RequestFriendArticleTimelineDto dto) throws Exception;

    List<Article> getArticlesByRandomInWeek(Integer size, Long userId);

    List<Article> getRecommendArticles(Integer size, Long userId);
}

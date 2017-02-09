package com.izanpin.data.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.izanpin.data.ImportData;
import com.izanpin.entity.Article;
import com.izanpin.entity.User;
import com.izanpin.enums.ArticleStatus;
import com.izanpin.enums.ArticleType;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.service.ArticleService;
import com.izanpin.service.ImageService;
import com.izanpin.service.UserService;
import com.izanpin.common.util.Http;
import com.izanpin.common.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * Created by Smart on 2017/1/31.
 */
@Component
public class ImportDataImpl implements ImportData {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;

    @Autowired
    ArticleRepository articleRepository;

    public void importData() throws Exception {
        importJokes();
        importPictures();
    }

    public void importJokes() throws Exception {
        String jsonString = Http.get("http://japi.juhe.cn/joke/content/text.from?key=eb1e4987289b12958853c58aa68f29a7&page=1&pagesize=20");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("reason").equals("Success")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    JSONObject jObj = (JSONObject) obj;
                    String hashId = jObj.getString("hashId");
                    if (!articleService.existHashId(hashId)) {
                        Article article = setArticle(jObj, ArticleType.JOKE, robots);
                        try {
                            articleService.addArticle(article);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void importPictures() throws Exception {
        String jsonString = Http.get("http://japi.juhe.cn/joke/img/text.from?key=eb1e4987289b12958853c58aa68f29a7&page=1&pagesize=20");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("reason").equals("Success")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    JSONObject jObj = (JSONObject) obj;
                    String hashId = jObj.getString("hashId");
                    if (!articleService.existHashId(hashId)) {
                        Article article = setArticle(jObj, ArticleType.PICTURE, robots);
                        try {
                            articleService.addArticle(article);
                            imageService.AddImage(jObj.getString("url"), article.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private Article setArticle(JSONObject jObj, ArticleType articleType, List<User> robots) {
        Article article = new Article();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        article.setId(snowFlake.nextId());
        article.setContent(jObj.getString("content"));
        article.setHashId(jObj.getString("hashId"));
        article.setCommentCount(0);
        article.setLikeCount(0);
        article.setHateCount(0);
        article.setType(articleType.getValue());
        article.setStatus(ArticleStatus.NORMAL.getValue());

        article.setAuthorName("路人甲");

        if (robots != null && robots.size() > 0) {
            User robot = robots.get(new Random().nextInt(robots.size() - 1));
            if (robot != null) {
                article.setAuthorId(robot.getId());
                article.setAuthorName(robot.getNickname());
                article.setAuthorAvatar(robot.getAvatar());
            }
        }
        return article;
    }
}

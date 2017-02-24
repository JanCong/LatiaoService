package com.izanpin.data.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.izanpin.common.util.SHA;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

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
        Thread threadImportJokes = new Thread(() -> {
            try {
                importJokes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread threadImportPictures = new Thread(() -> {
            try {
                importPictures();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadImportJokes.start();
        threadImportPictures.start();
    }

    public void importJokes() throws Exception {
        importJokesFromJuhe();
        importJokesFromJisu();
    }

    private void importJokesFromJuhe() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String jsonString = Http.get("http://japi.juhe.cn/joke/content/text.from?key=eb1e4987289b12958853c58aa68f29a7&page=1&pagesize=20");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("reason").equals("Success")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    executorService.submit(() -> {
                        JSONObject jObj = (JSONObject) obj;
                        String hashId = jObj.getString("hashId");
                        String content = jObj.getString("content");
                        if (!articleService.existHashId(hashId) && !content.trim().isEmpty()) {
                            Article article = setArticle(content, hashId, ArticleType.JOKE, robots);
                            try {
                                articleService.addArticle(article);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
            }
        }

        executorService.shutdown();
    }

    private void importJokesFromJisu() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String jsonString = Http.get("http://api.jisuapi.com/xiaohua/text?pagesize=20&appkey=e908f9037b24776f");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("msg").equals("ok")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("list");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    executorService.submit(() -> {
                        JSONObject jObj = (JSONObject) obj;
                        String content = jObj.getString("content");
                        if (content == null) {
                            content = "";
                        }
                        String hashId = null;
                        try {
                            hashId = SHA.toSHAString(jObj.toString());
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        if (!content.trim().isEmpty() && !articleService.existHashId(hashId)) {
                            Article article = setArticle(content, hashId, ArticleType.JOKE, robots);
                            try {
                                articleService.addArticle(article);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
            }
        }

        executorService.shutdown();
    }

    public void importPictures() throws Exception {
        importPicturesFromJuhe();
        importPicturesFromJisu();
    }

    private void importPicturesFromJuhe() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String jsonString = Http.get("http://japi.juhe.cn/joke/img/text.from?key=eb1e4987289b12958853c58aa68f29a7&page=1&pagesize=20");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("reason").equals("Success")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    executorService.submit(() -> {
                        JSONObject jObj = (JSONObject) obj;
                        String hashId = jObj.getString("hashId");
                        String imgUrl = jObj.getString("url");
                        if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                            Article article = setArticle(jObj.getString("content"), hashId, ArticleType.PICTURE, robots);
                            try {
                                articleService.addArticle(article);
                                imageService.AddImage(imgUrl, article.getId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
            }
        }

        executorService.shutdown();
    }

    private void importPicturesFromJisu() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String jsonString = Http.get("http://api.jisuapi.com/xiaohua/pic?pagesize=20&appkey=e908f9037b24776f");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("msg").equals("ok")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("list");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    executorService.submit(() -> {
                        JSONObject jObj = (JSONObject) obj;
                        String content = jObj.getString("content");
                        if (content == null) {
                            content = "";
                        }
                        String hashId = null;
                        try {
                            hashId = SHA.toSHAString(jObj.toString());
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        String imgUrl = jObj.getString("pic");
                        if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                            Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                            try {
                                articleService.addArticle(article);
                                imageService.AddImage(imgUrl, article.getId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
            }
        }

        executorService.shutdown();
    }

    private Article setArticle(String content, String hashId, ArticleType articleType, List<User> robots) {
        Article article = new Article();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        article.setId(snowFlake.nextId());
        article.setContent(content);
        article.setHashId(hashId);
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

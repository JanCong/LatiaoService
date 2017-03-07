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

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public void importDataAsync() throws Exception {
        cachedThreadPool.submit(() -> {
            try {
                importJokes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cachedThreadPool.submit(() -> {
            try {
                importPictures();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cachedThreadPool.shutdown();
    }

    @Override
    public void importData() throws Exception {
        cachedThreadPool.submit(() -> {
            try {
                importJokes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cachedThreadPool.submit(() -> {
            try {
                importPictures();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cachedThreadPool.shutdown();
        while (!cachedThreadPool.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
    }

    public void importJokes() throws Exception {
        cachedThreadPool.submit(() -> {
            try {
                importJokesFromJuhe();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cachedThreadPool.submit(() -> {
            try {
                importJokesFromJisu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cachedThreadPool.submit(() -> {
            try {
                importJokesFromShowapi();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void importJokesFromJuhe() throws Exception {
        String jsonString = Http.get("http://japi.juhe.cn/joke/content/text.from?key=eb1e4987289b12958853c58aa68f29a7&page=1&pagesize=20");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("reason").equals("Success")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
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
            }
        }

    }

    private void importJokesFromJisu() throws Exception {
        String jsonString = Http.get("http://api.jisuapi.com/xiaohua/text?pagesize=20&appkey=e908f9037b24776f");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("msg").equals("ok")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("list");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
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
            }
        }

    }

    private void importJokesFromShowapi() throws Exception {
        String jsonString = Http.get("https://route.showapi.com/341-1?maxResult=50&page=" + new Random().nextInt(200) + "&showapi_appid=33128&showapi_sign=954aa0d0d2bd48768d3b83ed3a8cdc78");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getInteger("showapi_res_code").equals(0)) {
            JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("contentlist");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    JSONObject jObj = (JSONObject) obj;
                    String content = jObj.getString("text");
                    if (content != null && !content.isEmpty()) {
                        String hashId = null;
                        try {
                            hashId = SHA.toSHAString(jObj.toString());
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        if (!articleService.existHashId(hashId)) {
                            Article article = setArticle(content, hashId, ArticleType.JOKE, robots);
                            try {
                                articleService.addArticle(article);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
    }

    public void importPictures() throws Exception {
        cachedThreadPool.submit(() -> {
            try {
                importPicturesFromJuhe();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cachedThreadPool.submit(() -> {
            try {
                importPicturesFromShowapi();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cachedThreadPool.submit(() -> {
            try {
                importPicturesFromShowapi2();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cachedThreadPool.submit(() -> {
            try {
                importPicturesFromShowapi3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void importPicturesFromJuhe() throws Exception {
        String jsonString = Http.get("http://japi.juhe.cn/joke/img/text.from?key=eb1e4987289b12958853c58aa68f29a7&page=1&pagesize=20");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("reason").equals("Success")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
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
            }
        }

    }

    private void importPicturesFromJisu() throws Exception {
        String jsonString = Http.get("http://api.jisuapi.com/xiaohua/pic?pagesize=20&appkey=e908f9037b24776f");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getString("msg").equals("ok")) {
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("list");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
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
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    private void importPicturesFromShowapi() throws Exception {
        String jsonString = Http.get("https://route.showapi.com/197-1?num=50&rand=1&showapi_appid=33128&showapi_sign=954aa0d0d2bd48768d3b83ed3a8cdc78");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getInteger("showapi_res_code").equals(0)) {
            JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("newslist");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    JSONObject jObj = (JSONObject) obj;
                    String content = "";

                    String hashId = null;
                    try {
                        hashId = SHA.toSHAString(jObj.toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    String imgUrl = jObj.getString("picUrl");

                    if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                        try {
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    private void importPicturesFromShowapi2() throws Exception {
        String jsonString = Http.get("https://route.showapi.com/341-3?maxResult=50&page=" + new Random().nextInt(200) + "&showapi_appid=33128&showapi_sign=954aa0d0d2bd48768d3b83ed3a8cdc78");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getInteger("showapi_res_code").equals(0)) {
            JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("contentlist");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    JSONObject jObj = (JSONObject) obj;
                    String content = jObj.getString("title");
                    if (content == null) {
                        content = "";
                    }
                    String hashId = null;
                    try {
                        hashId = SHA.toSHAString(jObj.toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    String imgUrl = jObj.getString("img");

                    if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                        try {
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void importPicturesFromShowapi3() throws Exception {
        String jsonString = Http.get("https://route.showapi.com/341-2?maxResult=50&page=" + new Random().nextInt(200) + "&showapi_appid=33128&showapi_sign=954aa0d0d2bd48768d3b83ed3a8cdc78");
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (jsonObject.getInteger("showapi_res_code").equals(0)) {
            JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("contentlist");

            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<User> robots = userService.getRobotUsers();
                jsonArray.forEach(obj -> {
                    JSONObject jObj = (JSONObject) obj;
                    String content = jObj.getString("title");
                    if (content == null) {
                        content = "";
                    }
                    String hashId = null;
                    try {
                        hashId = SHA.toSHAString(jObj.toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    String imgUrl = jObj.getString("img");

                    if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                        try {
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
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

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
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
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

    static Logger logger = LogManager.getLogger();

    @Override
    public void importData() throws Exception {
        importJokes();
        importPictures();
    }

    @Override
    public void importJokes() throws Exception {
//        importJokesFromJuhe();
        importJokesFromJisu();
        importJokesFromShowapi();
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
                            logger.error("", e);
                        }
                    }
                });
            }
        } else {
            logger.warn(jsonString);
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
                        logger.error("", e);
                    }
                    if (!content.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.JOKE, robots);
                        try {
                            articleService.addArticle(article);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                });
            }
        } else {
            logger.warn(jsonString);
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
                            logger.error("", e);
                        }

                        if (!articleService.existHashId(hashId)) {
                            Article article = setArticle(content, hashId, ArticleType.JOKE, robots);
                            try {
                                articleService.addArticle(article);
                            } catch (Exception e) {
                                logger.error("", e);
                            }
                        }
                    }
                });
            }
        } else {
            logger.warn(jsonString);
        }
    }

    @Override
    public void importPictures() throws Exception {
        try {
//        importPicturesFromJuhe();
            importPicturesFromShowapi2();
//        importPicturesFromShowapi();
            importPicturesFromShowapi3();
            importPicturesFromGiphy();
        } catch (Exception e) {
            logger.error("", e);
        }

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
                    try {
                        if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                            Article article = setArticle(jObj.getString("content"), hashId, ArticleType.PICTURE, robots);
                            articleService.addArticle(article);
                            imageService.addImage(imgUrl, article.getId());
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                });
            }
        } else {
            logger.warn(jsonString);
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
                        logger.error("", e);
                    }

                    String imgUrl = jObj.getString("pic");
                    if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                        try {
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                });
            }
        } else {
            logger.warn(jsonString);
        }

    }

    private void importPicturesFromShowapi() throws Exception {
        String jsonString = Http.get("https://route.showapi.com/197-1?num=5&rand=1&showapi_appid=33128&showapi_sign=954aa0d0d2bd48768d3b83ed3a8cdc78");
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
                        logger.error("", e);
                    }

                    String imgUrl = jObj.getString("picUrl");

                    if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                        try {
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                });
            }
        } else {
            logger.warn(jsonString);
        }

    }

    private void importPicturesFromShowapi2() throws Exception {
        logger.trace("importPicturesFromShowapi2()");

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
                        logger.error("", e);
                    }

                    String imgUrl = jObj.getString("img");

                    if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                        try {
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    } else {
                        logger.warn("hashId existed");
                    }
                });
            }
        } else {
            logger.warn(jsonString);
        }
    }

    private void importPicturesFromShowapi3() throws Exception {
        logger.trace("importPicturesFromShowapi3()");

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
                        logger.error("", e);
                    }

                    String imgUrl = jObj.getString("img");

                    if (imgUrl != null && !imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                        Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                        try {
                            articleService.addPicture(article, imgUrl);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    } else {
                        logger.warn("hashId existed");
                    }
                });
            }
        } else {
            logger.warn(jsonString);
        }
    }

    private void importPicturesFromGiphy() throws Exception {
        logger.trace("importPicturesFromGiphy()");

        List<String> urls = new ArrayList<String>();

        urls.add("http://api.giphy.com/v1/gifs/trending?api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=funny&api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=cat&api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=cute&api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=kawaii&api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=lovely&api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=girl&api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=auto&api_key=dc6zaTOxFJmzC");
        urls.add("http://api.giphy.com/v1/gifs/search?q=kid&api_key=dc6zaTOxFJmzC");

        urls.forEach(u -> {
            try {
                String jsonString = null;
                jsonString = Http.get(u);
                JSONObject jsonObject = JSON.parseObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                if (!jsonArray.isEmpty()) {
                    List<User> robots = userService.getRobotUsers();
                    jsonArray.forEach(obj -> {
                        JSONObject jObj = (JSONObject) obj;
                        String content = "";
                        String hashId = null;
                        try {
                            hashId = SHA.toSHAString(jObj.getString("id"));
                        } catch (NoSuchAlgorithmException e) {
                            logger.error("", e);
                        }

                        String imgUrl = "https://i.giphy.com/" + jObj.getString("id") + ".gif";

                        if (!imgUrl.trim().isEmpty() && !articleService.existHashId(hashId)) {
                            Article article = setArticle(content, hashId, ArticleType.PICTURE, robots);
                            try {
                                articleService.addPicture(article, imgUrl);
                            } catch (Exception e) {
                                logger.error("", e);
                            }
                        } else {
                            logger.warn("hashId existed");
                        }
                    });
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        });
    }

    private Article setArticle(String content, String hashId, ArticleType articleType, List<User> robots) {
        Article article = new Article();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        article.setId(snowFlake.nextId());
        article.setContent(content);
        article.setHashId(hashId);
        article.setCommentCount(0);
        article.setLikeCount(new Random().nextInt(1024));
        article.setHateCount(new Random().nextInt(24));
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

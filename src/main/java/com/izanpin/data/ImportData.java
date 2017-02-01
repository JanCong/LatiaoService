package com.izanpin.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.izanpin.entity.Article;
import com.izanpin.entity.User;
import com.izanpin.enums.ArticleStatus;
import com.izanpin.enums.ArticleType;
import com.izanpin.repository.ArticleRepository;
import com.izanpin.services.ArticleService;
import com.izanpin.services.UserService;
import com.izanpin.utils.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Smart on 2017/1/31.
 */
@Component
public class ImportData {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    @Autowired
    ArticleRepository articleRepository;

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

                        Article article = new Article();
                        article.setContent(jObj.getString("content"));
                        article.setHashId(hashId);
                        article.setCommentCount(0);
                        article.setLikeCount(0);
                        article.setHateCount(0);
                        article.setType(ArticleType.JOKE.getValue());
                        article.setStatus(ArticleStatus.NORMAL.getValue());
                        article.setCreateTime(new Date());
                        article.setUpdateTime(new Date());

                        article.setAuthorName("路人甲");

                        if (robots != null && robots.size() > 0) {
                            User robot = robots.get(new Random().nextInt(robots.size() - 1));
                            if (robot != null) {
                                article.setAuthorId(robot.getId());
                                article.setAuthorName(robot.getNickname());
                                article.setAuthorAvatar(robot.getAvatar());
                            }
                        }

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
}

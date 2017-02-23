package com.izanpin.dto;

import com.izanpin.enums.ArticleType;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Created by St on 2017/2/23.
 */
public class RequestArticleTimelineDto {
    @ApiParam("若指定此参数，则返回ID比sinceId大的Article（即比sinceId时间晚的Article）")
    private Long sinceId;
    @ApiParam("若指定此参数，则返回ID小于或等于maxId的Article")
    private Long maxId;
    @ApiParam("若指定此参数，则返回作者ID是authorId的Article")
    private Long authorId;
    @ApiParam("若指定此参数，则返回根据keyword模糊搜索")
    private String keyword;
    @ApiParam("若指定此参数，则根据type返回Article (PICTURE/JOKE)")
    private ArticleType type;

    public Long getSinceId() {
        return sinceId;
    }

    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }
}

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="${ctx}/static/share/">
    <meta charset="utf-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <title>辣条</title>
    <link rel="stylesheet" href="dist/css/detail.css">
    <script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div id="topBar" class="bar bar-header" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
    <button onclick="closeTopBar()"
            style="float: left; touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
        <i class="ion-top-banner-close"></i>
    </button>
    <button class="button button-icon logo-button">
        <i class="x-land-log"></i>
    </button>
    <div class="logo-title">辣条</div>
    <button class="button follow j-ga" data-ga_category="引导下载点击" data-ga_action="下载点击-顶部" data-ga_tag=""
            style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
        <a class="ui-btn ui-btn-primary" href="http://t.cn/RaZ38kL" style="padding:0 20px;">下载</a>
    </button>
</div>
<div class="topContent">
    <div class="ui-row-flex list-user-author">
        <div class="ui-avatar item-user">
            <img class="j-imglazyload" src="${article.getAuthorAvatar()}" alt="笑公馆V">
        </div>
        <div class="ui-list-info">
            <h4 class="ui-nowrap">${article.getAuthorName()}</h4>
            <p class="ui-nowrap">${article.getCreateTime()?string("yyyy-MM-dd HH:mm:ss")}</p>
        <#--<p class="ui-nowrap">${article.getDevice()}</p>-->
        </div>
    </div>
    <div id="topContentHeader" class="land">
        <section class="ui-row-flex">
            <p>
            ${article.getContent()?replace("\n","<br />")}
            </p>
        </section>
        <section class=" ui-placehold ui-placehold-img topic-img">
        <#list article.getImages()! as image>
            <#if image.getIsVideo()>
                <div class="x-video-container j-video">
                    <div class="x-video-p" style="padding-top:56.25%">
                        <video class="topic-xx-video x-video j-player"
                               width="100%" webkit-playsinline="" playsinline="" x-webkit-airplay="true" controls=""
                               preload="none"
                               poster="${image.getThumbnailUrl()}">
                            <source src="${image.getUrl()!}"
                                    type="video/mp4">
                        </video>
                    </div>
                </div>
            <#else>
                <img src="${image.getUrl()!}" class="j-imglazyload"/>
            </#if>
        </#list>
        </section>
    </div>
    <section class="video-tool">
        <ul class="ui-row ui-whitespace topic-tool ui-border-b">
            <li class="ui-col ui-col-33 tool-item" style="padding-left: 10px;">
                <a href="javascript:void(0);" id="btnUp" class="j-ga" data-type="yes" data-ga_category="点赞"
                   data-ga_action="点赞" data-ga_tag=""
                   style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
                    <i class="j-ui-up"></i>&nbsp;<span>${article.getLikeCount()}</span>
                </a>
            </li>
        <#--<li class="ui-col ui-col-33 tool-item j-ga" style="text-align: center; touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);" id="btnShare" data-ga_category="引导分享点击" data-ga_action="点击分享图标" data-ga_tag="">-->
        <#--<a href="javascript:void(0);" data="">-->
        <#--<i class="j-ui-share"></i>&nbsp;25-->
        <#--</a>-->
        <#--</li>-->
            <li class="ui-col ui-col-33 tool-item" style="text-align: right; padding-right: 10px; float:right">
                <i class="ui-icon-comments"></i>
                <span>${article.getCommentCount()}</span>
            </li>
        </ul>
    </section>
    <section class="comments">
    <#if article.getCommentCount() gt 0>
    <div id="articleHotComments" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
        <div class="item-divider ui-border-tb">
        </div>
        <div class="item-title">最新评论</div>
        <ul id="ulHotComments" class="ui-list ui-list-pure hot-comment">
            <#list comments.getList() as comment>
                <li class="ui-border-b">
                    <div class="comment-user-header">
                        <div class="ui-avatar">
                            <span style="background-image:url('${comment.getUserAvatar()}')"></span>
                        </div>
                    </div>
                    <div class="ui-list-info">
                        <div class="comment-content">
                            <div class="ui-row-flex line-header" data-cid="79876322">
                            <#--<i class="j-ui-female"></i>-->
                                <div class="ui-col ui-col-3 ui-nowrap-ellipsis">
                                ${comment.getUserName()}
                                </div>
                                <div class="ui-col tool-user-praise line-header" style="position: relative;">
                                    <span>${comment.getLikeCount()}</span>
                                    <i class="j-ui-up"></i>
                                </div>
                            </div>
                            <div class="comment">
                            ${comment.getContent()?replace("\n","<br />")}
                            </div>
                        </div>
                    </div>
                </li>
            </#list>
        </ul>
    </#if>
        <div style="padding: 15px;">
            <a class="download-app j-ga" data-ga_category="引导下载点击" data-ga_action="下载点击-更多评论" data-ga_tag=""
               href="http://t.cn/RaZ38kL"
               style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">下载辣条
            <#if article.getCommentCount() gt 0>
                查看<span id="more-comments">${article.getCommentCount()}</span>条精彩评论
            <#else>
                随时随地,想看就看
            </#if>
            </a>
        </div>
    </div>
    </section>
</div>
<script>
    function closeTopBar() {
        $('#topBar').hide();
        $('body').css('padding-top', '0');
    }
</script>
</body>
</html>
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
</head>
<body>
<div id="topBar" class="bar bar-header" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
    <button style="float: left; touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
        <i class="ion-top-banner-close"></i>
    </button>
    <button class="button button-icon logo-button">
        <i class="x-land-log"></i>
    </button>
    <div class="logo-title">辣条</div>
    <button class="button follow j-ga" data-ga_category="引导下载点击" data-ga_action="下载点击-顶部" data-ga_tag=""
            style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
        <a class="ui-btn ui-btn-primary" style="padding:0 20px;">下载</a>
    </button>
</div>
<div class="topContent">
    <div class="ui-row-flex list-user-author">
        <div class="ui-avatar item-user">
            <img class="j-imglazyload" src="http://wuliaoa.bj.bcebos.com/1024.png" alt="笑公馆V">
        </div>
        <div class="ui-list-info">
            <h4 class="ui-nowrap">${article.getAuthorName()}</h4>

            <p class="ui-nowrap">2017-05-09 15:38:01</p>
        </div>
    </div>
    <div id="topContentHeader" class="land">
        <section class="ui-row-flex">
            <p>
            ${article.getContent()}
            </p>
        </section>
        <section class=" ui-placehold ui-placehold-img topic-img">
        ${article.getImages()?size}
        <#list article.getImages()! as image>
            <img src="${image.getUrl()!}" class="j-imglazyload"/>
        </#list>
        </section>
    </div>
    <section class="video-tool">
        <ul class="ui-row ui-whitespace topic-tool ui-border-b">
            <li class="ui-col ui-col-33 tool-item" style="padding-left: 10px;">
                <a href="javascript:void(0);" id="btnUp" class="j-ga" data-type="yes" data-ga_category="点赞"
                   data-ga_action="点赞" data-ga_tag=""
                   style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
                    <i class="j-ui-up"></i>&nbsp;<span>217</span>
                </a>
            </li>
        <#--<li class="ui-col ui-col-33 tool-item j-ga" style="text-align: center; touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);" id="btnShare" data-ga_category="引导分享点击" data-ga_action="点击分享图标" data-ga_tag="">-->
        <#--<a href="javascript:void(0);" data="">-->
        <#--<i class="j-ui-share"></i>&nbsp;25-->
        <#--</a>-->
        <#--</li>-->
            <li class="ui-col ui-col-33 tool-item" style="text-align: right; padding-right: 10px; float:right">
                <i class="ui-icon-comments"></i>
                <span>40</span>
            </li>
        </ul>
    </section>
    <section class="comments">
        <div id="articleHotComments" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
            <div class="item-divider ui-border-tb">
            </div>
            <div class="item-title">最新评论</div>
            <ul id="ulHotComments" class="ui-list ui-list-pure hot-comment">
                <li class="ui-border-b">
                    <div class="comment-user-header">
                        <div class="ui-avatar">
                            <span style="background-image:url('http://wimg.spriteapp.cn/profile/large/2017/04/30/5905775e6d7b4_mini.jpg ')"></span>
                        </div>
                    </div>
                    <div class="ui-list-info">
                        <div class="comment-content">
                            <div class="ui-row-flex line-header" data-cid="79876322">
                                <i class="j-ui-female"></i>
                                <div class="ui-col ui-col-3 ui-nowrap-ellipsis">
                                    要走的人我绝不留
                                </div>
                                <div class="ui-col tool-user-praise line-header" style="position: relative;">
                                    <span>16</span>
                                    <i class="j-ui-up"></i>
                                </div>
                            </div>
                            <div class="comment">
                                没想到中石化还有这个服务，加多少送一个啊
                            </div>
                        </div>
                    </div>
                </li>
                <li class="ui-border-b">
                    <div class="comment-user-header">
                        <div class="ui-avatar">
                            <span style="background-image:url('http://wimg.spriteapp.cn/profile/large/2017/05/09/5911759ce9df1_mini.jpg ')"></span>
                        </div>
                    </div>
                    <div class="ui-list-info">
                        <div class="comment-content">
                            <div class="ui-row-flex line-header" data-cid="79876490">
                                <i class="j-ui-male"></i>
                                <div class="ui-col ui-col-3 ui-nowrap-ellipsis">
                                    平地起风浪
                                </div>
                                <div class="ui-col tool-user-praise line-header" style="position: relative;">
                                    <span>11</span>
                                    <i class="j-ui-up"></i>
                                </div>
                            </div>
                            <div class="comment">

                                工作人员：你一天来看一百次也没用，我们这必须加油才能送的

                            </div>
                        </div>
                    </div>
                </li>
                <li class="ui-border-b">
                    <div class="comment-user-header">
                        <div class="ui-avatar">
                            <span style="background-image:url('http://qzapp.qlogo.cn/qzapp/100336987/4C2B5AD3D5FD50D77D2DE8C3CB31BA15/100 ')"></span>
                        </div>
                    </div>
                    <div class="ui-list-info">
                        <div class="comment-content">
                            <div class="ui-row-flex line-header" data-cid="79877292">
                                <i class="j-ui-male"></i>
                                <div class="ui-col ui-col-3 ui-nowrap-ellipsis">
                                    你好呀12
                                </div>
                                <div class="ui-col tool-user-praise line-header" style="position: relative;">
                                    <span>8</span>
                                    <i class="j-ui-up"></i>
                                </div>
                            </div>
                            <div class="comment">
                                虽然很俗但是很有效果
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div style="padding: 15px;">
                <a class="download-app j-ga" data-ga_category="引导下载点击" data-ga_action="下载点击-更多评论" data-ga_tag=""
                   href="http://t.cn/RaZ38kL"
                   style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">下载辣条
                    查看<span id="more-comments">40</span>条精彩评论</a>
            </div>
        </div>
    </section>
</div>
</body>
</html>






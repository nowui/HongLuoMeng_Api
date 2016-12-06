<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>红萝梦 - 全球美妆网红KOL代理</title>
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="email=no" />
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0" />
    <script src="vue.min.js"></script>
    <script src="fetch.js"></script>
    <style>
    * {
    		font-family: Arial,sans-serif;
        font-size: 14px;
        margin: 0px;
        padding: 0px;
        line-height: 25px;
    }

    #app {
        min-width: 320px;
        max-width: 430px;
        height: 100%;
        margin-left: auto;
        margin-right: auto;
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
    }

    .list {
        margin-top: 10px;
        margin-bottom: 10px;
        padding-bottom: 10px;
        border-bottom: 1px solid #e2e2e2;
    }

    .left {
        position: absolute;
        padding-left: 10px;
    }

    .avatar {
        width: 40px;
        height: 40px;
    }

    .right {
        padding-left: 60px;
        padding-right: 10px;
        width: 100%;
        box-sizing: border-box;
    }

    .image img {
        width: 80px;
        height: 80px;
    }

    .name {
        color: #576b95;
    }

    .button {
        width: 20px;
        float: right;
        margin-top: 5px;
    }

    .time {
        color: #b1b1b1;
    }

    .arrow {
        border-bottom: 8px solid #eee;
        border-left: 8px solid transparent;
        border-right: 8px solid transparent;
        width: 1px;
        margin-top: 5px;
        margin-left: 10px;
    }

    .comment {
        width: 100%;
        background-color: #eee;
    }

    .like {
        color: #576b95;
        padding-top: 10px;
        padding-left: 10px;
        padding-right: 10px;
    }

    .like img {
        width: 12px;
        padding-right: 5px;
    }

    .feedback {
        padding: 10px;
    }

    .feedback span {
        color: #3b5384;
    }
    </style>
</head>

<body>
    <div id="app">
        <div class="list" v-for="item in 10">
            <div class="left">
                <img class="avatar" src="image/ma1.jpg">
            </div>
            <div class="right">
                <p class="name">马云</p>
                <p>中共中央总书记、国家主席、中央军委主席、中央全面深化改革领导小组组长习近平12月5日下午主持召开中央全面深化改革领导小组第三十次会议并发表重要讲话。</p>
                <p class="image">
                    <img src="image/jt1.jpg">
                    <img src="image/jt1.jpg">
                    <img src="image/jt1.jpg">
                </p>
                <p class="time">刚刚<img class="button" src="image/c.png"></p>
                <div class="arrow"></div>
                <div class="comment">
                    <div class="like"><img src="image/l.png">苍井空，陈冠希，杨幂，王思聪，陈赫，刘德华，马云</div>
                    <div class="feedback">
                        <p><span>范冰冰：</span>珠宝再加上永生花，花永生，爱永恒，想想都觉得好浪漫哦~</p>
                        <p><span>李晨</span>回复<span>范冰冰 ：</span>小肥羊~初七情人节见！定格我们的爱情吧</p>
                        <p><span>王思聪：</span>我女朋友多，团购才能搞定啊！</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
<script>
var app = new Vue({
    el: '#app',
    data: {
        message: 'Hello Vue!'
    }
})

/*fetch('http://api.hongluomeng.nowui.com/member/login', {
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'token': 'eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE0ODAxODIyODYsImV4cCI6MTQ4MTIyMTUxNSwiYXV0aG9yaXphdGlvbl9pZCI6ImFjZDRiNDAwODU0MTQxMThiZDk4MmRkNmNjZjliZWE1IiwidXNlcl9pZCI6IjVjMTI2ODAxMzI3MjQ5YTFhODY3ZWU0NjZmYWI4YTBiIn0.J5pjwyb-lrlLGcqPltu75E-386VrqHbbeHlam72oucb5sCNJ3mq5UfVI8h7m78MQDFO79eFoRcT-5ZhPBTxE0g',
        'platform': 'WEB',
        'version': '1.0.0',
    },
    method: 'POST',
    body: JSON.stringify({
        user_phone: '18668141979',
        user_password: '12345678'
    })
}).then(function(response) {
    if (response.status !== 200) {

        return;
    }
    response.json().then(function(data) {
        console.log(data);
    })
}).catch(function(err) {
    console.log('Fetch Error :-S', err);
});*/
</script>

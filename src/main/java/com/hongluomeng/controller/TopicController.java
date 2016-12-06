package com.hongluomeng.controller;

import com.hongluomeng.common.Url;
import com.jfinal.core.ActionKey;

public class TopicController extends BaseController {

    @ActionKey(Url.URL_TOPIC_INDEX)
    public void index() {
        render("index.html");
    }


}

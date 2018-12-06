package com.ik.user.center.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.util.HttpUtil;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static JSONObject post(String url, JSONObject request) {
        String body = request.toJSONString();
        String response = HttpUtil.post(url).setBody(body).execute();
        log.info("http client post url = {}, body = {}, result = {}", url, body, response);
        return JSON.parseObject(response);
    }

    public static JSONObject get(String url) {
        String response = HttpUtil.get(url).execute();
        log.info("http client get url = {}, result = {}", url, response);
        return JSON.parseObject(response);
    }
}

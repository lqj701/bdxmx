package com.ik.user.center.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.util.HttpUtil;

public class HttpHandler {
    private static Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    public static String post(String url, Map<String, Object> headers) {
        HttpHandler.logger.info("POST: url = {}, header = {}", url, headers);

        long begin = System.currentTimeMillis();
        String response = HttpUtil.post(url).setHeader(headers).execute();
        HttpHandler.logger.info("POST: url = {}, time = {} ,response = {}", url, System.currentTimeMillis() - begin,
                response);

        return response;
    }

    public static String post(String url, JSONObject reqBody) {
        HttpHandler.logger.info("POST: url = {}, reqBody = {}", url, reqBody.toJSONString());

        long begin = System.currentTimeMillis();
        String response = HttpUtil.post(url).setBody(reqBody.toJSONString()).execute();
        HttpHandler.logger.info("POST: url = {}, time = {} ,response = {}", url, System.currentTimeMillis() - begin,
                response);

        return response;
    }

    public static String post(String url, JSONObject reqBody, Map<String, Object> headers) {
        HttpHandler.logger.info("POST: url = {}, reqBody = {}, headers = {}", url, reqBody.toJSONString(),
                JSONObject.toJSONString(headers));

        long begin = System.currentTimeMillis();
        String response = HttpUtil.post(url).setBody(reqBody.toJSONString()).setHeader(headers).execute();
        HttpHandler.logger.info("POST: url = {}, time = {} ,response = {}", url, System.currentTimeMillis() - begin,
                response);

        return response;
    }

    public static String post(String url, JSONObject reqBody, Map<String, Object> headers, int time) {
        HttpHandler.logger.info("POST: url = {}, reqBody = {}, headers = {}", url, reqBody.toJSONString(),
                JSONObject.toJSONString(headers));

        long begin = System.currentTimeMillis();
        String response = HttpUtil.post(url).setBody(reqBody.toJSONString()).setHeader(headers)
                .setConnectTimeoutSecond(time).execute();
        HttpHandler.logger.info("POST: url = {}, time = {} ,response = {}", url, System.currentTimeMillis() - begin,
                response);

        return response;
    }

    public static String get(String url, Map<String, Object> headers) {
        HttpHandler.logger.info("GET: url = {}, headers = {}", url, headers);

        long begin = System.currentTimeMillis();
        String response = HttpUtil.get(url).setHeader(headers).execute();
        HttpHandler.logger.info("GET: url = {}, time = {} ,response = {}", url, System.currentTimeMillis() - begin,
                response);

        return response;
    }

    public static String get(String url) {
        HttpHandler.logger.info("GET: url = {}", url);

        long begin = System.currentTimeMillis();
        String response = HttpUtil.get(url).execute();
        HttpHandler.logger.info("GET: url = {}, time = {} ,response = {}", url, System.currentTimeMillis() - begin,
                response);

        return response;
    }

    public static String put(String url, JSONObject reqBody, Map<String, Object> headers) {
        HttpHandler.logger.info("PUT: url = {}, reqBody = {}, headers = {}", url, reqBody.toJSONString(),
                JSONObject.toJSONString(headers));

        long begin = System.currentTimeMillis();
        String response = HttpUtil.put(url).setBody(reqBody.toJSONString()).setHeader(headers).execute();
        HttpHandler.logger.info("PUT: url = {}, time = {} ,response = {}", url, System.currentTimeMillis() - begin,
                response);

        return response;
    }
}

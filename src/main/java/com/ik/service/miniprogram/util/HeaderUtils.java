package com.ik.service.miniprogram.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

public class HeaderUtils {
    private static Logger logger = LoggerFactory.getLogger(HeaderUtils.class);

    public static <T> T getHeader(String name, Class<T> clazz) {
        Object headerObject = HeaderUtils.getHeader(name);
        if (headerObject == null) {
            return null;
        }

        if (clazz == String.class) {
            return (T) headerObject.toString();
        }

        return JSON.parseObject(headerObject.toString(), clazz);
    }

    public static Object getHeader(String name) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        return attributes.getRequest().getHeader(name);
    }
}

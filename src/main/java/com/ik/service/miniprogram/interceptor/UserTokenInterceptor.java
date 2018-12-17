package com.ik.service.miniprogram.interceptor;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.exception.MPException;

public class UserTokenInterceptor extends HandlerInterceptorAdapter {

    protected static final Logger log = LoggerFactory.getLogger(UserTokenInterceptor.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String url = request.getRequestURI();
        String userToken = request.getHeader("userToken");
        UserTokenInterceptor.log.info("userToken = {}, , url = {}", userToken, url);

        if (url.endsWith("logout") || url.endsWith("login")) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            return super.preHandle(request, response, handler);
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明不进行服务拦截
        IgnoreUserToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreUserToken.class);
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(IgnoreUserToken.class);
        }
        if (annotation != null) {
            return super.preHandle(request, response, handler);
        }
        if (StringUtils.isBlank(userToken)) {
            throw new MPException(ErrorCode.USERTOKEN_IS_NULL);
        }
        String uid = redisTemplate.opsForValue().get(userToken);
        if (StringUtils.isBlank(uid)) {
            throw new MPException(ErrorCode.TOKEN_EXPIRATE_OR_USER_TOKEN_ERR);
        }
        redisTemplate.expire(userToken, 2, TimeUnit.DAYS);

        request.setAttribute("userToken", uid);
        return super.preHandle(request, response, handler);
    }
}

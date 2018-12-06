package com.bdxmx.miniprogram.service.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bdxmx.miniprogram.constants.ErrorCode;
import com.bdxmx.miniprogram.service.LoginService;
import com.google.common.collect.Maps;
import com.ik.crm.commons.exception.SystemException;
import com.ik.crm.commons.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService {
    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getCredence(Integer uid) {
        JSONObject loginParams = new JSONObject();
        loginParams.put("uid", uid);
        if (uid == null) {
            throw new SystemException(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        String credence = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(credence, loginParams.toJSONString(), 2, TimeUnit.DAYS);
        return credence;
    }

    @Override
    public Map<String, String> getToken(String credence) {
        if (StringUtils.isBlank(credence)) {
            throw new SystemException(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        String loginParams = (String) redisTemplate.opsForValue().get(credence);
        if (loginParams == null || loginParams.isEmpty()) {
            throw new SystemException(ErrorCode.CREDENCE_ERROR.getCode(), ErrorCode.CREDENCE_ERROR.getMsg());
        }
        redisTemplate.delete(credence);

        Long uid = JSONObject.parseObject(loginParams).getLong("uid");

        String userToken = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(userToken, String.valueOf(uid), 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(String.valueOf(uid), userToken, 2, TimeUnit.DAYS);

        Map<String, String> result = Maps.newHashMap();
        result.put("userToken", userToken);
        return result;
    }

    @Override
    public void deleteUserToken(Integer uid) {
        if (uid != null) {
            String userToken = (String) redisTemplate.opsForValue().get(uid.toString());
            redisTemplate.delete(uid.toString());
            if (!StringUtils.isBlank(userToken)) {
                redisTemplate.delete(userToken);
            }
        }
    }

    @Override
    public String getTokenByUid(Integer uid) {
        if (uid == null) {
            return null;
        }
        return (String) redisTemplate.opsForValue().get(String.valueOf(uid));
    }


}

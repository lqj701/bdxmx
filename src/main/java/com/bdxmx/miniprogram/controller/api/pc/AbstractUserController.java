package com.bdxmx.miniprogram.controller.api.pc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.bdxmx.miniprogram.constants.ErrorCode;
import com.bdxmx.miniprogram.exception.MPException;
import com.bdxmx.miniprogram.model.Teacher;
import com.bdxmx.miniprogram.service.TeacherService;

public abstract class AbstractUserController {
    private static Logger logger = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TeacherService teacherService;

    protected Teacher getUser(HttpServletRequest request) {
        Object uidObject = request.getAttribute("uerToken");
        AbstractUserController.logger.info("uidObject = {}", uidObject);
        Integer uid = Integer.parseInt((String) uidObject);
        Teacher user = teacherService.selectByPrimaryKey(uid);
        if (user == null) {
            throw new MPException(ErrorCode.USER_NOT_EXIST);
        }
        return user;
    }
}

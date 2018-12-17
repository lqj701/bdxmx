package com.ik.service.miniprogram.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.ik.crm.commons.exception.SystemException;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.CurrentTeacherService;
import com.ik.service.miniprogram.service.TeacherService;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentTeacherServiceImpl implements CurrentTeacherService {
    private static Logger logger = LoggerFactory.getLogger(CurrentTeacherServiceImpl.class);
    @Autowired
    private TeacherService teacherService;

    private Teacher teacher;

    @Override
    public Teacher currentTeacher() {
        return teacher;
    }

    @Override
    public void setCurrentTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public Teacher setCurrentTeacherByUserToken(String userToken) {
        Teacher teacher = null;
        if (userToken != null) {
            teacher = teacherService.findTeacherByAccessToken(userToken);
            setCurrentTeacher(teacher);
            if (teacher == null) {
                throw new SystemException(ErrorCode.USER_NOT_EXIST.getCode(), ErrorCode.USER_NOT_EXIST.getMsg());
            }
        }
        return teacher;
    }

}

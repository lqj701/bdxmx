package com.bdxmx.miniprogram.service;

import com.bdxmx.miniprogram.model.Teacher;

public interface CurrentTeacherService {
    Teacher currentTeacher();

    void setCurrentTeacher(Teacher teacher);

    Teacher setCurrentTeacherByUserToken(String userToken);

}

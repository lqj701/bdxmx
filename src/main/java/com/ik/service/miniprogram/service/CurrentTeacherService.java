package com.ik.service.miniprogram.service;

import com.ik.service.miniprogram.model.Teacher;

public interface CurrentTeacherService {
    Teacher currentTeacher();

    void setCurrentTeacher(Teacher teacher);

    Teacher setCurrentTeacherByUserToken(String userToken);

}

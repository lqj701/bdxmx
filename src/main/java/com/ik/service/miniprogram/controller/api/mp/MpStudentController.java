package com.ik.service.miniprogram.controller.api.mp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.model.Student;
import com.ik.service.miniprogram.service.StudentService;

@RestController
@RequestMapping(value = "/api/mp", produces = "application/json;utf-8")
@IgnoreUserToken
public class MpStudentController {
    @Autowired
    private StudentService studentService;


    /**
     * 保存学生个人信息
     * @param params
     * @return
     */
    @PostMapping("/saveStudent")
    public ResultResponse saveAnswerSheet(@RequestBody JSONObject params) {
        Integer grade = params.getInteger("grade");
        String name = params.getString("name");
        String email = params.getString("email");
        String phone = params.getString("phone");
        Integer id = params.getInteger("id");

        if (name == null || id == null) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        Student student = studentService.selectByPrimaryKey(id);
        student.setName(name);
        student.setGrade(grade);
        student.setEmail(email);
        student.setPhone(phone);
        student.setUpdatedAt(new Date());
        student.setSetInfo(true);
        studentService.updateByPrimaryKeySelective(student);
        return ResultResponse.success();
    }

}

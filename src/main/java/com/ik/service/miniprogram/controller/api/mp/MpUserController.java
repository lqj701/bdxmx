package com.ik.service.miniprogram.controller.api.mp;

import java.util.List;
import java.util.Objects;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.model.Student;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.EnrollInfoService;
import com.ik.service.miniprogram.service.StudentService;
import com.ik.service.miniprogram.service.TeacherService;
import com.ik.service.miniprogram.service.TeacherStudentMapService;
import com.ik.service.miniprogram.util.CheckUtil;


@RestController
@RequestMapping(value = "/api/mp", produces = "application/json;utf-8")
public class MpUserController {
    private static Logger logger = LoggerFactory.getLogger(MpUserController.class);
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherStudentMapService teacherStudentMapService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollInfoService enrollInfoService;

    @IgnoreUserToken
    @GetMapping("/getStudentByOpenid/{openid}")
    public ResultResponse getStudentByOpenid(@PathVariable String openid) {
        Student student = new Student();
        student.setOpenid(openid);
        student = studentService.selectOne(student);

        return ResultResponse.success(student);
    }


    @IgnoreUserToken
    @RequestMapping(value = "/getTeacherList", method = RequestMethod.POST)
    public ResultResponse<?> getTeacherList(@RequestBody JSONObject params) {
        Integer page = params.getInteger("page");
        Integer row = params.getInteger("row");

        List<Teacher> teacherList = teacherService.getAllTeacherList(page, row);
        return ResultResponse.success(teacherList);
    }

    @IgnoreUserToken
    @RequestMapping(value = "/bindTeacher", method = RequestMethod.POST)
    public ResultResponse<?> bindTeacher(@RequestBody JSONObject params) {
        Integer studentId = params.getInteger("studentId");
        List<Integer> teacherIds = params.getJSONArray("teacherIds").toJavaList(Integer.class);

        List<Integer> errorTeacherIds = validateTeacherIds(teacherIds);
        if (!CollectionUtils.isEmpty(errorTeacherIds)) {
            return ResultResponse.define(ErrorCode.TEACHERIDS_ERROR.getCode(),
                    ErrorCode.TEACHERIDS_ERROR.getMsg() + "错误ID为: " + errorTeacherIds.toString());
        }

        teacherStudentMapService.saveStudentTeacherMap(studentId, teacherIds);
        return ResultResponse.success();
    }

    @IgnoreUserToken
    @RequestMapping(value = "/studentEnroll", method = RequestMethod.POST)
    public ResultResponse<?> studentEnroll(@RequestBody JSONObject params) {
        String phone = params.getString("phone");
        String name = params.getString("name");
        String email = params.getString("email");
        String address = params.getString("address");
        Integer grade = params.getInteger("grade");

        if (Objects.isNull(phone) || Objects.isNull(name) || Objects.isNull(email) || Objects.isNull(address)
                || Objects.isNull(grade)) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (CheckUtil.isMobile(phone)) {
            return ResultResponse.define(ErrorCode.PHONE_ERROR.getCode(), ErrorCode.PHONE_ERROR.getMsg());
        }
        JSONObject data = enrollInfoService.saveEnrollInfo(params);
        ErrorCode errorCode = (ErrorCode) data.get("error");
        if (Objects.nonNull(errorCode)) {
            return ResultResponse.define(errorCode.getCode(), errorCode.getMsg());
        }
        return ResultResponse.success();
    }


    private List<Integer> validateTeacherIds(List<Integer> teacherIds) {
        List<Integer> errorList = Lists.newArrayList();
        teacherIds.stream().forEach(teacherId -> {
            if (teacherService.selectByPrimaryKey(teacherId) == null) {
                errorList.add(teacherId);
            }
        });
        return errorList;
    }
}

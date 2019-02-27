package com.ik.service.miniprogram.controller.api.mp;

import java.util.List;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.constants.CourseEnum;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.TeacherService;
import com.ik.service.miniprogram.service.TeacherStudentMapService;


@RestController
@RequestMapping(value = "/api/mp", produces = "application/json;utf-8")
public class MpUserController {
    private static Logger logger = LoggerFactory.getLogger(MpUserController.class);
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherStudentMapService teacherStudentMapService;

    @IgnoreUserToken
    @RequestMapping(value = "/getTeacherList", method = RequestMethod.POST)
    public ResultResponse<?> getTeacherList(@RequestBody JSONObject params) {
        Integer page = params.getInteger("page");
        Integer row = params.getInteger("row");
        Integer courseType = params.getInteger("courseType");

        List<Teacher> teacherList = teacherService.getAllTeacherList(page, row,
                CourseEnum.getCourseEnum(courseType).getCode());
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

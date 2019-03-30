package com.ik.service.miniprogram.controller.api.pc;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import com.ik.crm.commons.util.StringUtils;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.constants.CourseEnum;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.model.Account;
import com.ik.service.miniprogram.model.Student;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.model.TeacherStudentMap;
import com.ik.service.miniprogram.service.*;
import com.ik.service.miniprogram.util.CheckUtil;


@RestController
@RequestMapping(value = "/api/user", produces = "application/json;utf-8")
public class UserController extends AbstractUserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private TeacherStudentMapService teacherStudentMapService;
    @Autowired
    private StudentService studentService;

    @IgnoreUserToken
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultResponse<?> register(@RequestBody JSONObject params) {
        String phone = params.getString("phone");
        String password = params.getString("password");
        String name = params.getString("name");
        String email = params.getString("email");
        String avatarUrl = params.getString("avatarUrl");
        Integer type = params.getInteger("type");
        Boolean gendar = params.getBoolean("gendar");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(name) || null == type) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        if (!CheckUtil.isMobile(phone)) {
            return ResultResponse.define(ErrorCode.REGISTER_FAILED_BECAUSE_ERROR_PHONE.getCode(),
                    ErrorCode.REGISTER_FAILED_BECAUSE_ERROR_PHONE.getMsg());
        }

        Account query = new Account();
        query.setPhone(phone);
        Account account = accountService.selectOne(query);
        if (null != account) {
            return ResultResponse.define(ErrorCode.PHONE_ALREADY_REGISTER.getCode(),
                    ErrorCode.PHONE_ALREADY_REGISTER.getMsg());
        }

        account = accountService.register(phone, password, name, email, avatarUrl, gendar);
        teacherService.save(account, CourseEnum.getCourseEnum(type).getCode());

        return ResultResponse.success();
    }

    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public ResultResponse<?> updateAccount(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);

        String name = params.getString("name");
        String email = params.getString("email");
        String avatarUrl = params.getString("avatarUrl");
        Integer type = params.getInteger("type");
        Boolean gendar = params.getBoolean("gendar");

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || null == type) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        accountService.updateAccount(teacher.getAccountId(), name, email, avatarUrl, gendar, type);

        return ResultResponse.success();
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResultResponse<?> updatePassword(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);

        String password = params.getString("password");
        String passwordTwo = params.getString("passwordTwo");
        String passwordNew = params.getString("passwordNew");

        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordTwo) || null == passwordNew) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (!password.equals(passwordTwo)) {
            return ResultResponse.define(ErrorCode.PASSWORD_NOT_THE_SAME.getCode(),
                    ErrorCode.PASSWORD_NOT_THE_SAME.getMsg());
        }

        accountService.updatePassword(teacher.getAccountId(), password, passwordNew);

        return ResultResponse.success();
    }

    @IgnoreUserToken
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultResponse<?> login(@RequestBody JSONObject params) {
        String phone = params.getString("phone");
        String password = params.getString("password");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        JSONObject jsonObject = accountService.loginVerify(phone, password);
        ErrorCode errorCode = (ErrorCode) jsonObject.get("error");
        if (null == errorCode) {
            Teacher teacher = (Teacher) jsonObject.get("teacher");
            String userToken = loginService.getToken(loginService.getCredence(teacher.getId())).get("userToken");
            jsonObject.put("userToken", userToken);
            jsonObject.put("uid", teacher.getId());
            return ResultResponse.success(jsonObject);
        } else {
            return ResultResponse.define(errorCode.getCode(), errorCode.getMsg());
        }
    }

    /**
     * 获取自己的学生
     * @param params
     * @param request
     * @return
     */
    @IgnoreUserToken
    @RequestMapping(value = "/getMyStudents", method = RequestMethod.POST)
    public ResultResponse<?> getMyStudents(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);

        TeacherStudentMap teacherStudentMap = new TeacherStudentMap();
        teacherStudentMap.setTeacherId(teacher.getId());

        List<TeacherStudentMap> teacherStudentMapList = teacherStudentMapService.select(teacherStudentMap);
        List<Integer> studentIds = teacherStudentMapList.stream().map(t -> t.getStudentId())
                .collect(Collectors.toList());

        List<Student> students = Lists.newArrayList();
        studentIds.stream().forEach(studentId -> {
            students.add(studentService.selectByPrimaryKey(studentId));
        });
        return ResultResponse.success(students);
    }

    /**
     * 对提交绑定的学生审核
     * @param params
     * @param request
     * @return
     */
    @IgnoreUserToken
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ResultResponse<?> review(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);
        List<Integer> studentIds = params.getJSONArray("studentIds").toJavaList(Integer.class);

        List<Integer> errorStudentIds = validateStudentIds(studentIds);
        if (!CollectionUtils.isEmpty(errorStudentIds)) {
            return ResultResponse.define(ErrorCode.STUDENTIDS_ERROR.getCode(),
                    ErrorCode.STUDENTIDS_ERROR.getMsg() + "错误ID为: " + errorStudentIds.toString());
        }

        studentIds.stream().forEach(studentId -> {
            TeacherStudentMap teacherStudentMap = new TeacherStudentMap();
            teacherStudentMap.setTeacherId(teacher.getId());
            teacherStudentMap.setStudentId(studentId);
            teacherStudentMap = teacherStudentMapService.selectOne(teacherStudentMap);

            if (teacherStudentMap != null) {
                teacherStudentMap.setAuditStatus(true);
                teacherStudentMapService.updateByPrimaryKeySelective(teacherStudentMap);

                // 审核成功后更新student binded_teacherIds(触发更新操作)后面再加个定时任务刷新
                updateStudent(studentId);
            }
        });
        return ResultResponse.success();
    }

    private void updateStudent(Integer studentId) {
        Student student = studentService.selectByPrimaryKey(studentId);
        TeacherStudentMap teacherStudentMap = new TeacherStudentMap();
        teacherStudentMap.setStudentId(studentId);
        List<TeacherStudentMap> teacherStudentMapList = teacherStudentMapService.select(teacherStudentMap);

        List<Integer> teacherIds = teacherStudentMapList.stream().map(TeacherStudentMap::getTeacherId)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(teacherIds)) {
            Integer[] teacherIdsArray = teacherIds.toArray(new Integer[teacherIds.size()]);
            student.setBindedTeacherids(StringUtils.join(teacherIdsArray, ","));
            studentService.updateByPrimaryKeySelective(student);
        }
    }

    private List<Integer> validateStudentIds(List<Integer> studentIds) {
        List<Integer> errorList = Lists.newArrayList();
        studentIds.stream().forEach(studentId -> {
            if (studentService.selectByPrimaryKey(studentId) == null) {
                errorList.add(studentId);
            }
        });
        return errorList;
    }

}

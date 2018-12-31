package com.ik.service.miniprogram.controller.api.pc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.AccountService;
import com.ik.service.miniprogram.service.LoginService;
import com.ik.service.miniprogram.service.TeacherService;


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

        Account account = accountService.register(phone, password, name, email, avatarUrl, gendar);
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

        String password = params.getString("password ");
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
        if (jsonObject.getInteger("code").equals(0)) {
            Teacher teacher = (Teacher) jsonObject.get("teacher");
            String userToken = loginService.getToken(loginService.getCredence(teacher.getId())).get("userToken");
            jsonObject.put("userToken", userToken);
            return ResultResponse.success(jsonObject);
        } else if (jsonObject.getInteger("code").equals(ErrorCode.ACCOUNT_NOT_EXIST.getCode())) {
            return ResultResponse.define(ErrorCode.ACCOUNT_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_NOT_EXIST.getMsg());
        } else if (jsonObject.getInteger("code").equals(ErrorCode.ACCOUNT_NOT_USABLE.getCode())) {
            return ResultResponse.define(ErrorCode.ACCOUNT_NOT_USABLE.getCode(), ErrorCode.ACCOUNT_NOT_USABLE.getMsg());
        } else if (jsonObject.getInteger("code").equals(ErrorCode.USER_NOT_EXIST.getCode())) {
            return ResultResponse.define(ErrorCode.USER_NOT_EXIST.getCode(), ErrorCode.USER_NOT_EXIST.getMsg());
        } else if (jsonObject.getInteger("code").equals(ErrorCode.LOGIN_FAILED.getCode())) {
            return ResultResponse.define(ErrorCode.LOGIN_FAILED.getCode(), ErrorCode.LOGIN_FAILED.getMsg());
        } else if (jsonObject.getInteger("code").equals(ErrorCode.PASSWORD_WRONG.getCode())) {
            return ResultResponse.define(ErrorCode.PASSWORD_WRONG.getCode(), ErrorCode.PASSWORD_WRONG.getMsg());
        }
        return ResultResponse.success();
    }

}

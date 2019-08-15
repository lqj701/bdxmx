package com.ik.service.miniprogram.controller.api.mp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.controller.api.pc.AbstractUserController;
import com.ik.service.miniprogram.service.QuestionService;

@RestController
@RequestMapping(value = "/api/mp", produces = "application/json;utf-8")
public class MpQuestionController extends AbstractUserController {
    private static final Logger logger = LoggerFactory.getLogger(MpQuestionController.class);

    @Autowired
    private QuestionService questionService;

    @IgnoreUserToken
    @GetMapping(value = "/getQuestion/{questionId}")
    public ResultResponse<?> getQuestion(@PathVariable Integer questionId) {
        if (null == questionId) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        return ResultResponse.success(questionService.selectByPrimaryKey(questionId));
    }

}

package com.ik.service.miniprogram.controller.api.pc;

import java.util.List;

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
import com.ik.service.miniprogram.constants.QuestionEnum;
import com.ik.service.miniprogram.model.Question;
import com.ik.service.miniprogram.service.QuestionService;

@IgnoreUserToken
@RestController
@RequestMapping(value = "/api/question", produces = "application/json;utf-8")
public class QuestionController extends AbstractUserController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultResponse<?> addQuestion(@RequestBody JSONObject params) {
        Integer courseType = params.getInteger("courseType");
        Integer questionType = params.getInteger("questionType");
        String stem = params.getString("stem");
        String questionChoice = params.getString("questionChoice");
        String answer = params.getString("answer");
        Integer accountId = params.getInteger("accountId");
        Float point = params.getFloat("point");

        if (null == stem || null == answer) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        Question question = questionService.save(CourseEnum.getCourseEnum(courseType).getCode(),
                QuestionEnum.getQuestionEnum(questionType).getCode(), stem, questionChoice,answer, accountId, point);

        return ResultResponse.success(question);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultResponse<?> updateQuestion(@RequestBody JSONObject params) {
        Integer questionId = params.getInteger("questionId");
        String stem = params.getString("stem");
        String questionChoice = params.getString("questionChoice");
        String answer = params.getString("answer");
        Float point = params.getFloat("point");

        if (null == questionId || null == stem || null == answer) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        Question question = questionService.update(questionId, stem,questionChoice, answer, point);

        return ResultResponse.success(question);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultResponse<?> deleteQuestions(@RequestBody JSONObject params) {
        List<Integer> questionIds = params.getJSONArray("questionIds").toJavaList(Integer.class);

        if (null == questionIds) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (CollectionUtils.isEmpty(questionIds)) {
            return ResultResponse.define(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg());
        }

        questionService.deleteByIds(questionIds);

        return ResultResponse.success();
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResultResponse<?> getQuestions(@RequestBody JSONObject params) {
        List<Integer> questionIds = params.getJSONArray("questionIds").toJavaList(Integer.class);

        if (null == questionIds) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (CollectionUtils.isEmpty(questionIds)) {
            return ResultResponse.define(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg());
        }

        return ResultResponse.success(questionService.getByIds(questionIds));
    }


}

package com.ik.service.miniprogram.controller.api.pc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.extend.page.param.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.QuestionService;

@RestController
@RequestMapping(value = "/api/question", produces = "application/json;utf-8")
public class QuestionController extends AbstractUserController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultResponse<?> addQuestion(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);
        Integer courseType = params.getInteger("courseType");
        Integer questionType = params.getInteger("questionType");
        String stem = params.getString("stem");
        String questionChoice = params.getString("questionChoice");
        String answer = params.getString("answer");
        Float point = params.getFloat("point");
        String questionImage = params.getString("questionImage");
        String questionAudio = params.getString("questionAudio");
        String questionVideo = params.getString("questionVideo");
        String questionExplain = params.getString("questionExplain");

        if (StringUtils.isEmpty(stem) || StringUtils.isEmpty(answer) || StringUtils.isEmpty(questionExplain)
                || StringUtils.isEmpty(point) || StringUtils.isEmpty(questionType)) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (QuestionEnum.choice.getCode().equals(questionType)) {
            if (StringUtils.isEmpty(questionChoice)) {
                return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
            }
        }


        Question question = questionService.save(CourseEnum.getCourseEnum(courseType).getCode(),
                QuestionEnum.getQuestionEnum(questionType).getCode(), stem, questionChoice, answer, teacher.getId(),
                point, questionImage, questionAudio, questionVideo, questionExplain);

        return ResultResponse.success(question);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public ResultResponse<?> getQuestionList(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);
        Integer page = params.getInteger("page");
        Integer row = params.getInteger("row");

        if (null == page || null == row) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        Page pageParam = new Page(row, page);

        return ResultResponse.success(questionService.getByTeacherId(teacher.getId(), pageParam));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultResponse<?> updateQuestion(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);
        Integer questionId = params.getInteger("questionId");
        String stem = params.getString("stem");
        String questionChoice = params.getString("questionChoice");
        String answer = params.getString("answer");
        Float point = params.getFloat("point");
        String questionImage = params.getString("questionImage");
        String questionAudio = params.getString("questionAudio");
        String questionVideo = params.getString("questionVideo");

        if (null == questionId || null == stem || null == answer) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        Question question = new Question();
        question.setTeacherId(teacher.getId());
        List<Question> questionList = questionService.select(question);
        Question questionOld = questionService.selectByPrimaryKey(questionId);
        if (!questionList.contains(questionOld)) {
            return ResultResponse.define(ErrorCode.NOT_AUTH.getCode(), ErrorCode.NOT_AUTH.getMsg());
        }
        Question questionNew = questionService.update(questionOld, stem, questionChoice, answer, point, questionImage,
                questionAudio, questionVideo);

        return ResultResponse.success(questionNew);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultResponse<?> deleteQuestions(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);
        List<Integer> questionIds = params.getJSONArray("questionIds").toJavaList(Integer.class);
        if (null == questionIds) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (CollectionUtils.isEmpty(questionIds)) {
            return ResultResponse.define(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg());
        }
        List<Question> questions = questionService.getByIds(questionIds);
        Question question = new Question();
        question.setTeacherId(teacher.getId());
        List<Question> questionList = questionService.select(question);
        for (Question q : questions) {
            if (!questionList.contains(q)) {
                return ResultResponse.define(ErrorCode.NOT_AUTH.getCode(), ErrorCode.NOT_AUTH.getMsg());
            }
        }
        questionService.deleteByIds(questionIds);

        return ResultResponse.success();
    }

    @IgnoreUserToken
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResultResponse<?> getQuestions(@RequestBody JSONObject params, HttpServletRequest request) {
        // Teacher teacher = getUser(request);
        List<Integer> questionIds = params.getJSONArray("questionIds").toJavaList(Integer.class);

        if (null == questionIds) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (CollectionUtils.isEmpty(questionIds)) {
            return ResultResponse.define(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg());
        }
        List<Question> questions = questionService.getByIds(questionIds);
        // Question question = new Question();
        // question.setTeacherId(teacher.getId());
        // List<Question> questionList = questionService.select(question);
        // for(Question q : questions){
        // if(!questionList.contains(q)) {
        // return ResultResponse.define(ErrorCode.NOT_AUTH.getCode(),ErrorCode.NOT_AUTH.getMsg());
        // }
        // }
        return ResultResponse.success(questions);
    }

}

package com.bdxmx.miniprogram.controller.api.pc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bdxmx.miniprogram.constants.CourseEnum;
import com.bdxmx.miniprogram.constants.ErrorCode;
import com.bdxmx.miniprogram.model.ExamPaper;
import com.bdxmx.miniprogram.model.Question;
import com.bdxmx.miniprogram.model.Teacher;
import com.bdxmx.miniprogram.service.ExamPaperService;
import com.bdxmx.miniprogram.service.QuestionService;
import com.ik.crm.commons.dto.ResultResponse;

@RestController
@RequestMapping(value = "/api/paper", produces = "application/json;utf-8")
public class ExamPaperController extends AbstractUserController {
    private static final Logger logger = LoggerFactory.getLogger(ExamPaperController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExamPaperService examPaperService;

    @RequestMapping(value = "/getQuestionsForTeacher", method = RequestMethod.POST)
    public ResultResponse<?> getQuestionsForTeacher(@RequestBody JSONObject params, HttpServletRequest request) {
        Teacher teacher = getUser(request);

        List<Question> questions = questionService.getByTeacherId(teacher.getId());

        return ResultResponse.success(questions);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultResponse<?> addPaper(@RequestBody JSONObject params, HttpServletRequest request) {
        Integer paperType = params.getInteger("paperType");
        String name = params.getString("name");
        List<Integer> questionIds = params.getJSONArray("questionIds").toJavaList(Integer.class);
        Integer totalScore = params.getInteger("totalScore");
        Teacher teacher = getUser(request);

        if (null == paperType || null == name || null == questionIds || null == totalScore) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        examPaperService.saveExamPaper(CourseEnum.getCourseEnum(paperType).getCode(), name, teacher.getId(),
                questionIds.toString(), totalScore);

        return ResultResponse.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultResponse<?> updatePaper(@RequestBody JSONObject params) {
        Integer examPaperId = params.getInteger("examPaperId");
        Integer paperType = params.getInteger("paperType");
        String name = params.getString("name");
        List<Integer> questionIds = params.getJSONArray("questionIds").toJavaList(Integer.class);
        Integer totalScore = params.getInteger("totalScore");


        if (null == examPaperId || null == paperType || null == name || null == questionIds || null == totalScore) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }

        ExamPaper examPaper = examPaperService.updateExamPaper(examPaperId,
                CourseEnum.getCourseEnum(paperType).getCode(), name, questionIds.toString(), totalScore);

        if (null == examPaper) {
            return ResultResponse.define(ErrorCode.DATA_NOT_EXIST.getCode(), ErrorCode.DATA_NOT_EXIST.getMsg());
        }
        return ResultResponse.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultResponse<?> deletePaper(@RequestBody JSONObject params) {
        List<Integer> examPaperIds = params.getJSONArray("examPaperIds").toJavaList(Integer.class);

        if (null == examPaperIds) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        if (CollectionUtils.isEmpty(examPaperIds)) {
            return ResultResponse.define(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg());
        }


        examPaperService.deleteByIds(examPaperIds);

        return ResultResponse.success();
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResultResponse<?> getPaper(@RequestBody JSONObject params) {
        Integer examPaperId = params.getInteger("examPaperId");

        if (null == examPaperId) {
            return ResultResponse.define(ErrorCode.PARAM_IS_NULL.getCode(), ErrorCode.PARAM_IS_NULL.getMsg());
        }
        JSONObject data = examPaperService.getPaper(examPaperId);
        if (null == data) {
            return ResultResponse.define(ErrorCode.DATA_NOT_EXIST.getCode(), ErrorCode.DATA_NOT_EXIST.getMsg());
        }
        return ResultResponse.success(data);
    }

}

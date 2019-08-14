package com.ik.service.miniprogram.controller.api.mp;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.model.AnswerSheet;
import com.ik.service.miniprogram.model.ExamPaper;
import com.ik.service.miniprogram.model.TeacherStudentMap;
import com.ik.service.miniprogram.service.*;
import com.ik.service.miniprogram.vo.AnswerSheetRequest;

@RestController
@RequestMapping(value = "/api/mp", produces = "application/json;utf-8")
@IgnoreUserToken
public class MpAnswerSheetController {
    @Autowired
    private AnswerSheetService answerSheetService;
    @Autowired
    private TeacherStudentMapService teacherStudentMapService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private QuestionService questionService;

    /**
     * 获取随机试卷列表
     * @return
     */
    @GetMapping("/getPapers")
    public ResultResponse getPapers() {
        List<ExamPaper> examPaperList = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            ExamPaper examPaper = new ExamPaper();
            examPaper.setPaperType(i);
            examPaper = examPaperService.selectOne(examPaper);
            if (examPaper != null) {
                examPaperList.add(examPaper);
            }
        }
        return ResultResponse.success(examPaperList);
    }

    /**
     * 获取自己老师出的考卷列表
     * @return
     */
    @GetMapping("/getPapers/{studentId}")
    public ResultResponse getPapers(@PathVariable Integer studentId) {
        TeacherStudentMap teacherStudentMap = new TeacherStudentMap();
        teacherStudentMap.setStudentId(studentId);
        List<TeacherStudentMap> teacherStudentMapList = teacherStudentMapService.select(teacherStudentMap);
        List<Integer> teacherIds = teacherStudentMapList.stream().map(TeacherStudentMap::getTeacherId)
                .collect(Collectors.toList());

        List<ExamPaper> examPaperList = Lists.newArrayList();
        teacherIds.stream().forEach(teacherId -> {
            ExamPaper examPaper = new ExamPaper();
            examPaper.setTeacherId(teacherId);
            List<ExamPaper> examPapers = examPaperService.select(examPaper);

            examPaperList.addAll(examPapers);
        });

        return ResultResponse.success(examPaperList);
    }

    /**
     * 学生提交答卷，提交后不可更改
     * @param params
     * @return
     */
    @PostMapping("/saveAnswerSheet")
    public ResultResponse saveAnswerSheet(@RequestBody AnswerSheetRequest params) {
        answerSheetService.saveAnswerSheet(params);
        return ResultResponse.success();
    }

    @GetMapping("/getAnswerSheets/{studentId}")
    public ResultResponse getAnswerSheets(HttpServletRequest request, @PathVariable Integer studentId) {
        AnswerSheet query = new AnswerSheet();
        query.setStudentId(studentId);

        List<AnswerSheet> answerSheetList = answerSheetService.select(query);
        List<JSONObject> data = answerSheetList.stream().map(t -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("answerSheet", t);
            jsonObject.put("setPerson", teacherService.selectByPrimaryKey(t.getTeacherId()).getName());
            jsonObject.put("paperName", examPaperService.selectByPrimaryKey(t.getPaperId()).getName());
            return jsonObject;
        }).collect(Collectors.toList());

        return ResultResponse.success(data);
    }

    @GetMapping("/getAnswerSheet/{answerSheetId}")
    public ResultResponse getAnswerSheet(@PathVariable Integer answerSheetId) {
        AnswerSheet answerSheet = answerSheetService.selectByPrimaryKey(answerSheetId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("answerSheet", answerSheet);
        jsonObject.put("setPerson", teacherService.selectByPrimaryKey(answerSheet.getTeacherId()).getName());
        jsonObject.put("paperName", examPaperService.selectByPrimaryKey(answerSheet.getPaperId()).getName());

        Integer examPaperId = answerSheet.getPaperId();
        ExamPaper examPaper = examPaperService.selectByPrimaryKey(examPaperId);
        String questionIds = examPaper.getQuestionIds();
        String[] questionIdArray = questionIds.substring(1, questionIds.length() - 1).split(",");
        JSONArray standardAnswer = new JSONArray();
        JSONArray answerExplain = new JSONArray();
        for (int i = 0; i < questionIdArray.length; i++) {
            standardAnswer.add(questionService.selectByPrimaryKey(Integer.valueOf(questionIdArray[i].replaceAll(" ", "")))
                    .getQuestionAnswer());
            answerExplain.add(questionService.selectByPrimaryKey(Integer.valueOf(questionIdArray[i].replaceAll(" ", "")))
                    .getQuestionExplain());
        }
        jsonObject.put("standardAnswer", standardAnswer);
        jsonObject.put("answerExplain", answerExplain);
        return ResultResponse.success(jsonObject);
    }

}

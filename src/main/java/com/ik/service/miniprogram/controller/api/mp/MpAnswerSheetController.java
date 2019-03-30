package com.ik.service.miniprogram.controller.api.mp;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.model.ExamPaper;
import com.ik.service.miniprogram.model.TeacherStudentMap;
import com.ik.service.miniprogram.service.AnswerSheetService;
import com.ik.service.miniprogram.service.ExamPaperService;
import com.ik.service.miniprogram.service.TeacherStudentMapService;
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

    /**
     * 获取自己老师出的考卷列表
     * @return
     */
    @GetMapping("/getPapers/{studentId}")
    public ResultResponse getPapers(@PathVariable Integer studentId) {
        TeacherStudentMap teacherStudentMap = new TeacherStudentMap();
        teacherStudentMap.setStudentId(studentId);
        List<TeacherStudentMap> teacherStudentMapList = teacherStudentMapService.select(teacherStudentMap);
        List<Integer> teacherIds = teacherStudentMapList.stream().map(TeacherStudentMap::getTeacherId).collect(Collectors.toList());

        List<ExamPaper> examPaperList = Lists.newArrayList();
        teacherIds.stream().forEach(teacherId-> {
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

}

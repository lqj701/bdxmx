package com.ik.service.miniprogram.controller.api.mp;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.model.AnswerSheet;
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
     * 获取随机试卷列表
     * @return
     */
    @GetMapping("/getPapers")
    public ResultResponse getPapers() {
        List<ExamPaper> examPaperList = Lists.newArrayList();
        for(int i=1;i<10;i++) {
            ExamPaper examPaper = new ExamPaper();
            examPaper.setPaperType(i);
            examPaper = examPaperService.selectOne(examPaper);
            if(examPaper!=null) {
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

    @GetMapping("/getAnswerSheets/{studentId}")
    public ResultResponse getAnswerSheets(HttpServletRequest request, @PathVariable Integer studentId) {
        AnswerSheet query = new AnswerSheet();
        query.setStudentId(studentId);

        List<AnswerSheet> answerSheetList = answerSheetService.select(query);
        return ResultResponse.success(answerSheetList);
    }

    @GetMapping("/getAnswerSheet/{answerSheetId}")
    public ResultResponse getAnswerSheet(@PathVariable Integer answerSheetId) {
        return ResultResponse.success(answerSheetService.selectByPrimaryKey(answerSheetId));
    }

}

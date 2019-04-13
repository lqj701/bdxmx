package com.ik.service.miniprogram.controller.api.pc;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.model.AnswerSheet;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.AnswerSheetService;
import com.ik.service.miniprogram.service.ExamPaperService;
import com.ik.service.miniprogram.service.TeacherService;
import com.ik.service.miniprogram.vo.AnswerSheetRequest;

@RestController
@RequestMapping(value = "/api/pc", produces = "application/json;utf-8")
public class PcAnswerSheetController extends AbstractUserController {
    @Autowired
    private AnswerSheetService answerSheetService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private TeacherService teacherService;


    @GetMapping("/getAnswerSheets/{studentId}")
    public ResultResponse getAnswerSheets(HttpServletRequest request, @PathVariable Integer studentId) {
        Teacher teacher = getUser(request);
        AnswerSheet query = new AnswerSheet();
        query.setTeacherId(teacher.getId());
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

        return ResultResponse.success(jsonObject);
    }

    /**
     * 老师后台批改学生作业
     * @param params
     * @return
     */
    @IgnoreUserToken
    @PostMapping("/reviseAnswerSheet")
    public ResultResponse reviseAnswerSheet(@RequestBody AnswerSheetRequest params) {
        answerSheetService.reviseAnswerSheet(params);
        return ResultResponse.success();
    }
}

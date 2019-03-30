package com.ik.service.miniprogram.controller.api.pc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.model.AnswerSheet;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.AnswerSheetService;
import com.ik.service.miniprogram.vo.AnswerSheetRequest;

@RestController
@RequestMapping(value = "/api/pc", produces = "application/json;utf-8")
public class PcAnswerSheetController extends AbstractUserController{
    @Autowired
    private AnswerSheetService answerSheetService;


    @GetMapping("/getAnswerSheets/{studentId}")
    public ResultResponse getAnswerSheets(HttpServletRequest request,@PathVariable Integer studentId) {
        Teacher teacher = getUser(request);
        AnswerSheet query = new AnswerSheet();
        query.setTeacherId(teacher.getId());
        query.setStudentId(studentId);

        List<AnswerSheet> answerSheetList = answerSheetService.select(query);
        return ResultResponse.success(answerSheetList);
    }

    @GetMapping("/getAnswerSheet/{answerSheetId}")
    public ResultResponse getAnswerSheet(@PathVariable Integer answerSheetId) {
        return ResultResponse.success(answerSheetService.selectByPrimaryKey(answerSheetId));
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

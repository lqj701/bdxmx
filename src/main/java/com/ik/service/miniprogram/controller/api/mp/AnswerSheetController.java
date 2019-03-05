package com.ik.service.miniprogram.controller.api.mp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.model.AnswerSheet;
import com.ik.service.miniprogram.service.AnswerSheetService;
import com.ik.service.miniprogram.vo.AnswerSheetRequest;

@RestController
@RequestMapping(value = "/api/mp", produces = "application/json;utf-8")
@IgnoreUserToken
public class AnswerSheetController {
    @Autowired
    private AnswerSheetService answerSheetService;

    @GetMapping("/getAnswerSheets/{studentId}")
    public ResultResponse getAnswerSheets(@PathVariable Integer studentId) {
        AnswerSheet query = new AnswerSheet();
        query.setStudentId(studentId);

        List<AnswerSheet> answerSheetList = answerSheetService.select(query);
        return ResultResponse.success(answerSheetList);
    }

    @GetMapping("/getAnswerSheet/{answerSheetId}")
    public ResultResponse getAnswerSheet(@PathVariable Integer answerSheetId) {
        return ResultResponse.success(answerSheetService.selectByPrimaryKey(answerSheetId));
    }

    @PostMapping("/saveAnswerSheet")
    public ResultResponse saveAnswerSheet(@RequestBody AnswerSheetRequest params) {
        answerSheetService.saveAnswerSheet(params);
        return ResultResponse.success();
    }
}

package com.ik.service.miniprogram.controller.api.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.annotation.IgnoreUserToken;
import com.ik.service.miniprogram.service.AnswerSheetService;
import com.ik.service.miniprogram.vo.AnswerSheetRequest;

@RestController
@RequestMapping(value = "/api/mp", produces = "application/json;utf-8")
@IgnoreUserToken
public class MpAnswerSheetController {
    @Autowired
    private AnswerSheetService answerSheetService;



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

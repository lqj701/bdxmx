package com.ik.service.miniprogram.controller.api.script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.mq.rocketmq.RocketmqProducer;

@RestController
@RequestMapping(value = "/api/test", produces = "application/json;utf-8")
public class TestMQController extends BaseScriptController {
    @Autowired
    private RocketmqProducer rocketmqProducer;

    @GetMapping(value = "/mq")
    public ResultResponse mq(String msg) {
        rocketmqProducer.sendUserMsg(msg);
        return ResultResponse.success();
    }
}

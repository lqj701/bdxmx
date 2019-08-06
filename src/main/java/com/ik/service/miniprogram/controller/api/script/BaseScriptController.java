package com.ik.service.miniprogram.controller.api.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ik.crm.commons.exception.SystemException;
import com.ik.service.miniprogram.prop.AuthProperty;

public abstract class BaseScriptController {

    private static Logger logger = LoggerFactory.getLogger(BaseScriptController.class);

    @Autowired
    private AuthProperty authProperty;

    @ModelAttribute
    public void verityScriptToken(@RequestHeader(value = "scriptToken", required = false) String scriptToken) {
        logger.info("Verify Script Token: {}, auth.scriptToken = {}", scriptToken, authProperty.getScriptToken());

        if (scriptToken == null || !authProperty.getScriptToken().equals(scriptToken)) {
            logger.info("Script Token error!");

            throw new SystemException();
        }
    }

}

package com.bdxmx.miniprogram.exception.handler;

import com.bdxmx.miniprogram.exception.MPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.crm.commons.exception.SystemException;

@ControllerAdvice
@ResponseBody
@SuppressWarnings("rawtypes")
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {SystemException.class})
    public ResultResponse<?> handleSystemException(SystemException e) {
        logger.error("catch a SystemException in GlobalExceptionHandler: " + e.getMessage(), e);

        return new ResultResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {MPException.class})
    public ResultResponse<?> handleUserCenterException(MPException e) {
        logger.error("catch a SystemException in GlobalExceptionHandler: " + e.getMessage(), e);

        return new ResultResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {java.lang.Exception.class, java.lang.RuntimeException.class})
    public ResultResponse<?> handle(Throwable e) {
        logger.error("catch a exception in GlobalExceptionHandler: " + e.getMessage(), e);

        return ResultResponse.failed(e.getMessage());
    }
}

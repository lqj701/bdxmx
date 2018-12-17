package com.ik.miniprogram.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.crm.commons.exception.SystemException;
import com.ik.service.miniprogram.exception.MPException;

@ControllerAdvice
@ResponseBody
@SuppressWarnings("rawtypes")
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {SystemException.class})
    public ResultResponse<?> handleSystemException(SystemException e) {
        GlobalExceptionHandler.logger.error("catch a SystemException in GlobalExceptionHandler: " + e.getMessage(), e);

        return new ResultResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {MPException.class})
    public ResultResponse<?> handleUserCenterException(MPException e) {
        GlobalExceptionHandler.logger.error("catch a SystemException in GlobalExceptionHandler: " + e.getMessage(), e);

        return new ResultResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {java.lang.Exception.class, java.lang.RuntimeException.class})
    public ResultResponse<?> handle(Throwable e) {
        GlobalExceptionHandler.logger.error("catch a exception in GlobalExceptionHandler: " + e.getMessage(), e);

        return ResultResponse.failed(e.getMessage());
    }
}

package com.ik.service.miniprogram.exception;


import com.ik.service.miniprogram.constants.ErrorCode;

public class MPException extends RuntimeException {
    /**
     */
    private static final long serialVersionUID = -5300077853740848235L;
    private Integer code;

    public MPException() {
    }

    public MPException(String message) {
        super(message);
        code = ErrorCode.SYSTEM_ERROR.getCode();
    }

    public MPException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public MPException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public MPException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        code = errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

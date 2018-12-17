package com.ik.service.miniprogram.constants;

public enum ErrorCode {
    REGISTER_SUCCESS(100000, "注册成功,请等待系统管理员审核！"),

    UPDATE_INFO_SUCCESS(100001, "更新账户信息成功，请等待系统管理员审核！"),

    RESET_PASSWORD_SUCCESS(100002, "修改密码成功，请等待系统管理员审核！"),

    PARAM_IS_NULL(200001, "参数不可为空"),

    PARAM_ERROR(200002, "参数错误"),

    ACCOUNT_NOT_EXIST(200003, "账号不存在"),

    ACCOUNT_NOT_ONLY(200004, "账号不唯一"),

    ACCOUNT_NOT_USABLE(200005, "账号不可用"),

    USER_NOT_EXIST(200006, "用户不存在"),

    LOGIN_FAILED(200007, "登录错误，请联系系统管理员"),

    PASSWORD_WRONG(200008, "密码错误"),

    PASSWORD_NOT_THE_SAME(200009, "两次密码不一致"),

    DATA_NOT_EXIST(200010, "数据不存在"),

    USERTOKEN_IS_NULL(200011, "userToken为空"),

    TOKEN_EXPIRATE_OR_USER_TOKEN_ERR(200012, "token过期或userToken不正确"),

    CREDENCE_ERROR(200013, "credence错误"),

    SYSTEM_ERROR(200014, "系统错误");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

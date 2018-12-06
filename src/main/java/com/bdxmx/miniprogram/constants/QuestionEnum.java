package com.bdxmx.miniprogram.constants;

public enum QuestionEnum {
    choice(1,"xuanze","选择题"),

    fillinBlank(2,"tiankong","填空题"),

    judgement(3,"panduan","判断题"),

    answer(4,"jieda","解答题");


    QuestionEnum(Integer code, String name, String cnName){
        this.code = code;
        this.name = name;
        this.cnName = cnName;
    }
    private Integer code;
    private String name;
    private String cnName;

    public Integer getCode() {
        return code;
    }


    public String getName() {
        return name;
    }


    public String getCnName() {
        return cnName;
    }


    public static QuestionEnum getQuestionEnum(String name) {
        for (QuestionEnum questionEnum : QuestionEnum.values()) {
            if (questionEnum.name().equals(name)) {
                return questionEnum;
            }
        }
        return null;
    }

    public static QuestionEnum getQuestionEnum(Integer code) {
        for (QuestionEnum questionEnum : QuestionEnum.values()) {
            if (questionEnum.getCode().equals(code)) {
                return questionEnum;
            }
        }
        return null;
    }
}

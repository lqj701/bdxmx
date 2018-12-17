package com.ik.service.miniprogram.constants;

public enum CourseEnum {
    yuwen(1,"yuwen","语文"),

    shuxue(2,"shuxue","数学"),

    yingyu(3,"yingyu","英语"),

    wuli(4,"wuli","物理"),

    huaxue(5,"huaxue","化学"),

    shengwu(6,"shengwu","生物"),

    zhengzhi(7,"zhengzhi","政治"),

    lishi(8,"lishi","历史"),

    dili(9,"dili","地理");

    CourseEnum(Integer code,String name,String cnName){
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


    public static CourseEnum getCourseEnum(String name) {
        for (CourseEnum courseEnum : CourseEnum.values()) {
            if (courseEnum.name().equals(name)) {
                return courseEnum;
            }
        }
        return null;
    }

    public static CourseEnum getCourseEnum(Integer code) {
        for (CourseEnum courseEnum : CourseEnum.values()) {
            if (courseEnum.getCode().equals(code)) {
                return courseEnum;
            }
        }
        return null;
    }
}

package com.ihomefnt.o2o.intf.manager.constant.programorder;


/**
 * 方案对比过程枚举
 */
public enum SolutionCompareEnum {

    COMMON(0, "基础方案价格"),

    LIVING_ROOM(1, "选配过程"),

    RESTAURANT(2, "整体风格"),

    MASTER_ROOM(3, "房间用途");

    private Integer code;

    private String description;


    private SolutionCompareEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static int getCode(String description) {
        for (SolutionCompareEnum typeEnum : SolutionCompareEnum.values()) {
            if (typeEnum.getDescription().equals(description)) {
                return typeEnum.getCode();
            }
        }
        return -1;
    }

    public static String getDescription(Integer code) {
        for (SolutionCompareEnum typeEnum : SolutionCompareEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getDescription();
            }
        }
        return null;
    }

}

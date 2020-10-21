package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum DesignDemondEnum {
    WAIT_AFFIRM_BETA("待确认", -2),
    UNNEED_DESIGN("正在分配设计师", -1),
    WAIT_DESIGN("正在分配设计师", 0),
    DESIGNING("方案正在设计中", 1),
    DESIGNED("已设计完成", 2),
    WAIT_ACCEPT("方案正在设计中", 3),
    INVALID("已取消", 4),
    INVALID2("已取消", -3),
    AUDITING("方案正在审核中", 5),
    AUDIT_NOTPASS("方案正在审核中", 6),
    CANCELLATION("已取消", 7),


    /**
     * wcm草稿状态
     */
    WAIT_SUBMIT("待提交", 111),
    WAIT_AFFIRM("待确认", 112),
    UNDER_DESIGN("设计中", 113),
    FINISHED("已完成", 114);


    private Integer code;

    private String statusStr;


    DesignDemondEnum(String statusStr, int code) {
        this.code = code;
        this.statusStr = statusStr;
    }

    public static DesignDemondEnum getEnumByCode(int code) {
        DesignDemondEnum[] values = values();
        for (DesignDemondEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}

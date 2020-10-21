package com.ihomefnt.o2o.service.proxy.dms.enums;

public enum DeliveryStatusEnum {

    /**
     * 0, "待交付"
     */
    WAIT_DELIVER(0, "转单阶段"),

    /**
     * 1, "需求确认"
     */
    REQUIREMENT_CONFIRM(1, "需求确认阶段"),

    /**
     * 2, "交付准备"
     */
    DELIVER_READY(2, "开工准备阶段"),

    /**
     * 3, "施工中/采购中"
     */
    CONSTRUCTING(3, "施工中/采购中"),

    /**
     * 5, "安装"
     */
    INSTALL(5, "软装安装阶段"),

    /**
     * 6, "竣工"
     */
    COMPLETE(6, "交付阶段"),

    /**
     * 7, "质保"
     */
    WARRANT(7, "质保阶段"),

    /**
     * 8, "完成"
     */
    FINISH(8, "已完结");

    private Integer code;

    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    DeliveryStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}

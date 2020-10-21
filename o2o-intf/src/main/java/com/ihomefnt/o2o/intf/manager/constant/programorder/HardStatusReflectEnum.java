package com.ihomefnt.o2o.intf.manager.constant.programorder;

public enum HardStatusReflectEnum {

    // hbms: 0-待排期 1-待施工 2-施工中 3-施工完成 4-待分配
    HARD_ORDER_PRE_DISTRIBUTION(1, 4, "待分配"),
    HARD_ORDER_PRE_SCHEDULE(2, 0, "待排期"),
    HARD_ORDER_PRE_CONSTRUCTION(3, 1, "待施工"),
    HARD_ORDER_CONSTRUCTIONING(4, 2, "施工中"),
    HARD_ORDER_COMPLETED(5, 3, "已完成"),;

    //order硬装状态
    private Integer status;

    //hbms硬装状态
    private Integer hbmsStatus;

    //描述
    private String desc;

    private HardStatusReflectEnum(Integer status, Integer hbmsStatus, String desc) {
        this.status = status;
        this.hbmsStatus = hbmsStatus;
    }

    public static HardStatusReflectEnum getOrderHardStatus(Integer hbmsStatus) {
        HardStatusReflectEnum[] values = values();
        for (HardStatusReflectEnum value : values) {
            if (value.getHbmsStatus().equals(hbmsStatus)) {
                return value;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHbmsStatus() {
        return hbmsStatus;
    }

    public void setHbmsStatus(Integer hbmsStatus) {
        this.hbmsStatus = hbmsStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

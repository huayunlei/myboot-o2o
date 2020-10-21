package com.ihomefnt.o2o.intf.manager.constant.home;

public enum SignSubStatusEnum {

    NO_SOLUTION(0, "未选方案"),

    WAIT_CONFIRM(1, "已选未确认"),

    WAIT_PAY_ALL(2, "已确认未结款"),

    CONFIRM_ADN_PAY_COMPLETED(3, "已确认已结款"),

    WAIT_CONSTRUCTION(4, "待确认开工");

    private Integer status;

    private String statusStr;

    SignSubStatusEnum(Integer status, String statusStr) {
        this.status = status;
        this.statusStr = statusStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}

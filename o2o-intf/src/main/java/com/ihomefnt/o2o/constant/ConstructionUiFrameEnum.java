package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum ConstructionUiFrameEnum {

    NOT_IN_CONSTRUCTION(1,"未到交付阶段"),
    WAIT_PLAN(2,"用户等待排期"),
    WAIT_CONSTRUCTION(3,"等待开工"),
    IN_CONSTRUCTION(4,"已开工"),
    COMPLETED(5,"已完成");

    private Integer uiFrameId;

    private String uiFrameName;


    ConstructionUiFrameEnum(int uiFrameId, String uiFrameName) {
        this.uiFrameId = uiFrameId;
        this.uiFrameName = uiFrameName;
    }

    public static ConstructionUiFrameEnum getEnumByFrameId(int uiFrameId){
        ConstructionUiFrameEnum[] values = values();
        for (ConstructionUiFrameEnum value : values) {
            if (value.getUiFrameId().equals(uiFrameId)) {
                return value;
            }
        }
        return null;
    }

    public Integer getUiFrameId() {
        return uiFrameId;
    }

    public void setUiFrameId(Integer uiFrameId) {
        this.uiFrameId = uiFrameId;
    }

    public String getUiFrameName() {
        return uiFrameName;
    }

    public void setUiFrameName(String uiFrameName) {
        this.uiFrameName = uiFrameName;
    }
}

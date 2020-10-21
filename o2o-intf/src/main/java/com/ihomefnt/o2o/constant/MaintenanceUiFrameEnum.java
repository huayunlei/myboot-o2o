package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum MaintenanceUiFrameEnum {

    NOT_IN_MAINTENANCE(1,"未到维保阶段"),
    IN_MAINTENANCE(2,"维保中"),
    OUT_MAINTENANCE(3,"已过质保期");

    private Integer uiFrameId;

    private String uiFrameName;


    MaintenanceUiFrameEnum(int uiFrameId, String uiFrameName) {
        this.uiFrameId = uiFrameId;
        this.uiFrameName = uiFrameName;
    }

    public static MaintenanceUiFrameEnum getEnumByFrameId(int uiFrameId){
        MaintenanceUiFrameEnum[] values = values();
        for (MaintenanceUiFrameEnum value : values) {
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

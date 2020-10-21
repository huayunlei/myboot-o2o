package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum DesignHomeUiFrameEnum {

    PAY_DEPOSIT(1,"交定金"),
    DESIGN_HOME(2,"设计我的家"),
    SEE_MY_DESIGN(3,"看看我的设计");

    private Integer uiFrameId;

    private String uiFrameName;


    DesignHomeUiFrameEnum(int uiFrameId, String uiFrameName) {
        this.uiFrameId = uiFrameId;
        this.uiFrameName = uiFrameName;
    }

    public static DesignHomeUiFrameEnum getEnumByFrameId(int uiFrameId){
        DesignHomeUiFrameEnum[] values = values();
        for (DesignHomeUiFrameEnum value : values) {
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

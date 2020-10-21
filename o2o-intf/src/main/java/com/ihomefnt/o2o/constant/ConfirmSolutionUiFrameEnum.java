package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum ConfirmSolutionUiFrameEnum {

    NO_SOLUTION(1,"未选方案"),
    WAIT_CONFIRM_SOLUTION(2,"待确认方案"),
    WAIT_PAY(3,"待结清款项"),
    PAY_ALREADY(4,"已结清款项未达到确认开工条件"),
    WAIT_CONFIRM_CONSTRUCT(5,"待确认开工"),
    IN_CONSTRUCTION(6,"订单已在交付中");

    private Integer uiFrameId;

    private String uiFrameName;


    ConfirmSolutionUiFrameEnum(int uiFrameId, String uiFrameName) {
        this.uiFrameId = uiFrameId;
        this.uiFrameName = uiFrameName;
    }

    public static ConfirmSolutionUiFrameEnum getEnumByFrameId(int uiFrameId){
        ConfirmSolutionUiFrameEnum[] values = values();
        for (ConfirmSolutionUiFrameEnum value : values) {
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

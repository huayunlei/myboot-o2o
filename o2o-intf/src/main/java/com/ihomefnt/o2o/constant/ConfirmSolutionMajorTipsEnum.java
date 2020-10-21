package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum ConfirmSolutionMajorTipsEnum {

    NO_SOLUTION(1,""),
    WAIT_CONFIRM_SOLUTION(2,"距离交房还有-天"),
    WAIT_PAY(3,"距离交房还有-天"),
    PAY_ALREADY(4,"已结清款项"),
    WAIT_CONFIRM_CONSTRUCT(5,"已结清款项"),
    IN_CONSTRUCTION(6,"已确认开工并结清款项");

    private Integer uiFrameId;

    private String majorTips;


    ConfirmSolutionMajorTipsEnum(int uiFrameId, String majorTips) {
        this.uiFrameId = uiFrameId;
        this.majorTips = majorTips;
    }

    public static ConfirmSolutionMajorTipsEnum getEnumByFrameId(int uiFrameId){
        ConfirmSolutionMajorTipsEnum[] values = values();
        for (ConfirmSolutionMajorTipsEnum value : values) {
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

    public String getMajorTips() {
        return majorTips;
    }

    public void setMajorTips(String majorTips) {
        this.majorTips = majorTips;
    }
}

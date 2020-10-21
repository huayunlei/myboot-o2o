package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum CheckUiFrameEnum {

    NOT_COMPLETE(1,"订单未完成"),
    WAIT_COMMENT(2,"待客户点评"),
    COMMENT_ALREADY(3,"已点评"),
    WAIT_CHECK(4,"待客户验收"),
    CHECK_NOT_PASS(5,"验收不通过");

    private Integer uiFrameId;

    private String uiFrameName;


    CheckUiFrameEnum(int uiFrameId, String uiFrameName) {
        this.uiFrameId = uiFrameId;
        this.uiFrameName = uiFrameName;
    }

    public static CheckUiFrameEnum getEnumByFrameId(int uiFrameId){
        CheckUiFrameEnum[] values = values();
        for (CheckUiFrameEnum value : values) {
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

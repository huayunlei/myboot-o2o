package com.ihomefnt.o2o.constant;

/**
 * @author xiamingyu
 */

public enum MainNodeEnum {

    ABOUT_US(1,"了解我们"),
    DESIGN_HOME(2,"设计我家"),
    CONFIRM_SOLUTION(3,"确认开工"),
    CONSTRUCTION(4,"施工"),
    CHECK(5,"验收"),
    MAINTENANCE(6,"维保");

    private Integer nodeId;

    private String nodeName;


    MainNodeEnum(int nodeId, String nodeName) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public static MainNodeEnum getEnumByNodeId(int nodeId){
        MainNodeEnum[] values = values();
        for (MainNodeEnum value : values) {
            if (value.getNodeId().equals(nodeId)) {
                return value;
            }
        }
        return null;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}

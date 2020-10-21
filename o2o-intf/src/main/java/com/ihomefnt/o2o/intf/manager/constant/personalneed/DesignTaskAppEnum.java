package com.ihomefnt.o2o.intf.manager.constant.personalneed;

/**
 * 后台设计任务状态枚举
 * xiamingyu
 */
public enum DesignTaskAppEnum {

    COMMITTED("已提交",1),
    IN_DESIGN("设计中", 2),
    DESIGN_FINISH("设计完成", 3),
    INVALID("已取消", -1);

    private Integer taskStatus;

    private String taskStatusStr;

    DesignTaskAppEnum(String taskStatusStr, Integer taskStatus) {
        this.taskStatus = taskStatus;
        this.taskStatusStr = taskStatusStr;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatusStr() {
        return taskStatusStr;
    }

    public void setTaskStatusStr(String taskStatusStr) {
        this.taskStatusStr = taskStatusStr;
    }
}

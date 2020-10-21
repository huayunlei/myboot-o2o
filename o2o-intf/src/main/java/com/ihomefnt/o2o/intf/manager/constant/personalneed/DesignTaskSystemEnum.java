package com.ihomefnt.o2o.intf.manager.constant.personalneed;

/**
 * 后台设计任务状态枚举
 * xiamingyu
 */
public enum DesignTaskSystemEnum {

    WAIT_SUBMIT_BETA("待确认",-2),
    UNNEED_DESIGN("无需分配",-1),
    WAIT_DESIGN("待分配", 0),
    DESIGNING("设计中", 1),
    DESIGNED("设计完成", 2),
    WAIT_ACCEPT("待确认",3),
    INVALID("已失效",4),
    INVALID2("已失效",-3),
	AUDITING("审核中", 5),
	AUDIT_NOTPASS("审核不通过", 6),
    CANCELLATION("已作废",7),


    /**
     * wcm草稿状态
     */
    WAIT_SUBMIT("待提交", 111),
    WAIT_AFFIRM("待确认", 112),
    UNDER_DESIGN("设计中", 113),
    FINISHED("已完成", 114);


    private Integer taskStatus;

    private String taskStatusStr;

    DesignTaskSystemEnum(String taskStatusStr,Integer taskStatus) {
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

package com.ihomefnt.o2o.intf.manager.constant.home;

/**
 * 全品家订单订单状态
 * Author: ZHAO
 * Date: 2018年7月20日
 */
public enum MasterOrderStatusEnum {
	ORDER_STATUS_PRE_DELIVERY(12, "待交付"),
    ORDER_STATUS_CONTACT_STAGE(13, "接触阶段"),
    ORDER_STATUS_INTENTIONAL_PHASE(14, "意向阶段"),
    ORDER_STATUS_DEPOSIT_PHASE(15, "定金阶段"),
    ORDER_STATUS_SIGNING_STAGE(16, "签约阶段"),
    ORDER_STATUS_IN_DELIVERY(17, "交付中"),
    ORDER_STATUS_COMPLETED(2, "已完成"),
    ORDER_STATUS_CANCELED(3, "已取消"),;
    
    private Integer status;
    
    private String description;
    
    private MasterOrderStatusEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
    
    public static String getDescription(Integer status){
        if (null == status) {
            return "";
        }
        MasterOrderStatusEnum[] values = values();
        for (MasterOrderStatusEnum value : values) {
            if (value.getStatus().equals(status)) {
                return value.getDescription();
            }
        }
        return "";
    }
    
    public static MasterOrderStatusEnum getEnumByStatus(Integer status){
    	MasterOrderStatusEnum[] values = values();
        for (MasterOrderStatusEnum value : values) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.ihomefnt.o2o.intf.manager.constant.home;

/**
 * 新增编辑房产结果
 * Author: ZHAO
 * Date: 2018年4月14日
 */
public enum AddCustomerResponseEnum {
	SUCCESS(1, "成功"),
	FAIL(0, "系统异常"),
	PARAM_ERROR(1000, "系统异常"),
    HOUSE_REPEAT(1001, "房产重复"),
    HOUSE_NOT_EXSISTS(1002, "房产不存在"),
    CUSTOMER_NOT_EXSISTS(1003, "客户不存在"),
    ORDER_NOT_EXSISTS(1004, "订单不存在"),
    ORDER_STATUS_LIMIT(1005, "签约后房产信息暂不支持修改，如果疑问请联系客服。"),
    HOUSE_HAS_ORDER_DOING(-1011,"当前房产关联未完结订单，不能再次创建"),
    HOUSE_PROPERTY_BASE_CREATE_ERROR(-1001,"客户房产基本信息创建失败"),
    HOUSE_PROPERTY_EXT_INFO_CREATE_ERROR(-1002,"客户房产扩展信息创建失败"),
    MASTER_ORDER_CREATE_ERROR(-1003,"全品家订单创建失败"),
    HOUSE_ALREADY_EXIST(-1014,"您已存在房产，请到我的房产中查看，不要重复创建哦"),
    DEFAULT(-1,"失败")
    ;
    
    private Integer status;
    
    private String description;
    
    private AddCustomerResponseEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
    
    public static String getDescription(Integer status){
        if (null == status) {
            return DEFAULT.getDescription();
        }
        AddCustomerResponseEnum[] values = values();
        for (AddCustomerResponseEnum value : values) {
            if (value.getStatus().equals(status)) {
                return value.getDescription();
            }
        }
        return DEFAULT.getDescription();
    }
    
    public static AddCustomerResponseEnum getEnumByStatus(Integer status){
        AddCustomerResponseEnum[] values = values();
        for (AddCustomerResponseEnum value : values) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return DEFAULT;
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

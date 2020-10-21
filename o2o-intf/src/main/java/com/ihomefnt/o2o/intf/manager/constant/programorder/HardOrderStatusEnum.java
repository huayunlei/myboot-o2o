package com.ihomefnt.o2o.intf.manager.constant.programorder;

public enum HardOrderStatusEnum {
	
	HARD_ORDER_PRE_PAY(0, "待交付"),
	HARD_ORDER_PRE_DISTRIBUTION(1, "待分配"),
	HARD_ORDER_PRE_SCHEDULE(2, "待排期"),
	HARD_ORDER_PRE_CONSTRUCTION(3, "待施工"),
	HARD_ORDER_CONSTRUCTIONING(4, "施工中"),
	HARD_ORDER_COMPLETED(5, "已完成"),
	HARD_ORDER_CANCLE(6, "已取消");
    
	private Integer status;

	private String description;

	private HardOrderStatusEnum(Integer status, String description) {
		this.status = status;
		this.description = description;
	}

	public static String getMsg(int code) {
		for (HardOrderStatusEnum c : HardOrderStatusEnum.values()) {
			if (c.getStatus() == code) {
				return c.description;
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

package com.ihomefnt.o2o.intf.manager.constant.programorder;

public enum SoftOrderStatusEnum {
	
	SOFT_ORDER_PRE_PAY(0, "待交付"),
	SOFT_ORDER_PRE_PUARCHASE(1, "待采购"),
	SOFT_ORDER_PURCHASEING(2, "采购中"),
	SOFT_ORDER_PRE_DELIVERY(3, "待配送"),
	SOFT_ORDER_DELIVERYING(4, "配送中"),
	SOFT_ORDER_COMPLETED(5, "已完成"),
	SOFT_ORDER_CANCLE(6, "已取消");
    
	private Integer status;

	private String description;

	private SoftOrderStatusEnum(Integer status, String description) {
		this.status = status;
		this.description = description;
	}

	public static String getMsg(int code) {
		for (SoftOrderStatusEnum c : SoftOrderStatusEnum.values()) {
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

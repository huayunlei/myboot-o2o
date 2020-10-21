package com.ihomefnt.o2o.intf.manager.constant.programorder;



/**
 * 需求确认响应枚举值
 *
 */
public enum RequireConfirmResponseEnum {
	SUCCESS(1,"成功"),
	FAIL(0,"失败"),
    ORDER_EMPTY(1000, "订单不存在，请联系客服后再操作"),
    ORDER_WRONGFUL(1001, "订单数据异常，请联系客服后再操作"),
    CONFIRM_FAIL(1002,"确认开工失败，请联系客服后再操作"),
	NO_PERCONFRIMED(1003,"未确认方案，请联系客服后再操作"),
    ;

	private Integer code;

	private String description;

	private RequireConfirmResponseEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public static String getDescription(Integer code) {
		if (null == code) {
			return null;
		}
		for (RequireConfirmResponseEnum value : RequireConfirmResponseEnum.values()) {
			if (value.getCode().equals(code)) {
				return value.getDescription();
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

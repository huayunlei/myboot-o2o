package com.ihomefnt.o2o.intf.domain.programorder.dto;

/**
 * 特定用户资质判断
 * @author ZHAO
 */
public enum AladdinSpecificConstant {
	SUCCESS(1, "成功"),
	FAIL(2, "系统异常"),
	USER_HAS_NO_HOUSE(3, "用户与房产不匹配"),
	HOUSE_TYPE_NOT_EXIST(4, "户型不存在"),
	ORDER_NOT_EXIST(40, "房产下的订单状态不满足或不存在关联订单不可看"),
	ORDER_EXCEPTION_NO(41, "房产关联订单不存在或正在签约交付中不可看"),
	ORDER_EXCEPTION_MORE(42, "房产关联订单异常存在多个有效订单"),
	HOUSE_NO_NOT_EXIST_ALL(50, "户型存在楼栋号或者方案有一个不满足"),
	HOUSE_NO_NOT_EXIST(5, "户型存在楼栋号不存在且方案数不足不可看"),
	HOUSE_NO_NOT_EXIST_ENOUGH(6, "户型存在楼栋号不存在且方案数满足可看"),
	SOLUTION_NOT_ENOUGH(7, "户型存在楼栋号存在且方案数不满足不可看"),
	PAYED_NOT(8, "户型存在楼栋号存在且方案数满足但未交钱可看"),
	ORDER_AVAILABLE(9, "可下单"),
	ORDER_EXIST_REFUND(10,"您的订单申请取消，不能选择方案，请联系客服");//存在退款
	

	private Integer code;
	private String msg;

	private AladdinSpecificConstant(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getMsg(int code) {
		for (AladdinSpecificConstant c : AladdinSpecificConstant.values()) {
			if (c.getCode() == code) {
				return c.msg;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

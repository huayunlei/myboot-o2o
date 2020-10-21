package com.ihomefnt.o2o.intf.manager.constant.order;

/**
 * @author huayunlei
 * @created 2018年10月31日 下午3:24:21
 * @desc 订单相关错误码枚举
 */
public enum OrderErrorCodeEnum {

	FAIL(2, "系统异常", "系统有点小异常，请稍后再试"),
	USER_HAS_NO_HOUSE(3, "用户与房产不匹配", "房产匹配异常，请联系客服后再操作"),
	HOUSE_TYPE_NOT_EXIST(4, "户型不存在", "户型信息异常，请联系客服后再操作"),
	HOUSE_NO_NOT_EXIST(5, "户型存在楼栋号不存在且方案数不足不可看", "户型关联楼栋信息异常，请联系客服后再操作"),
	HOUSE_NO_NOT_EXIST_ENOUGH(6, "户型存在楼栋号不存在且方案数满足可看", "楼栋号异常，请联系客服后再操作"),
	SOLUTION_NOT_ENOUGH(7, "户型存在楼栋号存在且方案数不满足不可看", "设计方案异常，请联系客服后再操作"),
	PAYED_NOT(8, "户型存在楼栋号存在且方案数满足但未交钱可看", "交款状态异常，请联系客服后再操作"),
	ORDER_EXIST_REFUND(10, "退款中", "您有款项处于退款中，请联系客服后再操作"),
	CONCURREN_REQUEST(11, "并发提交", "系统正在处理中，请勿重复提交"),
	SKU_ROOM_CLASS_ID_FAIL(12, "空间硬装商品分类校验失败", "方案中硬装数据异常，请联系客服后再操作"),
	BUILDING_ERROR(18,"楼盘数据异常","楼盘数据异常，请联系客服后再操作"),
	PARMER_EMPTY_ERROR(19,"参数为空或者参数格式不正确","参数异常，请联系客服后再操作"),
	ORDER_NOT_EXIST(40, "房产下的订单状态不满足或不存在关联订单不可看", "订单异常，请联系客服后再操作"),
	ORDER_EXCEPTION_NO(41, "房产关联订单不存在或正在签约交付中不可看", "订单状态异常，请联系客服后再操作"),
	ORDER_EXCEPTION_MORE(42, "房产关联订单异常存在多个有效订单", "房产关联订单异常，请联系客服后再操作"),
	HOUSE_NO_NOT_EXIST_ALL(50, "户型存在楼栋号或者方案有一个不满足", "楼栋信息异常，请联系客服后再操作"),


	ORDER_NOTEXITS_ERROR(1000,"订单不存在","订单查询异常，请返回APP首页刷新重试"),
	CREATE_ORDER_FAIL(1001, "仅支持取消方案订单或该订单已被取消", "方案提交异常，请返回APP首页刷新重试"),
	IN_ORDER_CANCEL_FAIL(1002,"订单已采购施工中不能取消方案！","订单状态异常，请返回APP首页刷新重试"),
	UNKNOWN_ERROR(1003,"系统异常","系统有点小异常，请稍后再试"),
	SOLUTION_SNAPSHOT_EMPTY(1004,"已入库的方案快照信息为空","方案提交异常，请稍后重试");
	
	private int code;
	private String errorMsg;
	private String showMsg;
	
	private OrderErrorCodeEnum(int code, String errorMsg, String showMsg) {
		this.code = code;
		this.errorMsg = errorMsg;
		this.showMsg = showMsg;
	}

	public static String getShowMsgByCode(int code) {
		OrderErrorCodeEnum[] values = values();
		for (OrderErrorCodeEnum v : values) {
			if (v.getCode() == code) {
				return v.getShowMsg();
			}
		}
		return null;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getShowMsg() {
		return showMsg;
	}

	public void setShowMsg(String showMsg) {
		this.showMsg = showMsg;
	}
	
}

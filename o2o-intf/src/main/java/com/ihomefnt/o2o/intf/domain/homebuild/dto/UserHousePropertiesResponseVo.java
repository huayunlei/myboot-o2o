package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

/**
 * 用户关联楼盘信息
 * @author ZHAO
 */
@Data
public class UserHousePropertiesResponseVo {
	
	private Integer specific;//是否是特定用户：1、没有资质 2、只能看 3、可以下单
	
	private Integer buildingId;//楼盘项目ID

	private Integer zoneId;//分区ID

	private Integer houseTypeId;//户型ID

	private Integer houseId;//房产ID
	
	private Integer masterOrderId;//全品家大订单ID

	private String msg;//是否有资质原因

	private Integer houseProjectId;

	private Integer userId;
	
	/**
	 * SUCCESS(1, "成功"),<br/>
	 * FAIL(2, "系统异常"),<br/>
	 * USER_HAS_NO_HOUSE(3, "用户与房产不匹配"),<br/>
	 * HOUSE_TYPE_NOT_EXIST(4, "户型不存在"),<br/>
	 * ORDER_NOT_EXIST(40, "房产下的订单状态不满足或不存在关联订单不可看"),<br/>
	 * ORDER_EXCEPTION_NO(41, "房产关联订单不存在或正在签约交付中不可看"),<br/>
	 * ORDER_EXCEPTION_MORE(42, "房产关联订单异常存在多个有效订单"),<br/>
	 * HOUSE_NO_NOT_EXIST_ALL(50, "户型存在楼栋号或者方案有一个不满足"),<br/>
	 * HOUSE_NO_NOT_EXIST(5, "户型存在楼栋号不存在且方案数不足不可看"),<br/>
	 * HOUSE_NO_NOT_EXIST_ENOUGH(6, "户型存在楼栋号不存在且方案数满足可看"),<br/>
	 * SOLUTION_NOT_ENOUGH(7, "户型存在楼栋号存在且方案数不满足不可看"),<br/>
	 * PAYED_NOT(8, "户型存在楼栋号存在且方案数满足但未交钱可看"),<br/>
	 * ORDER_AVAILABLE(9, "可下单"),<br/>
	 */
	private Integer code;//code：9可下单  10存在退款
	
}

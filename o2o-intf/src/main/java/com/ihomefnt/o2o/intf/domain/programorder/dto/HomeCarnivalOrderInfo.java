package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 1219置家狂欢节订单信息
 * @author ZHAO
 */
@Data
public class HomeCarnivalOrderInfo {

	private Integer orderId;//订单id
	
	private Integer orderType; // 订单类型 1软装 2硬装 3全品家 5艺术品
	
	private Integer state;//订单状态
	
	private String stateDesc;
	
	private String buildingAddress;//楼盘地址：XX省XX市楼盘项目名
	
	private String houseArea;// 面积
	
	private String houseName;//户型
	
	private String houseFullName;//户型全称
	
	private String housePattern;//格局
	
	private Integer orderSource;//订单来源：6代客下单
	
	private String adviserMobileNum;//置家顾问电话

	private boolean homeCarnivalFlag;//是否参加1219活动
	
	private String homeCarnivalTime;//1219活动报名时间

	public HomeCarnivalOrderInfo() {
		this.orderId = 0;
		this.orderType = 0;
		this.state = 0;
		this.stateDesc = "";
		this.buildingAddress = "";
		this.houseArea = "";
		this.houseName = "";
		this.houseFullName = "";
		this.housePattern = "";
		this.orderSource = 0;
		this.adviserMobileNum = "";
		this.homeCarnivalFlag = false;
		this.homeCarnivalTime = "";
	}

}

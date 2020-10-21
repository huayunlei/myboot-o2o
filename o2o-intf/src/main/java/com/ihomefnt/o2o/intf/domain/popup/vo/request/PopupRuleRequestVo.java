package com.ihomefnt.o2o.intf.domain.popup.vo.request;

import lombok.Data;

/**
 * @author huayunlei
 * @created 2018年12月6日 上午10:56:39
 * @desc 
 */
@Data
public class PopupRuleRequestVo {

	private Integer category=1;// 弹框类别 1：权益弹框
	
	private Integer loginStatus=0;// 登录状态 0未登录  1登录
	
	private Integer hasOrder;// 是否有订单 0无订单 1有订单
	
	private Integer orderStatus;// 订单状态
	
	private Integer gradeId;// 等级id
	
	private Integer orderType;// 订单类型：0全品家（软+硬） 1全品家（软）
	
	private Integer allMoney;// 订单是否交完全款 0否  1是
	
	private Integer aijiaLoan;// 是否已申请艾佳贷 0否 1是
	
	
	/**
	 * 艾佳贷id
	 */
	private Long loanId;
}

package com.ihomefnt.o2o.intf.domain.popup.dto;

import lombok.Data;

import java.util.Date;
@Data
public class PopupRuleDto {

	private Integer id;// 主键
	
	private Integer category;// 弹框类别 1：权益弹框
	
	private Integer loginStatus;// 登录状态 0未登录  1登录
	
	private Integer hasOrder;// 是否有订单 0无订单 1有订单
	
	private Integer orderStatus;// 订单状态
	
	private Integer gradeId;// 等级id
	
	private Integer orderType;// 订单类型：0全品家（软+硬） 1全品家（软）
	
	private Integer allMoney;// 订单是否交完全款 0否  1是
	
	private Integer aijiaLoan;// 是否已申请艾佳贷 0否 1是
	
	
	
	private Integer popup;// 是否弹框 0不弹框  1弹框
	
	private Integer popupMode;// 弹框模式 1通知 2营销
	
	private Integer frequency;// 弹框次数
	
	private Integer frequencyUnit;// 弹框次数单位 0永久  1日  2月  3年
	
	private String mainTitle;// 主标题
	
	private String mainContent;// 主文案
	
	private String subContent;// 副文案
	
	private Integer style;// 适用样式  1通知  2引导升级  3通知+引导升级
	
	private String remark;// 备注
	
	private Date createTime;
	
	private Integer deleteFlag;
	
	private String replaceParams;// 替换参数值
	
	private Integer guideStyle;// 引导样式 1:引导1 2:引导3 3:引导4 4:引导5
}

package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import lombok.Data;

/**
 * 交付信息
 * Author: ZHAO
 * Date: 2018年7月23日
 */
@Data
public class DeliverInfoVo {
	private Integer deliverId;//交付单号
	
	private Integer orderId;//订单号
	
	private Integer status;//交付状态：0待交付 1待排期 2需求确认 3施工中/采购中 5安装 6竣工 7质保 8完结

	private Integer orderStatus;//交付状态：-1待交付 0待采购1采购中2代送货3送货中4送货完成7以取消
	
	private String statusName;//交付状态
	
	private Integer managerId;//艾管家id
	
	private String managerName;//艾管家名字
	
	private String mobile;//手机号
	
	private Integer principalId;//交付负责人id
	
	private String principalName;//交付负责人名字
	
	private String houseDeliverDate;//交房日期
	
	private String planBeginDate;//计划开工日期
	
	private String actualBeginDate;//实际开工日期
	
	private Integer deliverDay;//交付时长 天
	
	private String softProcess;//软装进度
	
	private String hardProcess;//硬装进度

	private Integer softTotal;//软装数量
	
	private Integer softFinishNum;//软装完成数
	
	private String projectStatus;//硬装施工进度
}

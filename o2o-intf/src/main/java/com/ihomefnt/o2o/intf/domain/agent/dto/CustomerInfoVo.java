package com.ihomefnt.o2o.intf.domain.agent.dto;

import lombok.Data;

import java.util.Date;

/**
 * 客户信息
 * @author ZHAO
 */
@Data
public class CustomerInfoVo {

	private Integer userId;//用户id
	
	private String userName;//用户name
	
	private String mobile;//用户手机
	
	private Integer overdueHours;//过期小时数
	
	private Date registerDate;//注册日期
	
	private Integer dealPhaseCode;//成交阶段码 0-定金阶段 1-签约代收款 2-签约已收款
	
	private String dealPhaseName;//成交阶段name 枚举:定金阶段、签约代收款、签约已收款
	
	private Integer reRecommendTimes;//可重新推荐次数
	
	private Integer buildingId;//所属楼盘ID

	private Integer zoneId;//分区id
	
	private String partitionName;//分区名称
	
	private Integer purchaseIntention;//购买意向星级

	private Integer customerId;//客户ID
}

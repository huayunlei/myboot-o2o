package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AgentAndCustomerDto {

	@ApiModelProperty("经纪人id")
	private Long agentId;

	@ApiModelProperty("经纪人名称")
	private String agentName;

	@ApiModelProperty("经纪人手机号")
	private String agentMobile;

	@ApiModelProperty("经纪人类型 0-专属 1-个人 2-渠道")
	private Integer type;

	@ApiModelProperty("经纪人用户id")
	private Long agentUserId;

	@ApiModelProperty("推荐关系表id")
	private Long id;

	@ApiModelProperty("用户id")
	private Long userId;

	@ApiModelProperty("客户姓名")
	private String customerName;

	@ApiModelProperty("客户手机号")
	private String mobile;

	@ApiModelProperty("项目")
	private Long buildingId;

	@ApiModelProperty("分区")
	private Long zoneId;

	@ApiModelProperty("状态 0-潜在客户 1-机会客户 2-成交客户 3-潜在关系失效 4-已释放客户")
	private Integer status;

	@ApiModelProperty("推荐时间")
	private Date recommendTime;

	@ApiModelProperty("绑定时间")
	private Date bindTime;

	@ApiModelProperty("推荐方式 0-经纪人推荐 1-客户自己推荐")
	private Integer recommendType;

	private Integer createUserId;

	private Date createTime;

	private Integer updateUserId;

	private Date updateTime;

	private Integer delFlag;
}
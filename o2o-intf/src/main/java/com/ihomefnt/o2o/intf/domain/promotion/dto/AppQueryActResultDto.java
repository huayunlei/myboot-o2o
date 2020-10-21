package com.ihomefnt.o2o.intf.domain.promotion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@ApiModel("app查询有效活动返回")
public class AppQueryActResultDto {

	@ApiModelProperty("活动ID")
	private Long actCode;

	@ApiModelProperty("活动名称")
	private String actName;

	@ApiModelProperty(value = "活动APP呈现名称")
	private String appActName;

	@ApiModelProperty(value = "活动图片")
	private List<ActBasicAppPicturesDto> actBasicAppPicturesList;

	@ApiModelProperty(value = "客服呈现文案")
	private String customerServiceText;

	@ApiModelProperty("活动目标 2:贷款回全款,1:定金回款,3:现金回全款,4:选方案下单,5:确认方案订单 ")
	private Integer actType;

	@ApiModelProperty("赠品方式：0到赠品描述1:到赠品SPU")
	private Integer presentType;

	@ApiModelProperty("活动类型描述")
	private String actTypeDesc;

	@ApiModelProperty("活动状态 1:新建2:上线3:暂停 4:结束")
	private Integer actStatus;

	@ApiModelProperty("活动订单记录状态 1:已参加 2:已退出")
	private Integer actOrderRecordStatus;

	@ApiModelProperty("活动状态描述")
	private String actStatusDesc;

	@ApiModelProperty("活动文本描述")
	private String actDesc;

	@ApiModelProperty("奖励规则描述")
	private String rewardRuleDesc;

	@ApiModelProperty("活动开始时间")
	private Date startTime;

	@ApiModelProperty("活动结束时间")
	private Date endTime;

	@ApiModelProperty("优惠金额")
	private BigDecimal promotionAmount;

	@ApiModelProperty("赠品列表")
	private List<Integer> presentList;

	@ApiModelProperty("赠品描述")
	private String presentDesc;

	@ApiModelProperty("与本活动互斥的活动ID")
	private List<Long> mutexPromotions;

	@ApiModelProperty("是否成功参加:0:否，1:是")
	private Integer isSuccessJoin = 0;

	@ApiModelProperty("不可参加原因code")
	private Integer canNotJoinCode;

	@ApiModelProperty("不可参加原因描述")
	private String canNotJoinDesc;

	@ApiModelProperty("差距值")
	private BigDecimal distance;

	@ApiModelProperty("参与奖励计算时订单基数金额")
	private BigDecimal orderBaseAmount;

	@ApiModelProperty("活动优惠明细")
	private PromotionRewardDetailDto rewardDetail;

	@ApiModelProperty("不可参加的活动是否可见 0=可见 1=不可见")
	private Integer isShow = 0;

}

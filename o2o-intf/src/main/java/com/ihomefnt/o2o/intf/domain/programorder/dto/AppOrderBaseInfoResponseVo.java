package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("订单信息")
public class AppOrderBaseInfoResponseVo {

	@ApiModelProperty("订单号")
	private Integer orderNum;

	@ApiModelProperty("这个字段订单取值错了，取了orderType，枚举不是订单类型 0:方案下单，6:代客下单")
	private Integer source;

	@ApiModelProperty("订单类型 0:方案下单，6:代客下单")
	private Integer sourceType;

	@ApiModelProperty("beta订单状态，app前端慎用")
	private Integer orderStatus;

	@ApiModelProperty("订单阶段-子状态(161,未签约),(162,签约中),(163,签约成功)")
	private Integer orderSubstatus;

	@ApiModelProperty("已付金额")
	private BigDecimal fundAmount;

	@ApiModelProperty("款项类型")
	private Integer paymentType;

	@ApiModelProperty("合同价")
	private BigDecimal contractAmount;

	@ApiModelProperty("收款进度")
	private BigDecimal fundProcess;

	@ApiModelProperty("方案头图")
	private String solutionUrl;

	@ApiModelProperty("创建时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty("更新时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@ApiModelProperty("已选方案风格名称")
	private String solutionStyleName;

	@ApiModelProperty("已选方案风格id")
	private Integer solutionStyleId;

	@ApiModelProperty("置家顾问id")
	private Integer adviser;

	@ApiModelProperty("置家顾问")
	private String adviserName;

	@ApiModelProperty("置家顾问电话")
	private String adviserPhone;

	@ApiModelProperty("距离交房还差多少天")
	private Integer deliverDiff;

	@ApiModelProperty("是否是老订单(0. 老订单 1. 新订单)")
	private Integer oldOrder;

	@ApiModelProperty("售卖类型：0：全品家（软+硬） 1：全品家（软）")
	private Integer orderSaleType;

	@ApiModelProperty("楼盘id")
	private Integer buildingId;

	@ApiModelProperty("户型id")
	private Integer layoutId;

	@ApiModelProperty("权益等级")
	private Integer gradeId;

	@ApiModelProperty("权益等级名称")
	private String gradeName;

	@ApiModelProperty("权益版本号")//2020版本权益为4
	private Integer rightsVersion = 2;

	@ApiModelProperty("用户是否已预确认方案，0:未预确认  1：已确认")
	private Integer preConfirmed;

	@ApiModelProperty("用户享受权益并缴满全款，0: 不满足  1: 满足")
	private Integer allMoney;

	@ApiModelProperty("软装商品总数")
	private Integer totalProductCount;

	@ApiModelProperty("送货完成数")
	private Integer completeDelivery;

	@ApiModelProperty("艾升级券面值")
	private BigDecimal upGradeCouponAmount;

	@ApiModelProperty("订单原始金额（方案总价）")
	private BigDecimal originalOrderAmount;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private BigDecimal orderTotalAmount;

	@ApiModelProperty("方案总价")
	private BigDecimal solutionAmount;

	@ApiModelProperty("定价优惠")
	private BigDecimal priceDisAmount;

	@ApiModelProperty("艾升级优惠")
	private BigDecimal upgradeDisAmount;

	@ApiModelProperty("促销优惠")
	private BigDecimal promotionDisAmount;

	@ApiModelProperty("艾升级权益可抵扣金额")
	private BigDecimal upItemDeAmount;

	@ApiModelProperty("房产id")
	private Integer customerHouseId;

	@ApiModelProperty("-1:失效（老订单） 0：锁价中 1：最终锁价")
	private Integer lockPriceFlag;

	@ApiModelProperty("锁价倒计时")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date lockPriceExpireTime;

}

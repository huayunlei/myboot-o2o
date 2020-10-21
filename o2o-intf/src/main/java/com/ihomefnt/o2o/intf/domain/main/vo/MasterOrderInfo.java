package com.ihomefnt.o2o.intf.domain.main.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */
@ApiModel("订单信息")
@Data
@Accessors(chain = true)
public class MasterOrderInfo {

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("未付金额")
    private BigDecimal unpaidMoney;

    @ApiModelProperty("已付金额")
    private BigDecimal paidMoney;

    @ApiModelProperty("是否是老订单")
    private Boolean oldFlag;

    @ApiModelProperty("权益等级id")
    private Integer gradeId;

    @ApiModelProperty("权益等级名称")
    private String gradeName;

    @ApiModelProperty("方案总价")
    private BigDecimal solutionTotalPrice;

    @ApiModelProperty("艾升级优惠")
    private BigDecimal upgradeDisAmount;

    @ApiModelProperty("艾升级券面值")
    private BigDecimal upGradeCouponAmount;

    @ApiModelProperty("置家顾问电话")
    private String homeAdviserMobile;

    @ApiModelProperty("可选方案数量")
    private Integer availableSolutionCount;

    @ApiModelProperty("楼盘Id")
    private Integer houseProjectId;

    @ApiModelProperty("户型Id")
    private Integer houseTypeId;

    @ApiModelProperty("方案类型:0软+硬，1纯软")
    private Integer solutionType;

    @ApiModelProperty("艾升级权益可抵扣金额,即APP选配项金额，仅在确认方案前使用")
    private BigDecimal upItemDeAmount;

    @ApiModelProperty("-1:失效（老订单） 0：锁价中 1：最终锁价")
    private Integer lockPriceFlag;

    @ApiModelProperty("锁价倒计时")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date lockPriceExpireTime;

    @ApiModelProperty("锁价倒计时 单位秒")
    private Integer priceLockCount;

    @ApiModelProperty("是否交清全款 0否 1是 2超过应付金额")
    private Integer allMoney = 0;

    @ApiModelProperty("订单子状态")
    private Integer orderSubStatus;

    @ApiModelProperty("权益版本号")//2020版本权益为4
    private Integer rightsVersion = 2;

    private Boolean isCustomRightVersion = Boolean.FALSE;

}

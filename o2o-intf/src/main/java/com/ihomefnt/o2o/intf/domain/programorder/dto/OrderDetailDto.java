package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by acer on 2018/9/8.
 */
@Data
public class OrderDetailDto {

    @ApiModelProperty("订单号")
    private Long orderNum;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

//    /**签约状态子状态**/
//    (161, "未签约"),
//    (162, "签约中"),
//    (163, "签约成功"),
    @ApiModelProperty("订单子状态")
    private Integer orderSubStatus;

    @ApiModelProperty("项目id")
    private Long buildingId;

    @ApiModelProperty("项目名称")
    private String buildingName;

    @ApiModelProperty("分区id")
    private Long zoneId;

    @ApiModelProperty("分区名称")
    private String zoneName;

    @ApiModelProperty("等级id")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;

    @ApiModelProperty("合同额")
    private BigDecimal contractAmount;

    @ApiModelProperty("下单方式 0：方案套餐 6：代客下单")
    private Integer source;

    @ApiModelProperty("楼栋号")
    private String housingNum;

    @ApiModelProperty("单元号")
    private String unitNum;

    @ApiModelProperty("房号")
    private String roomNum;

    @ApiModelProperty("置家顾问id")
    private Long adviser;

    @ApiModelProperty("置家顾问姓名")
    private String adviserName;

    @ApiModelProperty("置家顾问手机号")
    private String adviserMobile;

    @ApiModelProperty("已收金额")
    private BigDecimal fundAmount;

    @ApiModelProperty("艾升级权益可抵扣金额")
    private BigDecimal upItemDeAmount;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("原合同额")
    private BigDecimal originalOrderAmount;

    @ApiModelProperty("方案是否已确认 0-否 1-是")
    private Integer preConfirmed;

    @ApiModelProperty("升级项金额")
    private BigDecimal upItemAmount;

    @ApiModelProperty("手输房产信息")
    private String buildingInfo;

    @ApiModelProperty("艾升级券面值")
    private BigDecimal upGradeCouponAmount;

    @ApiModelProperty("全品家立减金额")
    private BigDecimal rightsDiscountAmount;

    @ApiModelProperty("已确认收款金额")
    private BigDecimal confirmedAmount;

    @ApiModelProperty("订单总价")
    private BigDecimal orderTotalAmount;

    @ApiModelProperty("其他优惠")
    private BigDecimal otherDisAmount;

    @ApiModelProperty("方案总价")
    private BigDecimal solutionAmount;

    @ApiModelProperty("定价优惠")
    private BigDecimal priceDisAmount;

    @ApiModelProperty("艾升级优惠")
    private BigDecimal upgradeDisAmount;

    @ApiModelProperty("促销优惠")
    private BigDecimal promotionDisAmount;

    @ApiModelProperty("剩余应付")
    private BigDecimal surplusPayAmount;

    @ApiModelProperty("其他优惠金额")
    private BigDecimal newOtherDisAmount;

    @ApiModelProperty("方案总价")
    private BigDecimal newSolutionAmount;

    @ApiModelProperty("保价优惠金额")
    private BigDecimal preferentialAmount;
}

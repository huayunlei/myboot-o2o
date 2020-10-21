package com.ihomefnt.o2o.intf.domain.order.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("根据订单编号批量查询简单信息（订单信息，房产信息）")
public class OrderSimpleInfoResponseVo {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("客户编号")
    private Integer customerId;

    @ApiModelProperty("客户姓名")
    private String customerName;

    @ApiModelProperty("置家顾问id")
    private Integer adviser;

    @ApiModelProperty("置家顾问姓名")
    private String adviserName;

    @ApiModelProperty("客户手机号")
    private String mobile;

    @ApiModelProperty("样板间订单id")
    private Integer orderId;

    @ApiModelProperty("订单编号")
    private String orderNum;

    @ApiModelProperty("售后单号")
    private String afterSaleOrderNum;

    @ApiModelProperty("售卖类型")
    private Integer orderSaleType;

    @ApiModelProperty("订单类型")
    private Integer orderType;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("已收金额 已确认收-已确认退-艾积分不足抵扣")
    private BigDecimal confirmedAmount;

    @ApiModelProperty("合同金额")
    private BigDecimal contractAmount;

    @ApiModelProperty("收款进度")
    private BigDecimal fundProcess;

    @ApiModelProperty("已确认收金额")
    private BigDecimal fundedAmount;

    @ApiModelProperty("已确认退金额")
    private BigDecimal refundedAmount;

    @ApiModelProperty("艾积分不足抵扣金额")
    private BigDecimal totalDeductionAmount;

    @ApiModelProperty("合同主体关系")
    private Integer contractRelationship;

    @ApiModelProperty("合同主体关系字符串")
    private String contractRelationshipStr;

    @ApiModelProperty("删除标识")
    private Integer delFlag;

    @ApiModelProperty("交房时间")
    private String deliverTime;

    @ApiModelProperty("交房时间字符串")
    private String deliverTimeStr;

    @ApiModelProperty("期望交付日期")
    private String expectTime;

    @ApiModelProperty("期望交付日期")
    private String expectTimeStr;

    @ApiModelProperty("曾经完成过签约 true 是 false否")
    private Boolean everFinishedSign;

    @ApiModelProperty("等级")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;

    @ApiModelProperty("下单方式")
    private Integer orderSource;

    @ApiModelProperty("创建订单来源标识:1-beta 2-app 3-嵩云pos 4-银联pos 21-app-散单客户 41-银联pos云客")
    private Integer originalSource;

    @ApiModelProperty("下单方式字符串")
    private String orderSourceStr;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单状态字符串")
    private String orderStatusStr;

    @ApiModelProperty("佣金侧需要订单子状态")
    private Integer orderSubStatus;

    @ApiModelProperty("户型名称")
    private String apartmentName;

    @ApiModelProperty("面积")
    private BigDecimal area;

    @ApiModelProperty("楼盘id")
    private Integer buildingId;

    @ApiModelProperty("楼盘名称")
    private String buildingName;

    @ApiModelProperty("楼盘类型BBC，B2C，B2B，C2C")
    private String buildingType;

    @ApiModelProperty("房产id")
    private Integer houseId;

    @ApiModelProperty("楼栋号")
    private String housingNum;

    @ApiModelProperty("户型id")
    private Integer layoutId;

    @ApiModelProperty("分区名称")
    private String partitionName;

    @ApiModelProperty("客户地址")
    private String receiverAddressDetail;

    @ApiModelProperty("区域id")
    private Integer receiverAreaId;

    @ApiModelProperty("单元号")
    private String unitNum;

    @ApiModelProperty("房号")
    private String roomNum;

    @ApiModelProperty("分区id")
    private Integer zoneId;

}

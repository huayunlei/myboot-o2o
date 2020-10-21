package com.ihomefnt.o2o.intf.domain.programorder.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/9/8
 */

@ApiModel("全品家订单收银台支付信息")
@Data
public class FamilyOrderPayResponse {

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单子状态 当前仅在签约阶段使用：161,未签约;162,签约中;163,签约成功")
    private Integer orderSubStatus;

    @ApiModelProperty("房产信息拼接字段")
    private String houseInfo;

    @ApiModelProperty("权益等级id")
    private Integer gradeId;

    @ApiModelProperty("权益等级名称")
    private String gradeName;

    @ApiModelProperty("合同额")
    private BigDecimal contractAmount;

    @ApiModelProperty("已付金额")
    private BigDecimal paidAmount;

    @ApiModelProperty("权益抵扣金额")
    private BigDecimal rightAmount;

    @ApiModelProperty("剩余应付")
    private BigDecimal restAmount;

    @ApiModelProperty("下单方式")
    private Integer orderSource;//0方案下单 6代客下单

    @ApiModelProperty("置家顾问姓名")
    private String adviserName;

    @ApiModelProperty("置家顾问手机号")
    private String adviserMobile;

    @ApiModelProperty("最小定金金额限制")
    private BigDecimal minDeposit;

    @ApiModelProperty("方案总价")
    private BigDecimal solutionTotalPrice;

    @ApiModelProperty("升级项金额")
    private BigDecimal upItemAmount;
    
    @ApiModelProperty("艾升级券面值")
    private BigDecimal upGradeCouponAmount;

    @ApiModelProperty("全品家立减金额")
    private BigDecimal rightsDiscountAmount;

    @ApiModelProperty("已确认收款金额")
    private BigDecimal confirmReapAmount;

    @ApiModelProperty("订单总价信息")
    private CopyWriterAndValue<String, BigDecimal> finalOrderPrice;

    @ApiModelProperty("其他优惠")
    private BigDecimal otherDisAmount;

    @ApiModelProperty("款项优化后的剩余应付")
    private BigDecimal newRestAmount;

    @ApiModelProperty("方案总价")
    private BigDecimal solutionAmount;

    @ApiModelProperty("可贷款金额")
    private BigDecimal canLoanAmount;

    @ApiModelProperty("保价优惠金额")
    private BigDecimal preferentialAmount;

    @ApiModelProperty("是否交清全款 0否 1是 2超过应付金额")
    private Integer allMoney = 0;

}

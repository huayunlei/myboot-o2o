package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lindan on 2017/8/4.
 */
@Data
@ApiModel(description = "代客下单详情（订单页展示）")
public class ValetOrderResultDto {

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单状态名称")
    private String orderStatusStr;

    @ApiModelProperty(value = "订单类型，15.代客下单")
    private Integer orderType;

    @ApiModelProperty(value = "订单类型名称")
    private String orderTypeStr;

    @ApiModelProperty(value = "合同金额")
    private BigDecimal contractAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "软装增减项成交金额")
    private BigDecimal softDealAmount;

    @ApiModelProperty(value = "硬装增减项成交金额")
    private BigDecimal hardDealAmount;

    @ApiModelProperty(value = "软装成交金额")
    private BigDecimal softOrderAmount;

    @ApiModelProperty(value = "软装实际金额")
    private BigDecimal softOrderActualAmount;

    @ApiModelProperty(value = "硬装成交金额")
    private BigDecimal hardOrderAmount;

    @ApiModelProperty(value = "下单时间")
    private Date orderTime;

    @ApiModelProperty(value = "下单时间字符串")
    private String orderTimeStr;

    @ApiModelProperty(value = "期望收货时间")
    private Date expectTime;

    @ApiModelProperty(value = "期望收货时间字符串")
    private String expectTimeStr;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "商品总数量")
    private Integer productCount;

    @ApiModelProperty(value = "软装增项金额")
    private BigDecimal softAddAmount;

    @ApiModelProperty(value = "软装减项金额")
    private BigDecimal softReduceAmount;

    @ApiModelProperty(value = "硬装增项金额")
    private BigDecimal hardAddAmount;

    @ApiModelProperty(value = "硬装减项金额")
    private BigDecimal hardReduceAmount;

    @ApiModelProperty(value = "软装商品集合")
    private List<SoftOrderProductResultDto> softOrderInfoDto;

    @ApiModelProperty(value = "软装增项商品集合")
    private List<SoftOrderProductResultDto> softIncreaseInfoDto;

//    @ApiModelProperty(value = "硬装增减项详细信息")
//    private Map<String, List<IncrementItemHardDetail>> hardIncrementDetailMap;

    @ApiModelProperty(value = "软装金额（优惠前）")
    private BigDecimal softTotalAmount;

    @ApiModelProperty(value = "硬装金额（优惠前）")
    private BigDecimal hardTotalAmount;

    @ApiModelProperty(value = "软装金额（优惠后）")
    private BigDecimal softAmount;

    @ApiModelProperty(value = "硬装金额（优惠后）")
    private BigDecimal hardAmount;

    @ApiModelProperty(value = "删除线金额")
    private BigDecimal strictOutAmount;

//    @ApiModelProperty(value = "商品状态汇总")
//    private ProductStatusCountRsultDto productDto;

    @ApiModelProperty(value = "售卖类型")
    private String orderSaleTypeStr;

    @ApiModelProperty(value = "售卖类型")
    private Integer orderSaleType;

//    @ApiModelProperty(value = "硬装进展信息")
//    private ProjectDetailProgressInfoVo projectDetailProgressInfoVo;

    @ApiModelProperty(value = "硬装增项成本金额")
    private BigDecimal hardIncreaseCostAmount;

    @ApiModelProperty(value = "硬装减项成本金额")
    private BigDecimal hardDecreaseCostAmount;

    @ApiModelProperty(value = "整单优惠")
    private BigDecimal orderAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "立减优惠")
    private BigDecimal subtractAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "下单人")
    private String orderOperator;

//    @ApiModelProperty("软装商品进度节点")
//    private List<SoftProductProcessInfoVo> softProcessNodeList;

    @ApiModelProperty("商品总数")
    private int totalProductCount;

    @ApiModelProperty("商品总价")
    private BigDecimal totalProAmount;

    @ApiModelProperty("工期延期天数")
    private Integer delayDays;

    @ApiModelProperty("等级")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;

    @ApiModelProperty("是否存在订单变更审核记录 true=是 false=否")
    private Boolean orderChangeAuditing;
}

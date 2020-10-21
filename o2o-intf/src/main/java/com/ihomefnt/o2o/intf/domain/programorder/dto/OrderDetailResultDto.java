package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author hongjingchao
 * 2017年6月15日
 */
@Data
@ApiModel(description = "全品家订单详情")
public class OrderDetailResultDto {

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单状态名称")
    private String orderStatusStr;

    @ApiModelProperty(value = "订单类型，取值：13-全品家订单")
    private Integer orderType;

    @ApiModelProperty(value = "订单类型名称")
    private String orderTypeStr;

    @ApiModelProperty(value = "订单类型，取值：0：软装+硬装，1：软装")
    private Integer orderSaleType;

    @ApiModelProperty(value = "订单类型名称")
    private String orderSaleTypeStr;

    @ApiModelProperty(value = "合同金额")
    private BigDecimal contractAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "软装金额")
    private BigDecimal softOrderAmount;

    @ApiModelProperty(value = "硬装金额")
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

    @ApiModelProperty(value = "软装详细信息")
    private OrderSoftDetailResultDto orderSoftDetailResultDto;

    @ApiModelProperty(value = "订单来源")
    private Integer source;

    @ApiModelProperty(value = "订单来源字符串")
    private String SourceStr;

    @ApiModelProperty("订单等级")
    private Integer gradeId;

    @ApiModelProperty("订单等级名称")
    private String gradeName;

    @ApiModelProperty("确认方案  0：未预确认 1：已预确认")
    private Integer preConfirmed;

    @ApiModelProperty("方案id")
    private Long solutionId;

    @ApiModelProperty("是否存在订单变更审核记录 true=是 false=否")
    private Boolean orderChangeAuditing;

	@ApiModelProperty("软装数量")
	private Integer softTotal;

	@ApiModelProperty("软装完成数")
	private Integer softFinishNum;

    
}

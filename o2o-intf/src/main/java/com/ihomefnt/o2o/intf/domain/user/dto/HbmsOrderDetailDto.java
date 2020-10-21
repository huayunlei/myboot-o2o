package com.ihomefnt.o2o.intf.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 大订单详情
 *
 * @author liyonggang
 * @create 2019-02-22 19:24
 */
@Data
public class HbmsOrderDetailDto {

    @ApiModelProperty("订单编号")
    private Integer orderNum;// 订单编号,

    @ApiModelProperty("合同金额")
    private BigDecimal contractAmount;//合同金额,

    @ApiModelProperty("已确认金额")
    private BigDecimal confirmedAmount;// 已确认金额,

    @ApiModelProperty("已确认退款金额")
    private BigDecimal refundedAmount;//已确认退款金额,

    @ApiModelProperty("收款进度")
    private BigDecimal fundProcess;//收款进度,

    @ApiModelProperty("订单等级")
    private Integer gradeId; //订单等级,

    @ApiModelProperty("订单等级图片")
    private String iconUrl;// 订单等级图片
}

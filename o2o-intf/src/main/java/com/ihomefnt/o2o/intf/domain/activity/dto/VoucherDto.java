package com.ihomefnt.o2o.intf.domain.activity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/11/26
 */
@Data
@Accessors(chain = true)
@ApiModel("优惠券信息")
public class VoucherDto {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("优惠券券码")
    private String voucherCode;

    @ApiModelProperty("所属活动id")
    private Integer activityId;

    @ApiModelProperty("持有者手机号")
    private String mobile;

    @ApiModelProperty("券类型 1-现金优惠")
    private Integer voucherType;

    @ApiModelProperty("券抵用金额")
    private BigDecimal voucherMoney;

    @ApiModelProperty("领取邀请码")
    private String inviteCode;

    @ApiModelProperty("券状态：0-可使用，1-已使用，-1-已失效")
    private Integer voucherStatus;

    @ApiModelProperty("订单号")
    private Integer orderId;
}

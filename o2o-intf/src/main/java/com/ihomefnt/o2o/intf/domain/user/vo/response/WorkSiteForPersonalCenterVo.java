package com.ihomefnt.o2o.intf.domain.user.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 我的工地交付信息
 *
 * @author liyonggang
 * @create 2019-04-10 11:37
 */
@Data
@ApiModel("我的工地交付信息")
public class WorkSiteForPersonalCenterVo {

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单来源")
    private Integer orderSource;

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("交付单状态    0-待交付 1-需求确认 2-交付准备 3交付中 6竣工 7质保 8已完成")
    private Integer deliverStatus;

    @ApiModelProperty("售卖类型：0：全品家（软+硬） 1：全品家（软）")
    private Integer orderSaleType;
}

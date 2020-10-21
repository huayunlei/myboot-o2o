package com.ihomefnt.o2o.intf.domain.hbms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/9/27
 */

@Data
@ApiModel("待确认的工期变更项")
public class NeedConfirmItemsDto {

    @ApiModelProperty("项目id")
    private Integer id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("标准工期")
    private Integer standardDays;

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("艾管家手机号")
    private String phone;
}

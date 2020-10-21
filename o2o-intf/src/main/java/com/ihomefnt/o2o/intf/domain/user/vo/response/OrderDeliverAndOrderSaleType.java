package com.ihomefnt.o2o.intf.domain.user.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @auor liyonggang
 * @create 2019-05-13 16:12
 */
@Data
public class OrderDeliverAndOrderSaleType {
    @ApiModelProperty("是否已开工")
    private Boolean startWork = Boolean.FALSE;

    @ApiModelProperty("售卖类型：0：全品家（软+硬） 1：全品家（软）")
    private Integer orderSaleType;
}

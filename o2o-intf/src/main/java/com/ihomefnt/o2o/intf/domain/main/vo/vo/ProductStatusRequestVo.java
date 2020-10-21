package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品状态查询参数")
public class ProductStatusRequestVo {
    @ApiModelProperty("订单编号")
    private Integer orderNum;
}

package com.ihomefnt.o2o.intf.domain.order.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OrderSimpleInfoRequestVo {

    @ApiModelProperty("订单编号集合")
    private List<Integer> orderNums;

    @ApiModelProperty("查询系统来源 1:BOSS 2:APP 3:财务")
    private Integer source = 2;
}

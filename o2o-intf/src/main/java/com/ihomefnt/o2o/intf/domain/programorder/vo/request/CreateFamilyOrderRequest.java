package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 创建订单请求输入
 * 
 * @author ibm
 */
@ApiModel("创建大订单入参")
@Data
@Accessors(chain = true)
public class CreateFamilyOrderRequest extends QueryFamilyOrderPriceRequest {

    @ApiModelProperty(value = "方案id")
    private List<Long> solutionIds;

    @ApiModelProperty("操作类型 1-下单 2-查询订单价格")
    private Integer opType;

}

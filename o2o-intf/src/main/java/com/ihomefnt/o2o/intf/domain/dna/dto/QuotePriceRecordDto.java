package com.ihomefnt.o2o.intf.domain.dna.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-11-18 14:50
 */
@Data
@ApiModel("获取装修报价记录")
public class QuotePriceRecordDto {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("获取装修报价时订单状态")
    private Integer orderStatus;

    @ApiModelProperty("报价结果数据")
    private String quotePriceResult;
}

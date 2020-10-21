package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author jerfan cang
 * @date 2018/9/27 13:31
 */
@Data
@ApiModel("订单单个权益确权详情")
public class OrderSingleClassifyDto {

    @ApiModelProperty("权益分类基本信息")
    private RightsClassifyDto classifyDto;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("订单编号")
    private Integer orderNum;


    @ApiModelProperty("权益项列表")
    List<RightItemDto> itemDetailList;

}

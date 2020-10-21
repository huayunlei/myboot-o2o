package com.ihomefnt.o2o.intf.domain.paintscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 推屏返回数据
 */
@ApiModel("推屏返回新数据")
@Data
public class PushArtResultDto {

    @ApiModelProperty("推屏返回消息ID")
    private Long requestId;

    @ApiModelProperty("推屏返回结果")
    private Integer pushResult = 1;

}

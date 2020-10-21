package com.ihomefnt.o2o.intf.domain.flowmsg.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/19 4:30 下午
 */
@Data
@Accessors(chain = true)
public class DeleteOfflineDrawMessageDto {

    @ApiModelProperty("草稿编号")
    private Long draftProfileNum;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("订单id")
    private Long orderId;
}

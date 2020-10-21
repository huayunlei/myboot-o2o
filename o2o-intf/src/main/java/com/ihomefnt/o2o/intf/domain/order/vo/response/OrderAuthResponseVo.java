package com.ihomefnt.o2o.intf.domain.order.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author hua
 * @Date 2020/1/13 10:21 上午
 */
@Data
@Builder
@Accessors(chain = true)
@ApiModel("OrderAuthResponseVo")
public class OrderAuthResponseVo {

    @ApiModelProperty("鉴权结果")
    private boolean result;

}

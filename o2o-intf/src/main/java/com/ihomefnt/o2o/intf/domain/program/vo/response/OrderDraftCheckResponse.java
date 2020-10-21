package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanyunxin
 * @create 2019-03-24 22:36
 */
@ApiModel("订单与草稿校验返回结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDraftCheckResponse {

    @ApiModelProperty("校验结果")
    private Boolean result;
}

package com.ihomefnt.o2o.intf.domain.hbms.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/9/26
 */
@Data
@ApiModel("硬装工期变更请求参数")
public class TimeChangeParamDto extends HttpBaseRequest{

    @ApiModelProperty("订单号")
    private Integer orderId;
}

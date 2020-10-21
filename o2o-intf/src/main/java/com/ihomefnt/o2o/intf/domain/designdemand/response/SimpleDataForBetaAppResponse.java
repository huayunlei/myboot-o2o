package com.ihomefnt.o2o.intf.domain.designdemand.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-11-05 16:16
 */
@Data
@ApiModel("订单设计任务和方案意见简单信息")
public class SimpleDataForBetaAppResponse {

    ReviseOpinionDtoSimpleInfo reviseOpinionDtoSimpleInfo;

    DesignDemandSimpleInfo designDemandSimpleInfo;

    @ApiModelProperty("订单用户id")
    private Integer userId;
}

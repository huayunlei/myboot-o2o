package com.ihomefnt.o2o.intf.domain.designdemand.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-11-05 16:14
 */
@Data
@ApiModel("方案意见简单信息")
public class ReviseOpinionDtoSimpleInfo {

    @ApiModelProperty("修改意见草稿ID")
    private String programOpinionId;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("方案号")
    private Integer solutionId;
}

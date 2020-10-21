package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电子合同
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@ApiModel("合同信息")
@Data
public class ContractInfoResponse {

    @ApiModelProperty("订单编号")
    private Integer orderId;//订单编号

    @ApiModelProperty("文件路径")
    private String filePath;//文件路径

    @ApiModelProperty("合同类型")
    private Integer type;//合同类型

    @ApiModelProperty("合同类型名称")
    private String typeName;//合同类型名称

    @ApiModelProperty("创建时间")
    private String createTime;//创建时间
}

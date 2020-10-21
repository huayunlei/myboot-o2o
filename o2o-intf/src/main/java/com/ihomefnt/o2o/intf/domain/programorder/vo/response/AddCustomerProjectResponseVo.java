package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianxingxing
 */
@Data
@ApiModel("APP新增C端项目户型返回参数")
public class AddCustomerProjectResponseVo {

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("户型id")
    private Long apartmentId;

    @ApiModelProperty("户型名称")
    private String apartmentName;
}

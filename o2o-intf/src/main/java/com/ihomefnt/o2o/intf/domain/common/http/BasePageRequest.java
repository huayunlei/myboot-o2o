package com.ihomefnt.o2o.intf.domain.common.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2019-12-11 14:44
 */
@Data
@ApiModel("分页查询请求参数")
public class BasePageRequest {
    @ApiModelProperty(value = "页码")
    private int pageNo = 0;

    @ApiModelProperty(value = "每页记录数")
    private int pageSize = 30;
}

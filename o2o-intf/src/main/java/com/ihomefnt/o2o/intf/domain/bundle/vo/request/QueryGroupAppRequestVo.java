package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2019-12-12 14:19
 */
@Data
public class QueryGroupAppRequestVo {

    @ApiModelProperty(value = "组id",required = true)
    private Integer groupId;
}

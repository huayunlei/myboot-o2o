package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-12-05 15:20
 */
@Data
public class SubmitDesignResponse {

    @ApiModelProperty("查询是否有提交设计需求权限 true 有false 没有")
    private Boolean hasPermission;
}

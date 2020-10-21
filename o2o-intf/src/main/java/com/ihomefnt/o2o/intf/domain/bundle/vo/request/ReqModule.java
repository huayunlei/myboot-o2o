package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/11/22
 */

@ApiModel(description = "模块请求参数")
@Data
@Accessors(chain = true)
public class ReqModule {

    @ApiModelProperty("模块唯一码")
    private String moduleCode;

    @ApiModelProperty("模块版本号")
    private Integer versionCode;

}

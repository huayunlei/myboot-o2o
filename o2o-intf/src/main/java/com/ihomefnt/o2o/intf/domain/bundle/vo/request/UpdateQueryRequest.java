package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/11/22
 */

@ApiModel(description = "热更新请求参数")
@Data
@Accessors(chain = true)
public class UpdateQueryRequest {

    @ApiModelProperty(value = "app版本号")
    private Integer appVersionCode;

    @ApiModelProperty(value = "模块列表")
    private List<ReqModule> modules;

    @ApiModelProperty("应用唯一码")
    private String appId;

    @ApiModelProperty("手机号")
    private String mobileNum;
}

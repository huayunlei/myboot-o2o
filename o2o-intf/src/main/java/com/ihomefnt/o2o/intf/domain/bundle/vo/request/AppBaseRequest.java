package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/11/22
 */

@ApiModel("基本请求参数")
@Data
@Accessors(chain = true)
public class AppBaseRequest {

    @ApiModelProperty(value = "应用唯一码")
    private String appId;

    @ApiModelProperty(value = "系统类型：iphone,android,ipad,android_pad,simulator")
    private String systemType;

    @ApiModelProperty(value = "设备唯一码")
    private String deviceToken;

    @ApiModelProperty(value = "APP版本号")
    private Integer appVersionCode;

    @ApiModelProperty("系统版本号")
    private String systemVersion;

}

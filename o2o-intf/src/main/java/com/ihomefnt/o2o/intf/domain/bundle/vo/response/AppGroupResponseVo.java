package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppGroupResponseVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "app名称")
    private String appName;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "下载地址")
    private String url;

    @ApiModelProperty(value = "app类型  1:IOS 2:Android")
    private Integer appType;

    @ApiModelProperty(value = "来源 1: mop 2:其他来源")
    private Integer source;
}

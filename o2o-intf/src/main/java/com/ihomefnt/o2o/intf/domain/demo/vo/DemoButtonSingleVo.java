package com.ihomefnt.o2o.intf.domain.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DemoButtonSingleVo {


    @ApiModelProperty("演示功能按钮图标")
    private String icon;

    @ApiModelProperty("演示功能按钮名称")
    private String buttonName;

    @ApiModelProperty("演示功能按钮调用地址")
    private String buttonUrl;
}

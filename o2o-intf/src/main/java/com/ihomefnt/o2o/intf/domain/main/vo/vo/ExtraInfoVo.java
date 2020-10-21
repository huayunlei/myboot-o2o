package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("通用其它样式")
@Data
public class ExtraInfoVo {

    @ApiModelProperty("核心区状态名称")
    private String mainCoreName;

    @ApiModelProperty("核心区状态编号")
    private String mainCoreNo;

    @ApiModelProperty("文案")
    private String copywriting;

    @ApiModelProperty("按钮文案")
    private String buttonCopywriting;

    @ApiModelProperty("按钮跳转")
    private String openUrl;

    @ApiModelProperty("图标")
    private String icon;
}

package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("首页核心操作区按钮详情")
@Data
public class ActionInfoVo {

    @ApiModelProperty("核心区状态名称")
    private String mainCoreName;

    @ApiModelProperty("核心区状态编号")
    private String mainCoreNo;

    @ApiModelProperty("操作区样式 1单按钮；2双按钮等比；3双按钮左小右大")
    private Integer actionType;

    @ApiModelProperty("主按钮文案")
    private String buttonCopywriting;

    @ApiModelProperty("主按钮跳转")
    private String buttonOpenUrl;

    @ApiModelProperty("副按钮文案")
    private String subButtonCopywriting;

    @ApiModelProperty("副按钮跳转")
    private String subButtonOpenUrl;

    @ApiModelProperty("泡泡文案")
    private String popuCopywriting;

}

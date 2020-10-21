package com.ihomefnt.o2o.intf.domain.main.vo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("首页核心操作区详情")
@Data
public class CoreOperationAreaInfoVo {

    @ApiModelProperty("头部信息")
    private HeadInfoVo headInfo;

    @ApiModelProperty("信息区信息")
    private ContentInfoVo contentInfo;

    @ApiModelProperty("底部按钮信息")
    private ActionInfoVo actionInfo;

    @ApiModelProperty("下滑浮层信息")
    private ExtraInfoVo floatInfo;
}

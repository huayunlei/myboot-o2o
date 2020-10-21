package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("首页核心操作区头部详情")
@Data
public class HeadInfoVo {
    @ApiModelProperty("头部样式类型，目前只有一种")
    private String headType;

    @ApiModelProperty("核心区状态名称")
    private String mainCoreName;

    @ApiModelProperty("核心区状态编号")
    private String mainCoreNo;

    @ApiModelProperty("头部左侧文案")
    private CopywritingLeftVo copywritingLeft;

    @ApiModelProperty("头部中部文案")
    private CopywritingMiddleVo copywritingMiddle;

    @ApiModelProperty("头部右侧文案")
    private CopywritingRightVo copywritingRight;


    @ApiModel("头部左侧文案")
    @Data
    public class CopywritingLeftVo{

        @ApiModelProperty("头部左侧文案1")
        private String leftFirst;

        @ApiModelProperty("头部左侧文案2")
        private String leftSecond;

    }

    @ApiModel("头部中部文案")
    @Data
    public class CopywritingMiddleVo{

        @ApiModelProperty("头部中部图标")
        private String icon;

        @ApiModelProperty("头部中部文案")
        private String copywriting;

        @ApiModelProperty("头部中部跳转")
        private String openUrl;


    }

    @ApiModel("头部右侧文案")
    @Data
    public class CopywritingRightVo{

        @ApiModelProperty("头部右侧文案")
        private String copywriting;

        @ApiModelProperty("头部右侧跳转")
        private String openUrl;

    }
}

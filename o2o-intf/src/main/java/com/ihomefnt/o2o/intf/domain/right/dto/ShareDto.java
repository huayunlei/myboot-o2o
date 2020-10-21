package com.ihomefnt.o2o.intf.domain.right.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分享内容")
public class ShareDto {
    @ApiModelProperty("跳转地址")
    private String radeShareUrl;
    @ApiModelProperty("标题")
    private String  gradeShareTitle;
    @ApiModelProperty("描述文字")
    private String gradeShareDesc;
    @ApiModelProperty("图标地址")
    private String gradeShareIconUrl;
}

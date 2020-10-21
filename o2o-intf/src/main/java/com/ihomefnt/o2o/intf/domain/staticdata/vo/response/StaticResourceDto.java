package com.ihomefnt.o2o.intf.domain.staticdata.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 静态资源数据
 *
 * @author liyonggang
 * @create 2019-11-21 15:34
 */
@Data
@ApiModel("静态资源数据")
public class StaticResourceDto {
    @ApiModelProperty("资源名称")
    private String resourceName;
    @ApiModelProperty("标签")
    private String label;
    @ApiModelProperty("资源类型 1:视频,2:图片")
    private String resourceType;
    @ApiModelProperty("资源类型图标")
    private String resourceTypeIcon;
    @ApiModelProperty("资源链接")
    private String resourceUrl;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("图片比例")
    private Double proportion;
}

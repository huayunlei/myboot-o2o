package com.ihomefnt.o2o.intf.domain.homecard.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("活动图详情")
public class BannerResponseVo {
    @ApiModelProperty("活动图url")
    private String imgUrl;
    @ApiModelProperty("活动图的高")
    private float height;
    @ApiModelProperty("活动图的宽")
    private float width;
    @ApiModelProperty("活动图的高宽比")
    private float ratioHW;
    @ApiModelProperty("活动图的宽高比")
    private float ratioWH;
}

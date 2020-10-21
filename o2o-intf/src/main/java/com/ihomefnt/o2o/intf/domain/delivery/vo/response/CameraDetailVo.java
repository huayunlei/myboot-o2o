package com.ihomefnt.o2o.intf.domain.delivery.vo.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CameraDetailVo {

    @ApiModelProperty("摄像头图片")
    private String url;

    @ApiModelProperty("绑定状态 0 绑定 1未绑定")
    private Integer bindStatus;

    @ApiModelProperty("摄像头品牌 1乐橙 2摩看")
    private Integer brand;

    @ApiModelProperty("摩看摄像头的accessToken")
    private String mokanToken;

    @ApiModelProperty("摄像头SN码")
    private String cameraSn;
}

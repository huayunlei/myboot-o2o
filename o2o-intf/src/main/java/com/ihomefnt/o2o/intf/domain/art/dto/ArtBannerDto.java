package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wanyunxin
 * @create 2019-08-06 19:37
 */
@Data
@ApiModel(value = "新版艾商城banner")
public class ArtBannerDto {

    @ApiModelProperty(value = "banner调整地址")
    private String bannerUrl;

    @ApiModelProperty(value = "banner图片")
    private String picUrl;

    @ApiModelProperty(value = "跳转类型 1 h5 2 RN 3 原生")
    private Integer jumpType;
}

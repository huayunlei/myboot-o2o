package com.ihomefnt.o2o.intf.domain.promotion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by danni.wu on 2018/5/24.
 */
@Data
@ApiModel("活动在app端呈现图列表")
public class ActBasicAppPicturesDto {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "活动编号")
    private Long actCode;

    @ApiModelProperty(value = "图片url")
    private String pictureUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "图片类型 1：banner,2:详情图")
    private Integer picType;

    @ApiModelProperty(value = "是否是首图 0：否，1：是")
    private Integer isFirst;

}

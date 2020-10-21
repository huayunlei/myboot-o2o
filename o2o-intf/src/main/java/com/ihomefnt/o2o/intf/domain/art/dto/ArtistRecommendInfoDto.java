package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-06 20:12
 */
@Data
@ApiModel(value="新艾商城首页艺术家推荐内容")
public class ArtistRecommendInfoDto {

    @ApiModelProperty(value = "推荐内容id")
    private Integer id;

    @ApiModelProperty(value = "艺术家id")
    private String artistId;

    @ApiModelProperty(value = "艺术家名称")
    private String artistName;

    @ApiModelProperty(value = "推荐标题")
    private String recommendTitle;

    @ApiModelProperty(value = "推荐副标题")
    private String recommendSubTitle;

    @ApiModelProperty(value = "显示图片")
    private String recommendImage;
}

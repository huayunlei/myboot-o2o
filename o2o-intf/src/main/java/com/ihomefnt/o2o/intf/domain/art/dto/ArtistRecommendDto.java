package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 17:49
 */

@Data
@ApiModel(value="新艾商城首页艺术家推荐内容对象")
public class ArtistRecommendDto {

    private List<ArtistRecommendInfoDto> artistRecommendInfo;

    @ApiModelProperty(value = "总前台类目数")
    private Integer totalCount;

    @ApiModelProperty(value = "总页数")
    private Integer totalPage;
}

/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:ArtStarWorkList.java
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 小星星作品列表
 *
 * @author ZHAO
 */
@Data
@ApiModel("小星星作品列表")
public class ArtStarWorkList {
    @ApiModelProperty("艺术家作品集合")
    private List<ArterWork> arterWorkList;
    
    @ApiModelProperty("全部艺术家作品")
    private List<ArtStarWork> artStarWorkList;
}

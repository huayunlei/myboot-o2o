package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/12/4 0004.
 */
@Data
@ApiModel("首页展示信息")
public class ArtHomePageResponse {

    @ApiModelProperty("顶部轮播")
    private ArtHomeResponse topRecommend;

    @ApiModelProperty("展示位")
    private ArtHomeResponse banner;

    @ApiModelProperty("热门作品")
    private ArtHomeResponse hostScreenSimple;

    @ApiModelProperty("精选画集")
    private ArtHomeResponse selectedScreenSimple;

    @ApiModelProperty("猜你喜欢")
    private ArtHomeResponse guessScreenSimple;

}

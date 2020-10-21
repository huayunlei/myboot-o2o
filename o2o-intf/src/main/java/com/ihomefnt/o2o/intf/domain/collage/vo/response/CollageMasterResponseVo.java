package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/15 11:18
 */
@Data
@ApiModel(" 团页面（团主和成员共用的页面）")
public class CollageMasterResponseVo {

    @ApiModelProperty("ProductInfoVo 商品信息")
    private ProductVo productInfoVo;

    @ApiModelProperty("CollageInfoVo 团信息")
    private CollageInfoVo collageInfoVo;

    @ApiModelProperty("CollageInfoVo 团活动")
    private GroupBuyActivityVo activityInfoVo;

    @ApiModelProperty("UserInfoVo 当前openid所在的团")
    private UserInfoVo userCollageInfoVo;

    @ApiModelProperty("服务端当前时间戳")
    private Long currentTime;

    public CollageMasterResponseVo(GroupBuyActivityVo activityInfoVo , ProductVo productInfoVo,
                                   CollageInfoVo collageInfoVo,UserInfoVo userCollageInfoVo ) {
        this.productInfoVo = productInfoVo;
        this.collageInfoVo = collageInfoVo;
        this.activityInfoVo = activityInfoVo;
        this.userCollageInfoVo = userCollageInfoVo;
        this.currentTime = System.currentTimeMillis();
    }
}

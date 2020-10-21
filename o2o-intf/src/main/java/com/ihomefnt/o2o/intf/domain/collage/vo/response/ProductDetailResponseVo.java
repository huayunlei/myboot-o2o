package com.ihomefnt.o2o.intf.domain.collage.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/15 13:44
 */
@Data
@ApiModel("ProductDetailResponseVo")
public class ProductDetailResponseVo {


    @ApiModelProperty("艺术品详情")
    private Object artSkuDetail;

    @ApiModelProperty("ProductInfoVo 商品信息")
    private ProductVo productInfo;

    @ApiModelProperty("GroupBuyActivityVo 活动信息")
    private GroupBuyActivityVo activityInfoVo;

    @ApiModelProperty("CollageInfoVo 团信息")
    private CollageInfoVo collageInfoVo;

    @ApiModelProperty("CollageInfoVo 团信息")
    private UserInfoVo userCollageInfoVo;

    @ApiModelProperty("服务端当前时间戳")
    private Long currentTime;

    public ProductDetailResponseVo(GroupBuyActivityVo activityInfoVo , ProductVo productInfo,
                                   CollageInfoVo collageInfoVo,UserInfoVo userCollageInfoVo ) {
        this.activityInfoVo = activityInfoVo;
        this.productInfo = productInfo;
        this.collageInfoVo = collageInfoVo;
        this.userCollageInfoVo = userCollageInfoVo;
        this.currentTime = System.currentTimeMillis();
    }
}

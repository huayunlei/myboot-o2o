package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/14 21:35
 */
@Data
@ApiModel("MainPageVo-活动主页")
public class MainPageVo {


    @ApiModelProperty("productInfo 商品信息")
    private ProductVo  productInfo;

    @ApiModelProperty("activityInfo 活动信息")
    private GroupBuyActivityVo activityInfo;

    @ApiModelProperty("aijiaDescInfo 艾佳简介信息")
    private Object aijiaDescInfo;

    @ApiModelProperty("UserInfoVo userInfoVo=null 表示为参团")
    private UserInfoVo userInfoVo;

    @ApiModelProperty("服务端当前时间戳")
    private Long currentTime;

    public MainPageVo(GroupBuyActivityVo activityInfo, ProductVo productInfo, Object aijiaDescInfo, UserInfoVo userInfoVo) {
        this.activityInfo = activityInfo;
        this.productInfo = productInfo;
        this.aijiaDescInfo = aijiaDescInfo;
        this.userInfoVo = userInfoVo;
        this.currentTime = System.currentTimeMillis();
    }


}

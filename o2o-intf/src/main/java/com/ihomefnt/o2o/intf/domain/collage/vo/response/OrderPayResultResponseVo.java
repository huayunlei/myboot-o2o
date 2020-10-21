package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jerfan cang
 * @date 2018/10/17 13:50
 */
@Data
@NoArgsConstructor
@ApiModel("OrderPayResultResponseVo")
public class OrderPayResultResponseVo {

    @ApiModelProperty("GroupBuyActivityVo 活动信息")
    private GroupBuyActivityVo activityInfo;

    @ApiModelProperty("CollageInfoVo 团信息")
    private CollageInfoVo collageInfoVo;

    @ApiModelProperty("服务器当前时间戳")
    private Long currentTime;

    public OrderPayResultResponseVo(GroupBuyActivityVo activityInfo, CollageInfoVo collageInfoVo) {
        this.activityInfo = activityInfo;
        this.collageInfoVo = collageInfoVo;
        this.currentTime = System.currentTimeMillis();
    }
}

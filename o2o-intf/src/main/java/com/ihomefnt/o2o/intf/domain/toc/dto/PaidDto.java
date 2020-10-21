package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@ApiModel("已交定信息")
public class PaidDto {

    @ApiModelProperty("手机号")
    private String mobileNum;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("交定金时间")
    private String paidTime;

    @ApiModelProperty("用户头像")
    private String headImgUrl;

    @ApiModelProperty("剩余抽奖次数")
    private Integer times;

    @ApiModelProperty("状态  1=正常，2=用户订单全部已退款 默认正常")
    private Integer state = 1;
}

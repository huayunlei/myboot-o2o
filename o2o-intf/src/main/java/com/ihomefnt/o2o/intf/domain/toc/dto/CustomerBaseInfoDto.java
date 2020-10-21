package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/19 0019.
 */
@Data
public class CustomerBaseInfoDto {


    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("绑定时间")
    private String bindTimeStr;

    @ApiModelProperty("客户姓名")
    private String customerName;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("定金时间")
    private String depositTimeStr;

    @ApiModelProperty("是否抽奖 0-否 1-是")
    private Integer luckyDraw;

    @ApiModelProperty("剩余次数")
    private Integer residueDegree;

    @ApiModelProperty("状态  1=正常，2=用户订单全部已退款")
    private Integer state =1;

}

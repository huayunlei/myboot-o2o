package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/17 13:47
 */
@Data
@ApiModel("QueryPayRequest")
public class QueryPayRequest  {

    @ApiModelProperty("屏幕宽度")
    private Integer width;

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("openid")
    private String openid;
}

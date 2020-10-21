package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询团页面请求参数
 *
 * @author jerfan cang
 * @date 2018/10/15 15:32
 */
@Data
@ApiModel("QueryCollageMasterRequest")
public class QueryCollageMasterRequest{

    @ApiModelProperty("屏幕宽度")
    private Integer width;

    @ApiModelProperty("openId 必传")
    private String openid;

    @ApiModelProperty("团id ")
    private Integer groupId;

    @ApiModelProperty("活动id 必传")
    private Integer activityId;
}

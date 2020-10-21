package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询H5艺术品拼团活动详情的请求bean
 * @author jerfan cang
 * @date 2018/10/13 14:29
 */
@Data
@ApiModel("QueryCollageActivityDetailRequest")
public class QueryCollageActivityDetailRequest {

    @ApiModelProperty("屏幕宽度")
    private Integer width;

    @ApiModelProperty("活动id 必传")
    private Integer activityId;

    @ApiModelProperty("openid 必传")
    private String openid;
}

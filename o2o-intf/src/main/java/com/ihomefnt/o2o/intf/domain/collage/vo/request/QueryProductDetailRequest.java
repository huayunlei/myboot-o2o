package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * H5查询拼团商品详情 附带团信息
 * @author jerfan cang
 * @date 2018/10/15 15:26
 */
@Data
@ApiModel("QueryProductDetailRequest")
public class QueryProductDetailRequest{

    @ApiModelProperty("屏幕宽度")
    private Integer width;

    @ApiModelProperty("活动id 必传")
    private Integer activityId;

    @ApiModelProperty("团id")
    private Integer groupId;

    @ApiModelProperty("openid 必传")
    private String openid;
}

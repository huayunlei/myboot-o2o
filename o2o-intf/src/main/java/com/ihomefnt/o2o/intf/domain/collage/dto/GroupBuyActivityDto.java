package com.ihomefnt.o2o.intf.domain.collage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * link GroupBuyActivityVo
 * @author jerfan cang
 * @date 2018/10/13 14:12
 */
@Data
@ApiModel("活动dto")
public class GroupBuyActivityDto {

    @ApiModelProperty("活动id")
    private Integer id;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动类型")
    private Integer type;

    @ApiModelProperty("活动开始时间")
    private Long beginTime;

    @ApiModelProperty("活动结束时间")
    private Long endTime;

    @ApiModelProperty("活动规则")
    private String activityRules;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("拼团销售商品的总数")
    private Integer joinCount;

    @ApiModelProperty("团成员上限 可以超")
    private Integer groupLimit;
}

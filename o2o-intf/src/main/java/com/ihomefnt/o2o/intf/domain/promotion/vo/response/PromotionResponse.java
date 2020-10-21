/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:PromotionResponse.java
 */
package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("活动")
public class PromotionResponse implements Serializable {

    @ApiModelProperty("活动ID")
    private Integer actCode;// 活动ID

    @ApiModelProperty("活动名称")
    private String actName;// 活动名称

    @ApiModelProperty("活动文本描述")
    private String actDesc;// 活动文本描述

    @ApiModelProperty("活动奖励规则描述")
    private String rewardRuleDesc;// 活动奖励规则描述

    @ApiModelProperty("活动状态:1 即将结束 2.正在进行 3.即将开始 4.已参与")
    private Integer status;// 活动状态:1 即将结束 2.正在进行 3.即将开始 4.已参与

    @ApiModelProperty("活动开始时间")
    private Date startTime;// 活动开始时间

    @ApiModelProperty("活动结束时间")
    private Date endTime;// 活动结束时间

    @ApiModelProperty("活动开始时间")
    private String startTimeDesc;// 活动开始时间

    @ApiModelProperty("活动结束时间")
    private String endTimeDesc;// 活动结束时间

    @ApiModelProperty("优惠金额")
    private BigDecimal promotionAmount;// 优惠金额

    @ApiModelProperty("与本活动互斥的活动ID")
    private List<Integer> mutexPromotions;// 与本活动互斥的活动ID

    @ApiModelProperty("是否选中:0:否，1:是")
    private Integer isSelect;// 是否选中:0:否，1:是

    @ApiModelProperty("是否成功参加:0:否，1:是")
    private Integer isSuccessJoin;// 是否成功参加:0:否，1:是

}

/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:PromotionPageResponse.java
 */
package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("促销活动")
public class PromotionPageResponse implements Serializable {

    // 已付金额 ＜ 订单总价 & 订单下没有“参加成功”的活动
    // 1: 待开始活动数==0 && 正在进行活动数==0
    // 2: 待开始活动数>=1 && 正在进行活动数==0
    // 3: 正在进行活动数>=1 未选择活动
    // 4: 正在进行活动数>=1 已选择活动
    // 已付金额 ＜ 订单总价 & 订单下有“参加成功”的活动
    // 已付金额 ≥ 订单总价
    // 5:没有参加过活动
    // 6:参加过活动

    @ApiModelProperty("场景编码")
    private Integer promotionCode; // 场景编码

    @ApiModelProperty("下次活动进行时间")
    private String nextTime;// 下次活动进行时间

    @ApiModelProperty("立减活动最小金额")
    private BigDecimal minPromotionMoney;// 立减活动最小金额

    @ApiModelProperty("活动优惠总金额")
    private BigDecimal totalPromotionMoney;// 活动优惠总金额

    @ApiModelProperty("剩余时间倒计时")
    private String timeLeft;// 剩余时间倒计时

    @ApiModelProperty("剩余时间倒计时")
    private Long minTime;// 剩余时间倒计时

    @ApiModelProperty("活动列表")
    private List<PromotionResponse> promotionList;// 活动列表
}

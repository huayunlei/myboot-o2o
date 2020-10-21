package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.BannerResponseVo;
import com.ihomefnt.o2o.intf.domain.promotion.dto.HouseOrderBriefInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("活动相关信息")
public class MarketingActivityVo {
    @ApiModelProperty("活动ID")
    private Integer activityId;//活动id
    @ApiModelProperty("活动图标")
    private String icon;//活动图标
    @ApiModelProperty("活动名")
    private String activityName;//活动名
    @ApiModelProperty("活动楼盘")
    private List<String> buildings;//活动楼盘
    @ApiModelProperty("活动头图")
    private BannerResponseVo headBanner;
    @ApiModelProperty("活动图")
    private List<BannerResponseVo> banners;
    @ApiModelProperty("活动文案")
    private String activityDesc;//活动文案
    @ApiModelProperty("活动开始时间")
    private String startDate;//活动开始时间
    @ApiModelProperty("活动活动结束时间")
    private String endDate;//活动结束时间
    @ApiModelProperty("可以参加活动的订单阶段")
    private String availableStage;//可以参加活动的订单编号
    @ApiModelProperty("可以参加活动的条件")
    private String availableCondition;//可以参加活动条件
    @ApiModelProperty("互斥活动")
    private List<String> mutexActivityName;
    @ApiModelProperty("奖励规则描述")
    private String rewardRuleDesc;//奖励规则描述

    @ApiModelProperty("是否已参加")
    private Boolean isIn;
    @ApiModelProperty("是否可以参加")
    private Boolean isAvailable;
    @ApiModelProperty("优惠内容")
    private List<String> preferentialTerms;
    @ApiModelProperty("是否是新活动")
    private Boolean isNewActivity;//仅在首页弹窗时需要用，其他地方不需要
    @ApiModelProperty("h5跳转链接")
    private String h5Url;
    @ApiModelProperty("可以参加的订单及相关的房屋信息")
    private List<HouseOrderBriefInfoVo> orders;
    @ApiModelProperty("不可参加原因")
    private String unAvailableDesc;
    @ApiModelProperty("互斥活动列表")
    private List<Integer> mutexPromotions;
}

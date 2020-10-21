package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/20
 */

@ApiModel("已选方案信息")
@Data
public class SolutionSelected implements Serializable{

    @ApiModelProperty("已选方案效果")
    private SolutionEffectInfo solutionSelected;

    @ApiModelProperty("已选空间设计列表")
    private List<SpaceDesign> spaceDesignSelected;

    @ApiModelProperty("置家顾问电话")
    private String homeAdviserMobile;//置家顾问电话

    @ApiModelProperty("活动赠品。已逗号分隔表示换行")
    private String activeGift;//活动赠品。已逗号分隔表示换行

    @ApiModelProperty("需求确认标志")
    private boolean confirmationFlag;//需求确认标志

    @ApiModelProperty("需求确认时间")
    private String confirmationTime;//需求确认时间

    @ApiModelProperty("预计开工时间")
    private String scheduledDate;//预计开工时间

    @ApiModelProperty("订单总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("方案标识 0普通方案，1爱家贷专用方案")
    private Integer solutionTag = 0;

    @ApiModelProperty("区分新老订单类型:0老订单 1:新订单")
    private Integer newOrderState;

    @ApiModelProperty("订单类型 0：方案 6：代客下单")
    private Integer orderSource;

    @ApiModelProperty("订单选方案简要信息")
    private SolutionInfo solutionInfo;

    @ApiModelProperty("-1:失效（老订单） 0：锁价中 1：最终锁价")
    private Integer lockPriceFlag;

    @ApiModelProperty("beta端变动标识 0：未变动 1：发生变动")
    private Integer betaChangeFlag = 0;

    private List<ServiceItemDto> serviceItemList;//服务项列表
}

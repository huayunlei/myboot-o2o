package com.ihomefnt.o2o.intf.domain.user.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.NoStandardUpgradeInfoVo;
import com.ihomefnt.o2o.intf.domain.program.dto.StandardUpgradeInfoVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinAddBagInfoVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinIncrementItemVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinRefundInfoVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppValetOrderInfoSoftDetailVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 大订单详情
 *
 * @author liyonggang
 * @create 2019-02-22 19:41
 */
@Data
public class AppMasterOrderDetailDto {


    private Integer orderNum;//主订单号,

    private AppMasterOrderInfoDto masterOrderInfo;//主订单信息,

    private HousePropertyInfoResultDto houseInfo;//客户房产基本信息,

    private AppMasterOrderAccountInfoDto accountInfo;//主订单款项信息,

    private AppMasterOrderCustomerInfoDto customerInfo;// 订单客户信息,

    private AppSoftOrderInfoDto softOrderInfo;// 软装子订单信息,

    private AppHardOrderInfoDto hardOrderInfo;//硬装子订单信息,

    private AladdinRefundInfoVo refundInfo;// 主订单退款信息,

    private Boolean checkResult;//是否需要需求确认,

    private Integer oldOrder;//是否是老订单(0. 老订单 1. 新订单),

    private Integer productCount;//商品总数

    private List<AppValetOrderInfoSoftDetailVo> valetSoftOrderInfo;// 代客下单软装子订单列表

    private AladdinIncrementItemVo incrementResultDto;// 增减项信息详情

    private Integer joinActFlag;// 是否参加了1219活动 0：否 1：是

    private Date joinTime;// 参加活动时间

    private String joinTimeStr;// 参加活动日期描述

    private List<StandardUpgradeInfoVo> upgradeInfos; // 标准升级包

    private List<NoStandardUpgradeInfoVo> noUpgradeInfos; // 非标准升级包

    private List<AladdinAddBagInfoVo> addBags;// 增配包商品列表

}

package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.NoStandardUpgradeInfoVo;
import com.ihomefnt.o2o.intf.domain.program.dto.SolutionRoomDetailVo;
import com.ihomefnt.o2o.intf.domain.program.dto.StandardUpgradeInfoVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 全品家订单信息
 * 
 * @author ZHAO
 */
@Data
public class AladdinOrderResultDto {

	private AladdinOrderBaseInfoVo orderInfo;// 订单信息

	private AladdinDealInfoVo transactionInfo;// 交易信息

	private AladdinUserInfoVo userInfo;// 用户信息

	private HouseInfoResponseVo houseInfo;// 房产信息

	private AladdinAdviserInfoVo adviserInfo;// 置家顾问信息

	private AladdinBuildingProjectInfoVo buildingInfo;// 楼盘项目信息

	private AladdinProgramInfoVo solutionSelectedInfo;// 已选方案信息

	private AladdinHardOrderInfoVo hardOrderInfo;// 硬装订单信息

	private AladdinSoftOrderInfoVo softOrderInfo;// 软装订单信息

	private List<AladdinAddBagInfoVo> addBags;// 增配包商品列表

	private List<StandardUpgradeInfoVo> upgradeInfos; // 标准升级包

	private List<NoStandardUpgradeInfoVo> noUpgradeInfos; // 非标准升级包

	private AladdinRefundInfoVo refundInfo;// 退款信息信息

	private List<AppValetOrderInfoSoftDetailVo> valetSoftOrderInfo;// 代客下单软装子订单列表

	private AladdinIncrementItemVo incrementResultDto;// 增减项信息详情

	private ActPageResponseVo queryPromotionResultDto;// 订单参加活动列表

	private Integer joinActFlag;// 是否参加了1219活动 0：否 1：是

	private Date joinTime;// 参加活动时间

	private String joinTimeStr;// 参加活动日期描述
	
	private List<SolutionRoomDetailVo> solutionRoomDetailVoList ; // 空间集合

	private Boolean checkResult;//是否需要需求确认

	private Integer oldOrder;//是否是老订单(0. 老订单 1. 新订单)

}

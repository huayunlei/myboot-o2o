package com.ihomefnt.o2o.service.service.promotion;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HomeCarnivalInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HomeCarnivalInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HomeCarnivalOrderInfo;
import com.ihomefnt.o2o.intf.domain.promotion.dto.*;
import com.ihomefnt.o2o.intf.domain.promotion.vo.request.MarketingActivityRequest;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.*;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderResultDto;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbActivityResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.promotion.PromotionProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.promotion.PromotionService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PromotionServiceImpl implements PromotionService{
	@Autowired
	private AjbProxy ajbProxy;

	@Autowired
	private ProductProgramOrderProxyImpl productProgramOrderProxy;

	@Autowired
	private ApiConfig apiConfig;

	@Autowired
    PromotionProxy promotionProxy;

	@Autowired
	private ProductProgramProxy productProgramProxy;
	@Autowired
	HouseService houseService;
	@Autowired
	PersonalCenterProxy personalCenterProxy;

	@Override
	public PromotionEffectiveResponse getPromotionEffective(HttpBaseRequest request, Integer userId) {
		PromotionEffectiveResponse response = new PromotionEffectiveResponse();
		int effectiveType = 1;//无活动
		//查询活动是否下架 开始结束时间
		AjbActivityResponseVo ajbActivityResponseVo = ajbProxy.queryAjbActivityByCode(ProductProgramPraise.HOME_CARNIVAL);
		if(ajbActivityResponseVo != null && ajbActivityResponseVo.getActiveFlag() != null && ajbActivityResponseVo.getActiveFlag() == 0){
			//判断当前时间是否在活动时间内
			Date nowTime = new Date();
			Date beginTime = new Date();
			Date endTime = new Date();
			if(ajbActivityResponseVo.getStartTime() != null){
				beginTime = ajbActivityResponseVo.getStartTime();
			}
			if(ajbActivityResponseVo.getEndTime() != null){
				endTime = ajbActivityResponseVo.getEndTime();
			}
			if (nowTime.after(beginTime) && nowTime.before(endTime)) {
				//判断是否参加活动
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userId", userId);
				HomeCarnivalInfoResponseVo carnivalInfoResponseVo = productProgramOrderProxy.queryOrderActRecordByUser(paramMap);
				if(carnivalInfoResponseVo != null && CollectionUtils.isNotEmpty(carnivalInfoResponseVo.getOrderHouseList())){
					int activeNum = 0;//有活动未参加订单数:意向、定金、签约阶段

					//有房产
					for (OrderHouseInfoResultVo houseInfoResultVo : carnivalInfoResponseVo.getOrderHouseList()) {
						if(houseInfoResultVo.getJoinActFlag() != null && houseInfoResultVo.getJoinActFlag().equals(ProductProgramPraise.ACTIVITY_FLAG_JOIN)){
							//已参加活动
							effectiveType = 3;
							break;
						}else{
							//未参加活动
							//1、意向、定金、签约阶段：弹框
							if(houseInfoResultVo.getOrderStatus() != null && (houseInfoResultVo.getOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_PURPOSE) ||
									houseInfoResultVo.getOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_HANDSEL) || houseInfoResultVo.getOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_SIGN))){
								activeNum++;
							}
						}
					}

					//所有订单未参加活动
					if(effectiveType != 3){
						if(activeNum > 0){
							//有意向、定金、签约阶段订单
							effectiveType = 2;
						}else{
							//无意向、定金、签约阶段订单
							effectiveType = 1;
						}
					}
				}else{
					//无房产
					effectiveType = 1;//无活动
				}
			}
		}

		response.setEffectiveType(effectiveType);
		response.setShareUrl(apiConfig.getHomeCarnivalUrl());

		return response;
	}

	@Override
	public PromotionActiveUserNumResponseVo getActiveUserNum(Integer userId) {
		PromotionActiveUserNumResponseVo result = new PromotionActiveUserNumResponseVo();
		Integer userNum = 0;
		String adviserMobile = ProductProgramPraise.ADVISER_MOBILE_DEFAULT;
		Integer orderId = 0;
		Integer orderSource = 0;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		HomeCarnivalInfoResponseVo carnivalInfoResponseVo = productProgramOrderProxy.queryOrderActRecordByUser(paramMap);
		if(carnivalInfoResponseVo != null){
			if(CollectionUtils.isNotEmpty(carnivalInfoResponseVo.getOrderHouseList())){
				if(carnivalInfoResponseVo.getOrderHouseList().get(0).getOrderNum() != null){
					orderId = carnivalInfoResponseVo.getOrderHouseList().get(0).getOrderNum();//只有一个订单，orderID有值就跳详情；多个订单跳列表，orderID无值
				}
				if(carnivalInfoResponseVo.getOrderHouseList().get(0).getSource() != null){
					orderSource = carnivalInfoResponseVo.getOrderHouseList().get(0).getSource();
				}
			}
			if(carnivalInfoResponseVo.getJoinActTotalCount() != null){
				userNum = carnivalInfoResponseVo.getJoinActTotalCount();
			}
			if(StringUtils.isNotBlank(carnivalInfoResponseVo.getAdviserMobile())){
				adviserMobile = carnivalInfoResponseVo.getAdviserMobile();
			}
		}

		result.setOrderId(orderId);
		result.setAdviserMobile(adviserMobile);
		result.setOrderSource(orderSource);
		result.setUserNum(userNum);
		return result;
	}

	@Override
	public HomeCarnivalInfoResponse getHomeCarnivalInfo(HttpBaseRequest request, Integer userId) {
		HomeCarnivalInfoResponse carnivalInfoResponse = new HomeCarnivalInfoResponse();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		HomeCarnivalInfoResponseVo carnivalInfoResponseVo = productProgramOrderProxy.queryOrderActRecordByUser(paramMap);
		if(carnivalInfoResponseVo != null){
			//1219信息
			HomeCarnivalInfo homeCarnivalInfo = new HomeCarnivalInfo();
			if(StringUtils.isNotBlank(carnivalInfoResponseVo.getCustomerName())){
				homeCarnivalInfo.setUserName(carnivalInfoResponseVo.getCustomerName());
			}
			if(carnivalInfoResponseVo.getPreReturnAijiaCoinCount() != null){
				homeCarnivalInfo.setPreReturnAijiaCoinCount(carnivalInfoResponseVo.getPreReturnAijiaCoinCount());
			}
			if(carnivalInfoResponseVo.getSort() != null){
				homeCarnivalInfo.setSort(carnivalInfoResponseVo.getSort());
			}
			if(carnivalInfoResponseVo.getPreReturnMoneyAmount() != null){
				homeCarnivalInfo.setPreReturnMoneyAmount(carnivalInfoResponseVo.getPreReturnMoneyAmount());
			}
			if(carnivalInfoResponseVo.getHasReturnedMoneyAmount() != null){
				homeCarnivalInfo.setHasReturnedMoneyAmount(carnivalInfoResponseVo.getHasReturnedMoneyAmount());
			}
			if(carnivalInfoResponseVo.getHasReturnAijiaCoinCount() != null){
				homeCarnivalInfo.setHasReturnAijiaCoinCount(carnivalInfoResponseVo.getHasReturnAijiaCoinCount());
			}
			if(carnivalInfoResponseVo.getLoanType() != null){
				homeCarnivalInfo.setLoanType(carnivalInfoResponseVo.getLoanType());
			}
			if(carnivalInfoResponseVo.getJoinActTotalCount() != null){
				homeCarnivalInfo.setJoinActTotalCount(carnivalInfoResponseVo.getJoinActTotalCount());
			}
			homeCarnivalInfo.setProfitRate(ProductProgramPraise.PROFIT_RATE);
			carnivalInfoResponse.setHomeCarnivalInfo(homeCarnivalInfo);

			//订单信息
			if(CollectionUtils.isNotEmpty(carnivalInfoResponseVo.getOrderHouseList())){
				List<HomeCarnivalOrderInfo> allProductOrderList = new ArrayList<HomeCarnivalOrderInfo>();
				for (OrderHouseInfoResultVo houseInfoResultVo : carnivalInfoResponseVo.getOrderHouseList()) {
					HomeCarnivalOrderInfo or = new HomeCarnivalOrderInfo();
					or.setOrderId(houseInfoResultVo.getOrderNum());
					or.setOrderType(OrderConstant.ORDER_TYPE_ALADDIN);//全品家订单
					//订单状态扭转
					if(houseInfoResultVo.getOrderStatus() != null){
						or.setState(houseInfoResultVo.getOrderStatus());
					}else{
						or.setState(-1);//订单状态
					}
					if(StringUtils.isNotBlank(houseInfoResultVo.getOrderStatusStr())){
						or.setStateDesc(houseInfoResultVo.getOrderStatusStr());
					}
					if(houseInfoResultVo.getSource() != null){
						or.setOrderSource(houseInfoResultVo.getSource());
					}
					String buildingName = "";
					if (StringUtils.isNotBlank(houseInfoResultVo.getAddress())) {
						buildingName = houseInfoResultVo.getAddress();
					}
					if (StringUtils.isNotBlank(houseInfoResultVo.getBuildingName())) {
						buildingName = buildingName + houseInfoResultVo.getBuildingName().replace(ProductProgramPraise.HOUSE_NAME_BBC, "");
					}
					or.setBuildingAddress(buildingName);
					if (StringUtils.isNotBlank(houseInfoResultVo.getArea())) {
						if(houseInfoResultVo.getArea().contains(ProductProgramPraise.AREA)){
							or.setHouseArea(houseInfoResultVo.getArea());
						}else{
							or.setHouseArea(houseInfoResultVo.getArea() + ProductProgramPraise.AREA);
						}
					}
					if (StringUtils.isNotBlank(houseInfoResultVo.getLayoutName())) {
						if (houseInfoResultVo.getLayoutName().contains(HomeCardPraise.HOUSE_TYPE)) {
							or.setHouseName(houseInfoResultVo.getLayoutName());
						} else {
							or.setHouseName(houseInfoResultVo.getLayoutName() + HomeCardPraise.HOUSE_TYPE);
						}
					}
					StringBuffer housePattern = new StringBuffer("");
					if (houseInfoResultVo.getLayoutRoom() != null && houseInfoResultVo.getLayoutRoom() > 0) {
						housePattern.append(houseInfoResultVo.getLayoutRoom()).append(ProductProgramPraise.CHAMBER);
					}
					if (houseInfoResultVo.getLayoutLiving() != null && houseInfoResultVo.getLayoutLiving() > 0) {
						housePattern.append(houseInfoResultVo.getLayoutLiving()).append( ProductProgramPraise.HALL);
					}
					if (houseInfoResultVo.getLayoutKitchen() != null && houseInfoResultVo.getLayoutKitchen() > 0) {
						housePattern.append(houseInfoResultVo.getLayoutKitchen()).append(ProductProgramPraise.KITCHEN);
					}
					if (houseInfoResultVo.getLayoutToilet() != null && houseInfoResultVo.getLayoutToilet() > 0) {
						housePattern.append(houseInfoResultVo.getLayoutToilet()).append(ProductProgramPraise.TOILET);
					}
					if (houseInfoResultVo.getLayoutBalcony() != null && houseInfoResultVo.getLayoutBalcony() > 0) {
						housePattern.append(houseInfoResultVo.getLayoutBalcony()).append(ProductProgramPraise.BALCONY);
					}
					or.setHousePattern(housePattern.toString());
					//楼栋单元房号
					StringBuffer houseRoomNum = new StringBuffer("");
					if(StringUtils.isNotBlank(houseInfoResultVo.getHousingNum())){
						houseRoomNum.append(houseInfoResultVo.getHousingNum());
					}
					if(StringUtils.isNotBlank(houseInfoResultVo.getRoomNum())){
						if(StringUtils.isNotBlank(houseRoomNum)){
							houseRoomNum.append("-");
						}
						houseRoomNum.append(houseInfoResultVo.getRoomNum());
					}
					or.setHouseFullName(houseRoomNum.toString());
					if(houseInfoResultVo.getJoinActFlag() != null && houseInfoResultVo.getJoinActFlag().equals(ProductProgramPraise.ACTIVITY_FLAG_JOIN)){
						or.setHomeCarnivalFlag(true);
					}
					if(StringUtils.isNotBlank(houseInfoResultVo.getJoinTimeStr())){
						or.setHomeCarnivalTime(houseInfoResultVo.getJoinTimeStr());
					}
					if(StringUtils.isNotBlank(houseInfoResultVo.getAdviserMobile())){
						or.setAdviserMobileNum(houseInfoResultVo.getAdviserMobile());
					}else{
						or.setAdviserMobileNum(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
					}
					allProductOrderList.add(or);
				}
				carnivalInfoResponse.setAllProductOrderList(allProductOrderList);
			}
		}

		return carnivalInfoResponse;
	}

	@Override
	public MarketingActivityVo queryActivityById(MarketingActivityRequest request, Integer userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("activityId", request.getActivityId());
		params.put("isIn", request.getIsIn());
		params.put("isAvailable", request.getIsAvailable());
		params.put("width", request.getWidth());
		params.put("userId", userId);

		List<HouseInfoResponseVo> hifr = null;
		if(userId != null) {
			hifr = houseService.queryUserHouseList(userId);
		}
		if(hifr == null || hifr.size() == 0) {
			params.put("hasOrder", 0);
		} else {
			params.put("hasOrder", 1);
		}

		MarketingActivityVo mav = promotionProxy.queryActivityById(params);
		if(mav != null && hifr != null) {
			List<HouseOrderBriefInfoVo> orders = fillOrdersInfoByHousesDetail(hifr, mav.getOrders());
			mav.setOrders(orders);
		}

		return mav;
	}

	@Override
	public List<MarketingActivityVo> queryEffectiveActivitiesByAccessTokenAndLocation(Integer userId, MarketingActivityRequest request) {
		List<MarketingActivityVo> mavs = null;
		List<HouseInfoResponseVo> hifr = houseService.queryUserHouseList(userId);
		if(hifr != null && hifr.size() >= 1) {
			mavs = queryEffectiveActivitiesByUserId(userId, request);
		} else {
			mavs = queryEffectiveActivitiesByLocation(userId, request);
		}
		return mavs;
	}

	@Override
	public List<MarketingActivityVo> queryActivityByOrderId(MarketingActivityRequest request) {
		Map<String, Object> params = new HashMap<>();
		params.put("orderId", request.getOrderId());
		params.put("width", request.getWidth());
		return promotionProxy.queryEffectiveActivitiesByOrderId(params);
	}

	@Override
	public List<MarketingActivityVo> queryAllActivityByUser(Integer userId, MarketingActivityRequest request) {
		return promotionProxy.queryEffectiveActivitiesByUserId(userId, request.getWidth());
	}

	@Override
	public JoinPromotionResponseVo participateActivityByOrderId(MarketingActivityRequest request) {
		Map<String, Object> params = new HashMap<>();
		params.put("orderId", request.getOrderId());
		params.put("activityId", request.getActivityId());

		JoinPromotionResultDto dto = promotionProxy.participateActivityByOrderId(params);
		if (null == dto) {
			return null;
		}
		JoinPromotionResponseVo responseVo = new JoinPromotionResponseVo();
		responseVo.setPromotionAmount(dto.getPromotionAmount());
		return responseVo;
	}

	private List<MarketingActivityVo> queryEffectiveActivitiesByUserId(Integer userId, MarketingActivityRequest request) {
        List<OrderPromotionActDetailDto> promotionResultActDtos=new ArrayList<>();

        List<AppMasterOrderResultDto> masterOrderList = personalCenterProxy.queryMasterOrderListByUserId(userId);
        if (!org.springframework.util.CollectionUtils.isEmpty(masterOrderList)){
            masterOrderList.forEach(dto -> {
                QueryPromotionResultDto actDto = promotionProxy.queryPromotionByOrderNum(dto.getMasterOrderId());
                OrderPromotionActDetailDto actDetailDto=new OrderPromotionActDetailDto();
                actDetailDto.setOrderNum(dto.getMasterOrderId());
                actDetailDto.setPromotionResultDto(actDto);

                promotionResultActDtos.add(actDetailDto);
            });
        }

//		return promotionProxy.queryEffectiveActivitiesByUserId(params);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("width", request.getWidth());
        return promotionProxy.queryEffectiveActivitiesByUserId(userId, request.getWidth());
	}

	private List<MarketingActivityVo> queryEffectiveActivitiesByLocation(Integer userId, MarketingActivityRequest request) {
		Map<String, Object> params = new HashMap<>();
		String location = request.getLocation();
		if(location.indexOf("市") == -1) location = location + "市";
		params.put("location", location);
		params.put("width", request.getWidth());
		params.put("userId", userId);

		return promotionProxy.queryEffectiveActivitiesByLocation(params);
	}

	private List<HouseOrderBriefInfoVo> fillOrdersInfoByHousesDetail(List<HouseInfoResponseVo> housesDetail, List<HouseOrderBriefInfoVo> orders) {
		List<HouseOrderBriefInfoVo> rets = null;
		if(orders != null && orders.size() >= 1) {
			rets = new ArrayList<>();

			for(HouseOrderBriefInfoVo houseOrderBriefInfoVo : orders) {
				HouseOrderBriefInfoVo ret = new HouseOrderBriefInfoVo();
				ret.setOrderId(houseOrderBriefInfoVo.getOrderId());

				if(housesDetail != null && housesDetail.size() >= 1) {
					for(HouseInfoResponseVo singleHouseDetail : housesDetail) {
						if (singleHouseDetail.getMasterOrderId().intValue() == houseOrderBriefInfoVo.getOrderId().intValue()) {
							//订单状态转换
							ret.setStage(transferOrderStatus(singleHouseDetail.getMasterOrderStatus()));
							ret.setState(singleHouseDetail.getMasterOrderStatus());
							//楼盘信息拼接
							ret.setHouseInfo(getClientHouseInfo(singleHouseDetail));
							ret.setHouseNum(getClientHouseNum(singleHouseDetail));
							ret.setBuildingInfo(singleHouseDetail.getBuildingInfo());
						}
					}
				}
				rets.add(ret);
			}
		}
		return rets;
	}

	private String getClientHouseInfo(HouseInfoResponseVo houseDetail) {
		StringBuilder houseString = new StringBuilder();
		houseString.append(StringUtils.isNotBlank(houseDetail.getHouseProjectName()) ? houseDetail.getHouseProjectName() : "");
		houseString.append(StringUtils.isNotBlank(houseDetail.getPartitionName()) ? "(" + houseDetail.getPartitionName() + ")" : "");
		return houseString.toString();
	}

	private String getClientHouseNum(HouseInfoResponseVo houseDetail) {
		StringBuilder houseString = new StringBuilder();
		houseString.append(StringUtils.isNotBlank(houseDetail.getHousingNum()) ? houseDetail.getHousingNum() + "栋" : "");
		houseString.append(StringUtils.isNotBlank(houseDetail.getUnitNum()) ? houseDetail.getUnitNum() + "单元": "");
		houseString.append(StringUtils.isNotBlank(houseDetail.getRoomNum()) ? houseDetail.getRoomNum() + "室" : "");
		return houseString.toString();
	}

	public String transferOrderStatus(Integer orderStatus) {
		if(orderStatus == null) return "意向阶段";

		if(orderStatus == 13) return "意向阶段";
		if(orderStatus == 14) return "接触阶段";
		if(orderStatus == 15) return "定金阶段";
		if(orderStatus == 16) return "签约阶段";
		return "意向阶段";
	}

}

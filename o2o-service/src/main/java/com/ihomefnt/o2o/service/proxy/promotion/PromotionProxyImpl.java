/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:PromotionProxy.java
 */
package com.ihomefnt.o2o.service.proxy.promotion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.RedisUtil;
import com.ihomefnt.o2o.intf.domain.agent.dto.ZoneVo;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homecard.dto.BannerResponseVo;
import com.ihomefnt.o2o.intf.domain.mail.dto.ErrorEntity;
import com.ihomefnt.o2o.intf.domain.promotion.dto.*;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.MarketingActivityVo;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderResultDto;
import com.ihomefnt.o2o.intf.manager.constant.program.PromotionErrorEnum;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.mail.MailProxy;
import com.ihomefnt.o2o.intf.proxy.promotion.PromotionProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhang
 */
@Service
public class PromotionProxyImpl implements PromotionProxy {

    @Resource
    private ServiceCaller serviceCaller;
    @Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Autowired
    MailProxy mailProxy;
	@Autowired
	PersonalCenterProxy personalCenterProxy;

	private static final Logger LOG = LoggerFactory.getLogger(PromotionProxyImpl.class);
	//http://www.cmd5.com/hash.aspx  pass:newmarketingactivity salt:c1 hash:aijia
	public static final String FLAG_MARKETING_ACTIVITY = "6d26b2c5e47f12b5e12df1f1c20d9cb9";

	/**
	 * 参加促销活动
	 *
	 * @param param
	 * @return
	 */
	public Integer confirmJoinAct(JoinPromotionVo param) {
		ResponseVo<Boolean> responseVo = null;
		ErrorEntity entity = new ErrorEntity();
		// 邮件标题
		entity.setTitle("参加促销活动出错");
		// 收件人,注意是配置在wcm.t_dic的key_desc这个字段,如:SEND_BUILDING_MAIL
		entity.setWcmEmail("SEND_APP_MAIL");
		// 执行方法名
		entity.setZeusMethod(AladdinOrderServiceNameConstants.CONFIRM_JOIN_ACT);
		// 请求参数
		entity.setParam(param);
		try {
			responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.CONFIRM_JOIN_ACT, param,
					new TypeReference<ResponseVo<Boolean>>() {
			});
		} catch (Exception e) {
			entity.setErrorMsg(JsonUtils.obj2json(e.getStackTrace()));
			mailProxy.sendErrorMail(entity);
			return PromotionErrorEnum.SERVICE_FAIL.getCode();
		}
		if (responseVo == null) {
			return PromotionErrorEnum.SERVICE_FAIL.getCode();
		}
		// 输出结果
		entity.setResponseVo(responseVo);
		mailProxy.sendErrorMail(entity);
		return responseVo.getCode();
	}

	@Override
	public List<Long> checkIsCanJoinAct(Integer actCode, List<Integer> orderIds) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("actCode", actCode);
		paramMap.put("orderNumList", orderIds);
		ResponseVo<List<Long>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post("aladdin-promotion.order.checkIsCanJoinAct", paramMap,
					new TypeReference<ResponseVo<List<Long>>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public MarketingActivityVo queryActivityById(Map<String, Object> params) {
		ResponseVo<Map> responseVo;
		MarketingActivityVo mav = null;
		Integer hasOrder = (Integer) params.get("hasOrder");
		Integer userId = (Integer) params.get("userId");
		Integer actCode = (Integer) params.get("activityId");
		JSONObject params4ServiceCall = new JSONObject();
		params4ServiceCall.put("actCode", actCode);

		try {
			responseVo = strongSercviceCaller.post("aladdin-promotion.salePromotion.querySalePromotionById", params4ServiceCall, ResponseVo.class);
			if(userId != null || hasOrder != 0){
				List<AppMasterOrderResultDto> masterOrderList = personalCenterProxy.queryMasterOrderListByUserId(userId);
				if (!CollectionUtils.isEmpty(masterOrderList)) {
					List<Integer> orderIds = new ArrayList<>();

					for (AppMasterOrderResultDto masterOrderResultDto : masterOrderList) {
						orderIds.add(masterOrderResultDto.getMasterOrderId());
					}
					//查询订单是否可以参加活动
					List<Long> filterOrders = this.checkIsCanJoinAct(actCode, orderIds);

					Map<String, Object> data = JsonUtils.json2map(JsonUtils.obj2json(responseVo.getData()));
					data.put("orderIds", filterOrders);
					responseVo.setData(data);
				}
			}
			mav = jsonObject2MarketingActivityVo(
					JSONObject.parseObject(JSON.toJSONString(responseVo.getData())),
					(Boolean) params.get("isIn"),
					(Boolean) params.get("isAvailable"),
					(Integer) params.get("width"),
					null
			);
		} catch (Exception e) {
			return null;
		}
		return mav;
	}

	@Override
	public List<MarketingActivityVo> queryEffectiveActivitiesByUserId(Integer userId, Integer width) {
		ResponseVo<?> responseVo;
		List<MarketingActivityVo> mavs = null;
		try {
			List<OrderPromotionActDetailDto> promotionResultActDtos=new ArrayList<>();

			List<AppMasterOrderResultDto> masterOrderList = personalCenterProxy.queryMasterOrderListByUserId(userId);

			if (!CollectionUtils.isEmpty(masterOrderList)){
				//查询订单活动详情
				for (AppMasterOrderResultDto masterOrderResultDto:masterOrderList) {
					QueryPromotionResultDto actDto= this.queryPromotionByOrderNum(masterOrderResultDto.getMasterOrderId());

					OrderPromotionActDetailDto actDetailDto=new OrderPromotionActDetailDto();
					actDetailDto.setOrderNum(masterOrderResultDto.getMasterOrderId());
					actDetailDto.setPromotionResultDto(actDto);

					promotionResultActDtos.add(actDetailDto);
				}
			}
			mavs = getMarketingActivityVos(userId, width, promotionResultActDtos);
		} catch (Exception e) {
			return null;
		}
		return mavs;
	}

	@Override
	public List<MarketingActivityVo> queryEffectiveActivitiesByLocation(Map<String, Object> params) {
		ResponseVo<?> responseVo;
		List<MarketingActivityVo> mavs = null;
		try {
			responseVo = strongSercviceCaller.post("dolly-web.api-project.queryProjectByCityName", params.get("location"),
					ResponseVo.class);
			List<ProjectInfoVo> buildings = JSON.parseArray(JSON.toJSONString(responseVo.getData()), ProjectInfoVo.class);
			/*
			 * by cangjifeng 20180925
			 * 如果buildings为空，方法结束
			 */
			if(null == buildings || buildings.size()==0){
				return mavs;
			}
			// end 20180925
			List<Integer> zoneIds = buildings2ZoneIds(buildings);
			/*
			* by cangjifeng 20180925
			* 如果 zoneIds 为空方法结束
			 */
			if(null == zoneIds || 0==zoneIds.size()){
				return mavs;
			}
			// end 20180925
			responseVo = strongSercviceCaller.post("aladdin-promotion.salePromotion.promotionActByZoneIds", zoneIds,
					ResponseVo.class);

			List<JSONObject> activities = (List<JSONObject>) JSON.parse(JSON.toJSONString(responseVo.getData()));
			if(activities != null) {
				mavs = new ArrayList<>();
				for (JSONObject json : activities) {
					MarketingActivityVo marketingActivityVo = jsonObject2MarketingActivityVo(json, false, false, (Integer) params.get("width"), (Integer) params.get("userId"));
					if(marketingActivityVo != null) {
						marketingActivityVo.setUnAvailableDesc("您当前没有订单可以参加该活动。");
						mavs.add(marketingActivityVo);
					}
				}
			}

		} catch (Exception e) {
			/*
			* by cangjifeng 20180925
			* 步骤异常返回null
			 */
			return null;
			// end 20180925
		}

		return mavs;
	}

	@Override
	public List<MarketingActivityVo> queryEffectiveActivitiesByOrderId(Map<String, Object> params) {
		ResponseVo<?> responseVo = null;
		List<MarketingActivityVo> mavs = null;
		JSONObject params4ServiceCall = new JSONObject();
		params4ServiceCall.put("orderNum", params.get("orderId"));
		params4ServiceCall.put("orderSource", 2);
		try {
			responseVo = strongSercviceCaller.post("aladdin-promotion.promotion.queryPromotion", params4ServiceCall, ResponseVo.class);
			mavs = separatedActivities21List(JSON.parseObject(JSON.toJSONString(responseVo.getData())), (Integer) params.get("width"), null);
		} catch (Exception e) {
			return null;
		}
		return mavs;
	}

	private List<MarketingActivityVo> getMarketingActivityVos(Integer userId, Integer width, Object data) {
		List<MarketingActivityVo> mavs = null;
		List<JSONObject> activitiesWithOrderNumList = (List<JSONObject>) JSON.parse(JSON.toJSONString(data));
		if(activitiesWithOrderNumList != null && activitiesWithOrderNumList.size() >= 1) {
			mavs = new ArrayList<>();
			for(JSONObject jsonObject : activitiesWithOrderNumList) {
				Map activitiesMap = JSON.parseObject(JSON.toJSONString(jsonObject.get("promotionResultDto")));
				if(activitiesMap != null){
					mavs.addAll(separatedActivities21List(activitiesMap, width, userId));
				}
			}
		}
		List<MarketingActivityVo> mavRet = new ArrayList<>();

		if(mavs != null && mavs.size() >= 1) {
			for(MarketingActivityVo mav1 : mavs) {
				Integer actId1 = mav1.getActivityId();
				boolean addFlag = true;
				for(MarketingActivityVo mav2 : mavRet) {
					Integer actId2 = mav2.getActivityId();
					if(actId2.intValue() == actId1.intValue()) addFlag = false;
				}
				if(addFlag) mavRet.add(mav1);
			}
		}
		return mavRet;
	}

	@Override
	public JoinPromotionResultDto participateActivityByOrderId(Map<String, Object> params) {
		ResponseVo<JoinPromotionResultDto> responseVo = null;
		JSONObject params4ServiceCall = new JSONObject();
		params4ServiceCall.put("orderNum", params.get("orderId"));
		params4ServiceCall.put("orderSource", 2);
		List<Integer> actCodes = new ArrayList<>();
		actCodes.add((Integer) params.get("activityId"));
		params4ServiceCall.put("actCodes", actCodes);
		try {
			responseVo = strongSercviceCaller.post("aladdin-promotion.promotion.joinPromotion", params4ServiceCall,
                    new TypeReference<ResponseVo<JoinPromotionResultDto>>() {
                    });
		} catch (Exception e) {
			throw new BusinessException(MessageConstant.FAILED);
		}

		if (responseVo == null) {
			throw new BusinessException(MessageConstant.DDATA_GET_FAILED);
		}

		if (!responseVo.isSuccess()) {
			throw new BusinessException(responseVo.getCode(), responseVo.getMsg());
		}
		return responseVo.getData();
	}

	@Override
	public QueryPromotionResultDto queryPromotionByOrderNum(Integer masterOrderId) {
		Map<String, Object> params = new HashMap<>(2);
		params.put("orderNum", masterOrderId);
		params.put("orderSource", 2);

		ResponseVo<QueryPromotionResultDto> responseVo = strongSercviceCaller.post("aladdin-promotion.order.queryPromotion", params,
				new TypeReference<ResponseVo<QueryPromotionResultDto>>() {
				});
		if (responseVo == null || !responseVo.isSuccess()) {
			return null;
		}
		return responseVo.getData();
	}

	private MarketingActivityVo jsonObject2MarketingActivityVo(JSONObject json, boolean isIn, boolean isAvailable, Integer width, Integer userId) {
		if(json == null) return null;
		Map map = JSON.parseObject(JSON.toJSONString(json));
		Integer isShow = (Integer) map.get("isShow");
		//判断该活动是否可见
		if(isShow != null && isShow == 1) return null;
		Integer actType = (Integer) map.get("actType");
		if(actType == null) return null;
		if(actType != 1) return null;

		Integer activityId = null;
		if(map.get("actCode") != null) activityId = (Integer) map.get("actCode");
		if(map.get("id") != null) activityId = (Integer) map.get("id");

		MarketingActivityVo marketingActivityVo = new MarketingActivityVo();

		marketingActivityVo.setIsNewActivity(isNewActivity(userId, activityId));
//		if(isFiltered(userId, activityId)) return null; else marketingActivityVo.setIsNewActivity(true);
		marketingActivityVo.setActivityId(activityId);
		marketingActivityVo.setActivityName((String) map.get("appActName"));
		marketingActivityVo.setActivityDesc((String) map.get("customerServiceText"));
		marketingActivityVo.setUnAvailableDesc((String) map.get("canNotJoinDesc"));

		marketingActivityVo.setStartDate(timeStamp2FormatDate2((String) map.get("startTime")));
		marketingActivityVo.setEndDate(timeStamp2FormatDate2((String) map.get("endTime")));

		marketingActivityVo.setIsAvailable(isAvailable);
		marketingActivityVo.setIsIn(isIn);

		setActivityBanners(width, map, marketingActivityVo);

		marketingActivityVo.setH5Url("rn://activity/MarketingActivityDetailPage?activityId=" + activityId + "&isIn=" + isIn + "&isAvailable=" + isAvailable);
		marketingActivityVo.setOrders(getActivityOrders(map));

		JSONObject actLimitCondition = (JSONObject) map.get("actLimitConditionDto");
		if(actLimitCondition != null) {
			Map actLimitConditionMap = JSON.parseObject(JSON.toJSONString(actLimitCondition));
			List<Integer> orderStatus = (List<Integer>) actLimitConditionMap.get("orderStatus");
			marketingActivityVo.setAvailableStage(transferOrderStatus(orderStatus));
		}

        marketingActivityVo.setMutexPromotions((List<Integer>) map.get("mutexPromotions"));
		/*marketingActivityVo.setIcon(null);
		marketingActivityVo.setAvailableCondition(null);
		marketingActivityVo.setAvailableStage(null);
		marketingActivityVo.setBuildings(null);
		marketingActivityVo.setMutexActivityName(null);*/
		return marketingActivityVo;
	}

	private List<HouseOrderBriefInfoVo> getActivityOrders(Map activityRec) {
		List<Integer> orderIds = (List<Integer>) activityRec.get("orderIds");
		if(orderIds == null || orderIds.size() == 0) return null;

		List<HouseOrderBriefInfoVo> orders = new ArrayList<>();
		for(Integer orderId : orderIds) {
			HouseOrderBriefInfoVo houseOrderBriefInfoVo = new HouseOrderBriefInfoVo();
			houseOrderBriefInfoVo.setOrderId(orderId);
			orders.add(houseOrderBriefInfoVo);
		}

		return orders;
	}

	private boolean isNewActivity(Integer userId, Integer activityId) {
		if(userId == null) return false;
		Jedis jedis = RedisUtil.getResource();
//		Set<String> cachedActivities = jedis.smembers(FLAG_MARKETING_ACTIVITY + userId);
		if(jedis.sismember(FLAG_MARKETING_ACTIVITY + userId, String.valueOf(activityId))) return false;
		jedis.sadd(FLAG_MARKETING_ACTIVITY + userId, String.valueOf(activityId));
		return true;
	}

	private boolean isFiltered(Integer userId, Integer activityId) {
		if(userId == null) return false;
		Jedis jedis = RedisUtil.getResource();
		if(jedis.sismember(FLAG_MARKETING_ACTIVITY + userId, String.valueOf(activityId))) return true;
		jedis.sadd(FLAG_MARKETING_ACTIVITY + userId, String.valueOf(activityId));
		return false;
	}

	private void setActivityBanners(Integer width, Map activityRec, MarketingActivityVo marketingActivityVo) {
		List<JSONObject> banners = (List<JSONObject>) JSON.parse(JSON.toJSONString(activityRec.get("actBasicAppPicturesList")));
		if(banners == null || banners.size() == 0) return;
		List<BannerResponseVo> bannerResponseVos = new ArrayList<>();
		for(JSONObject banner : banners) {
			Map bannerMap = JSON.parseObject(banner.toJSONString());
			if(bannerMap == null) continue;
			String pictureUrl = (String) bannerMap.get("pictureUrl");

			BannerResponseVo bannerResponseVo = bannerConverter(pictureUrl, width);
			if(bannerResponseVo == null) continue;

			if(bannerMap.get("picType") != null && (Integer)bannerMap.get("picType") == 1) {
				marketingActivityVo.setHeadBanner(bannerResponseVo);
			} else {
				if(bannerMap.get("isFirst") != null && (Integer)bannerMap.get("isFirst") == 1) {
					bannerResponseVos.add(0, bannerResponseVo);
				} else {
					bannerResponseVos.add(bannerResponseVo);
				}
			}
			marketingActivityVo.setBanners(bannerResponseVos);
		}
	}

	public String transferOrderStatus(Integer orderStatus) {
		if(orderStatus == null) return null;

		if(orderStatus == 13) return "意向阶段";
		if(orderStatus == 14) return "接触阶段";
		if(orderStatus == 15) return "定金阶段";
		if(orderStatus == 16) return "签约阶段";
		return null;
	}

	public String transferOrderStatus(List<Integer> orderStatuses) {
		if(orderStatuses == null || orderStatuses.size() == 0) return null;
		String result = "";
		for(Integer status : orderStatuses) {
			result += transferOrderStatus(status) + ",";
		}
		if(result.length() >= 1) return result.substring(0, result.length() - 1);
		return result;

	}

	private BannerResponseVo bannerConverter(String bannerUrl, Integer deviceWidth) {
		if(bannerUrl == null || "".equals(bannerUrl)) return null;
		BannerResponseVo bannerResponseVo = new BannerResponseVo();
		String imageUrl;
		Map<String, Object> imageSize;
		float height = 0f;
		float width = 0f;

		if(deviceWidth != null) {
			imageUrl = QiniuImageUtils.compressImageAndSamePicTwo(bannerUrl, deviceWidth, -1);
			imageSize = QiniuImageUtils.getImageSizeByType(imageUrl, "|imageInfo", serviceCaller);
		} else {
			imageUrl = bannerUrl;
			imageSize = QiniuImageUtils.getImageSizeByType(imageUrl, "?imageInfo", serviceCaller);
		}

		if (imageSize.get("height") != null) {
			height = Float.parseFloat(imageSize.get("height") + "");
		}
		if (imageSize.get("width") != null) {
			width = Float.parseFloat(imageSize.get("width") + "");
		}

		bannerResponseVo.setWidth(width);
		bannerResponseVo.setHeight(height);
		bannerResponseVo.setImgUrl(imageUrl);
		if (width > 0 && height > 0) {
			bannerResponseVo.setRatioHW(BigDecimal.valueOf(height).divide(BigDecimal.valueOf(width)).floatValue());
			bannerResponseVo.setRatioWH(BigDecimal.valueOf(width).divide(BigDecimal.valueOf(height)).floatValue());
		}
		return bannerResponseVo;
	}

	private List<MarketingActivityVo> separatedActivities21List(Map resultMap, Integer width, Integer userId) {
		if(resultMap == null) return null;

		List<MarketingActivityVo> mavs = new ArrayList<>();
		List<JSONObject> joinedActs = (List<JSONObject>) resultMap.get("joinedActs");
		List<JSONObject> canJoinActs  = (List<JSONObject>) resultMap.get("canJoinActs");
		List<JSONObject> canNotJoinActs  = (List<JSONObject>) resultMap.get("canNotJoinActs");
		if(joinedActs != null && joinedActs.size() >= 1) {
			for(JSONObject json: joinedActs){
				MarketingActivityVo marketingActivityVo = jsonObject2MarketingActivityVo(json, true, false, width, userId);
				if(marketingActivityVo != null) {
					mavs.add(marketingActivityVo);
				}
			}
		}
		if(canJoinActs != null && canJoinActs.size() >= 1) {
			for(JSONObject json : canJoinActs) {
				MarketingActivityVo marketingActivityVo = jsonObject2MarketingActivityVo(json, false, true, width, userId);
				if(marketingActivityVo != null) {
					mavs.add(marketingActivityVo);
				}
			}
		}
		if(canNotJoinActs != null && canNotJoinActs.size() >= 1) {
			for(JSONObject json : canNotJoinActs) {
				MarketingActivityVo marketingActivityVo = jsonObject2MarketingActivityVo(json, false, false, width, userId);
				if(marketingActivityVo != null) {
					mavs.add(marketingActivityVo);
				}
			}
		}

		if(mavs != null && mavs.size() >= 1) {
		    for(MarketingActivityVo mav1 : mavs) {
                boolean isIn = mav1.getIsIn();
                if(isIn) {
                    List<Integer> mutexPromotions = mav1.getMutexPromotions();
                    for(MarketingActivityVo mav2 : mavs) {
                        if(mav2.getActivityId().intValue() == mav1.getActivityId().intValue()) continue;
                        if(mutexPromotions != null && mutexPromotions.size() >= 1) {
                            for(Integer mutex : mutexPromotions) {
                                if(mutex.intValue() == mav2.getActivityId().intValue()) {
                                    mav2.setIsAvailable(false);
                                    mav2.setUnAvailableDesc("您已参加了活动\"" + mav1.getActivityName() + "\",无法参加该活动。");
                                }
                            }
                        }
                    }
                }

            }
        }


		return mavs;
	}

	private List<Integer> buildings2ZoneIds (List<ProjectInfoVo> buildings) {
		if(buildings == null || buildings.size() == 0) return null;
		List<Integer> zoneIds = new ArrayList<>();
		for(ProjectInfoVo projectInfoVo : buildings) {
			List<ZoneVo> zoneVos = projectInfoVo.getZoneList();
			if (zoneVos != null && zoneVos.size() >= 1) {
				for(ZoneVo zoneVo :zoneVos) zoneIds.add(zoneVo.getId());
			}
		}
		return zoneIds;
	}

	private String timeStamp2FormatDate(Long timeStamp) {
		if(timeStamp == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(timeStamp));

	}

	private String timeStamp2FormatDate2(String timeStamp) {
		if(timeStamp == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Long timeLong = Long.parseLong(timeStamp);
			return sdf.format(new Date(timeLong));
		} catch (RuntimeException e) {
			LOG.info("timeStamp2FormatDate2 Exception timeStamp:{}", timeStamp);
		}
		try {
			return sdf.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timeStamp));
		} catch (ParseException e) {
			LOG.info("timeStamp2FormatDate2 ParseException timeStamp:{}", timeStamp);
		}
		return null;
	}

}

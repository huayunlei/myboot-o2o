/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月13日
 * Description:ArtistServiceImpl.java 
 */
package com.ihomefnt.o2o.service.service.artist;

import com.ihomefnt.cms.utils.ModelMapperUtil;
import com.ihomefnt.common.util.DateUtils;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.RedisUtil;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistDto;
import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistListDto;
import com.ihomefnt.o2o.intf.domain.artist.dto.ArtistResponseVo;
import com.ihomefnt.o2o.intf.domain.artist.dto.DNAFeeResponseVo;
import com.ihomefnt.o2o.intf.domain.artist.dto.DesignFeeResponseVo;
import com.ihomefnt.o2o.intf.domain.artist.dto.RegisterRequestVo;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.*;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNABaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNABaseInfoVo;
import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response.KuaidiProductDeliveryResponseVo;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDetailDtoVo;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDtoVo;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderInfoSearchDto;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderListRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.LogisticListResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.LogisticResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.artist.ApplyEnum;
import com.ihomefnt.o2o.intf.manager.constant.artist.ArtistLoginErrorEnum;
import com.ihomefnt.o2o.intf.manager.constant.artist.ArtistRegisterErrorEnum;
import com.ihomefnt.o2o.intf.manager.constant.dic.DicConstant;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderStateEnum;
import com.ihomefnt.o2o.intf.manager.constant.user.UserRoleConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.AgeUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.art.StarProductProxy;
import com.ihomefnt.o2o.intf.proxy.artist.ArtistProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import com.ihomefnt.o2o.intf.service.art.ArtService;
import com.ihomefnt.o2o.intf.service.artcomment.ArtCommentService;
import com.ihomefnt.o2o.intf.service.artist.ArtistService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.ordersnapshot.OrderSnapshotService;
import com.ihomefnt.oms.trade.order.enums.OrderState;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhang
 */
@Service
public class ArtistServiceImpl implements ArtistService {
	private static final Logger LOG = LoggerFactory.getLogger(ArtistServiceImpl.class);
			
	@Autowired
	private ArtistProxy artistProxy;

	@Autowired
	private UserProxy userProxy;

	@Autowired
	private DicProxy dicProxy;

	@Autowired
	private HomeCardBossProxy homeCardBossProxy;
	
	@Autowired
	OrderProxy orderProxy;

	@Autowired
	private ArtService artService;
	
	@Autowired
	ArtCommentService artCommentService;
	
	@Autowired
	OrderService orderService;

	@Autowired
	AreaService areaService;

	@Autowired
	OrderSnapshotService orderSnapshotService;
	
	@Autowired
    private StarProductProxy starProductProxy;
    
	private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void register(ArtistRegisterRequest request) {
		if (request == null) {
			throw new BusinessException(ArtistRegisterErrorEnum.DATA_EMPTY.getCode(), ArtistRegisterErrorEnum.DATA_EMPTY.getMsg());
		}
		// 姓名
		if (StringUtils.isBlank(request.getUserName())) {
			throw new BusinessException(ArtistRegisterErrorEnum.NAME_ERROR.getCode(), ArtistRegisterErrorEnum.NAME_ERROR.getMsg());
		}
		// 手机
		if (StringUtils.isBlank(request.getMobileNum()) || request.getMobileNum().trim().length() != 11) {
			throw new BusinessException(ArtistRegisterErrorEnum.PHONE_ERROR.getCode(), ArtistRegisterErrorEnum.PHONE_ERROR.getMsg());
		}
		// 验证码
		if (StringUtils.isBlank(request.getSmsCode())) {
			throw new BusinessException(ArtistRegisterErrorEnum.SMS_ERROR.getCode(), ArtistRegisterErrorEnum.SMS_ERROR.getMsg());
		}
		// openId
		if (StringUtils.isBlank(request.getOpenId()) || request.getOpenId().equalsIgnoreCase("undefined")) {
			throw new BusinessException(ArtistRegisterErrorEnum.OPEN_ID_ERROR.getCode(), ArtistRegisterErrorEnum.OPEN_ID_ERROR.getMsg());
		}
		RegisterRequestVo param = new RegisterRequestVo();
		param.setImage(request.getImage());
		param.setMobile(request.getMobileNum());
		param.setName(request.getUserName());
		param.setOpenId(request.getOpenId());
		param.setOsType(request.getOsType());
		param.setSmsCode(request.getSmsCode());
		param.setSource(1);

		// 申请注册
		Long resultCode = artistProxy.registerDesigner(param);

		String msg = ArtistRegisterErrorEnum.getMsg(resultCode);
		// 失败:默认值
		if (resultCode.intValue() != ArtistRegisterErrorEnum.OK.getCode().intValue() && StringUtils.isBlank(msg)) {
			throw new BusinessException(resultCode, ArtistRegisterErrorEnum.DEFAULT_ERROR.getMsg());
		} else if (resultCode.intValue() != ArtistRegisterErrorEnum.OK.getCode().intValue()) {
			throw new BusinessException(resultCode, msg);
		}
	}

	@Override
	public ArtistLoginResponse login(ArtistLoginRequest request) {
		if (request == null) {
			throw new BusinessException(ArtistLoginErrorEnum.DATA_EMPTY.getCode(), ArtistLoginErrorEnum.DATA_EMPTY.getMsg());
		}
		// 手机
		if (StringUtils.isBlank(request.getMobileNum()) || request.getMobileNum().trim().length() != 11) {
			throw new BusinessException(ArtistLoginErrorEnum.PHONE_ERROR.getCode(), ArtistLoginErrorEnum.PHONE_ERROR.getMsg());
		}
		// 验证码
		if (StringUtils.isBlank(request.getSmsCode())) {
			throw new BusinessException(ArtistLoginErrorEnum.SMS_ERROR.getCode(), ArtistLoginErrorEnum.SMS_ERROR.getMsg());
		}
		UserDto userVo = userProxy.getUserByMobile(request.getMobileNum());
		// 用户是否存在
		if (userVo == null) {
			throw new BusinessException(ArtistLoginErrorEnum.USER_NOT_EXIST_ERROR.getCode(), ArtistLoginErrorEnum.USER_NOT_EXIST_ERROR.getMsg());
		}
		List<RoleDto> roles = userVo.getRoles();
		// 用户有没有设计师权限
		boolean roleTag = false;
		if (CollectionUtils.isNotEmpty(roles)) {
			for (RoleDto role : roles) {
				String code = role.getCode();
				if (StringUtils.isNotBlank(code) && UserRoleConstant.DESIGNER.equals(code)) {
					roleTag = true;
					break;
				}
			}
		}
		if (!roleTag) {
			ArtistResponseVo vo = artistProxy.queryDesignerByUserId(userVo.getId());
			if (vo == null || vo.getStatus() == null) {
				throw new BusinessException(ArtistLoginErrorEnum.USER_NOT_EXIST_ERROR.getCode(), ArtistLoginErrorEnum.USER_NOT_EXIST_ERROR.getMsg());
			} else if (vo.getStatus() == 1) {
				throw new BusinessException(ArtistLoginErrorEnum.USER_NOT_APPROVED_ERROR.getCode(), ArtistLoginErrorEnum.USER_NOT_APPROVED_ERROR.getMsg());
			} else if (vo.getStatus() == 3) {
				throw new BusinessException(ArtistLoginErrorEnum.USER_APPROVED_FAILED_ERROR.getCode(), ArtistLoginErrorEnum.USER_APPROVED_FAILED_ERROR.getMsg());
			}
		}

		SmsLoginUserParamVo loginUser = new SmsLoginUserParamVo();
		loginUser.setMobile(request.getMobileNum());
		loginUser.setSmsCode(request.getSmsCode());
		Integer osType = request.getOsType();
		if (osType == null) {
			osType = 0;
		}
		int source = 0;
		if (osType == 1) {
			source = 2;
		} else if (osType == 2) {
			source = 3;
		} else if (osType == 3) {
			source = 4;
		} else if (osType == 4) {
			source = 5;
		}
		loginUser.setSource(source);
		LoginResultVo loginResultDto = userProxy.loginBySmsCode(loginUser);
		// 接口异常
		if (loginResultDto == null || loginResultDto.getCode() == null) {
			throw new BusinessException(ArtistLoginErrorEnum.SYS_ERROR.getCode(), ArtistLoginErrorEnum.SYS_ERROR.getMsg());
		}
		Long resultCode = loginResultDto.getCode().longValue();
		String msg = ArtistLoginErrorEnum.getMsg(resultCode);
		if (resultCode.intValue() != ArtistLoginErrorEnum.OK.getCode().intValue()) {
			// 失败:默认值
			if (StringUtils.isBlank(msg)) {
				msg = ArtistLoginErrorEnum.DEFAULT_ERROR.getMsg();
			}
			throw new BusinessException(resultCode, msg);
		}

		String token = loginResultDto.getToken();
		ArtistLoginResponse response = new ArtistLoginResponse();
		response.setAccessToken(token);
		response.setMobile(request.getMobileNum());
		return response;
	}

	@Override
	public ArtistConfigResponse config(String accessToken) {
		String serviceRule = "";
		DicDto dicVo = dicProxy.queryDicByKey(DicConstant.ART_SERVICE_RULE);
		if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
			serviceRule = dicVo.getValueDesc();
		}
		ArtistConfigResponse response = new ArtistConfigResponse();
		if (StringUtils.isNotBlank(accessToken)) {
			UserDto userDto = userProxy.getUserByToken(accessToken);
			if (userDto == null || userDto.getId() == null) {
				return null;
			}
			Integer userId = userDto.getId();
			DNABaseInfoResponseVo dnaBaseInfoListResponse = homeCardBossProxy.getProductByCondition(userId, null, null, null,
					null, 1, 2);
			if (dnaBaseInfoListResponse != null && dnaBaseInfoListResponse.getTotalCount() == 1) {
				DNABaseInfoVo vo = dnaBaseInfoListResponse.getSearchResultList().get(0);
				if (vo != null && vo.getId() != null) {
					response.setDnaId(vo.getId());
				}
			}
		}
		response.setServiceRule(serviceRule);
		return response;
	}

	@Override
	public DesignFeeResponseVo asset(HttpBaseRequest request) {
		if (request == null || StringUtils.isBlank(request.getAccessToken())) {
			return null;
		}
		HttpUserInfoRequest userVo = request.getUserInfo();
		if (userVo == null || userVo.getId() == null) {
			return null;
		}
		
		DesignFeeResponseVo response = new DesignFeeResponseVo();
		DesignFeeResponseVo designFeeResponseVo = artistProxy.queryDesignFee(userVo.getId());
		if(designFeeResponseVo != null && designFeeResponseVo.getUserId() != null){
			List<DNAFeeResponseVo> dnaFeeListResponse = new ArrayList<DNAFeeResponseVo>();
			
			response.setUserId(designFeeResponseVo.getUserId());
			response.setBalance(designFeeResponseVo.getBalance());
			response.setSumAmount(designFeeResponseVo.getSumAmount());
			List<DNAFeeResponseVo> dnaFeeList = designFeeResponseVo.getDnaFeeList();// DNA收益列表,
			if(CollectionUtils.isNotEmpty(dnaFeeList)){
				for (DNAFeeResponseVo dnaFeeResponseVo : dnaFeeList) {
					if(dnaFeeResponseVo.getDnaFeeAmount() != null && !dnaFeeResponseVo.getDnaFeeAmount().equals(BigDecimal.ZERO) && dnaFeeResponseVo.getDnaFeeAmount().compareTo(BigDecimal.ZERO) != 0){
						dnaFeeListResponse.add(dnaFeeResponseVo);
					}
				}
			}
			response.setDnaFeeList(dnaFeeListResponse);
			response.setDnaFeeDetailList(designFeeResponseVo.getDnaFeeDetailList());// DNA收益明细列表
			response.setDnaFeePayList(designFeeResponseVo.getDnaFeePayList());
		}
		
		return response;
	}

	@Override
	public Integer applyCash(ArtistApplyCashRequest request) {
		if (request == null) {
			return ApplyEnum.USER_ERROR.getCode();
		}
		HttpUserInfoRequest userVo = request.getUserInfo();
		if (userVo == null || userVo.getId() == null) {
			return ApplyEnum.USER_ERROR.getCode();
		}
		return artistProxy.applyWithdrawal(userVo.getId(), request.getApplyCashMoney());
	}

	@Override
	public StarArtistListDto getStarArtistList(StarArtistListRequest request) {
		HttpUserInfoRequest userVo = request.getUserInfo();
		if (userVo == null || userVo.getId() == null) {
			throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.USER_NOT_LOGIN);
		}
		request.setUserId(userVo.getId());
		// 查询小星星列表
		StarArtistListDto list = userProxy.getStarArtistList(request);
        			
		return list;
	}

	@Override
	public List<StarArtistDto> getStarArtistByName(StarArtistByNameRequest request) {
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		// 根据姓名查询小星星艺术家
		List<MemberDto> list = userProxy.getUserInfoByNameAndCode(request.getArtistName(), "15");
		List<StarArtistDto> data = new ArrayList<>();
		for (MemberDto vo : list) {
			StarArtistDto dto = ModelMapperUtil.strictMap(vo, StarArtistDto.class);
			dto.setUserName(vo.getUsername());
			if (null != dto.getBirthDate()) {
				dto.setAge(AgeUtil.getAge(DateUtils.dateToString(dto.getBirthDate(), "yyyy-MM-dd")));
			}
			data.add(dto);
		}
		return data;
	}
	

	@Override
	public StarUserLoginResponse registerByWeChatApplet(StarUserRegisterRequest request) {
		// 小程序注册
		WechatAppletRegisterParamVo vo = ModelMapperUtil.strictMap(request, WechatAppletRegisterParamVo.class);
		vo.setSource(9);
		//查询小星星用户的openid
		String agentSessionKey = "sessionkey_star_" + request.getLoginSessionKey();
		List<String> sessionList = RedisUtil.getList(agentSessionKey);
		if(CollectionUtils.isNotEmpty(sessionList)){
			vo.setWechatId(sessionList.get(1));
		}else{
			LOG.info("registerByWeChatApplet WechatId is null");
			return null;
		}
		UserIdResultDto userResult = userProxy.registerByWeChatApplet(vo);
		
		if (null != userResult && StringUtils.isNotEmpty(userResult.getToken())) {
			UserDto userVo = userProxy.getUserByToken(userResult.getToken());
			if (null != userVo) {
				StarUserLoginResponse userInfo = new StarUserLoginResponse();
				MemberDto member = userVo.getMember();
				if(null != member){
					userInfo = ModelMapperUtil.strictMap(
							userVo.getMember(), StarUserLoginResponse.class);
				}
				userInfo.setMobile(userVo.getMobile());
				userInfo.setUserName(userVo.getUsername());
				userInfo.setAccessToken(userResult.getToken());
				return userInfo;
			}
		}
		
		return null;
	}

	@Override
	public PageModel<StarOrderResponse> getMyStarOrderList(HttpOrderListRequest orderRequest) {
		HttpUserInfoRequest userDto = orderRequest.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		
		OrderInfoSearchDto condition =new OrderInfoSearchDto();
		condition.setOrderType(15);
		condition.setUserId(userDto.getId());
		condition.setMark(1);
		if(orderRequest.getPageNo() > 0){
			condition.setPageNo(orderRequest.getPageNo());
		}else{
			condition.setPageNo(1);
		}
		if(orderRequest.getPageSize() > 0){
			condition.setPageSize(orderRequest.getPageSize());
		}else{
			condition.setPageSize(100);
		}
		
		PageModel pageModel = orderProxy.queryOrderInfo(condition);
		if (null != pageModel) {
			PageModel model = new PageModel();
			@SuppressWarnings("unchecked")
			List<OrderDtoVo> orderDtoList = pageModel.getList();
			if (CollectionUtils.isEmpty(orderDtoList) ) {
				throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.QUERY_EMPTY);
			}

			List<StarOrderResponse> orList = new ArrayList<StarOrderResponse>();
			for (OrderDtoVo orderDto : orderDtoList) {
				Integer orderType = orderDto.getOrderType();
				LOG.info("orderType:  "+orderType+",orderNum:"+orderDto.getOrderNum());
				StarOrderResponse or = setOrderInfo(orderDto, orderRequest);
				orList.add(or);
			}

			model.setList(orList);
			model.setPageNo(pageModel.getPageNo());
			model.setPageSize(pageModel.getPageSize());
			model.setTotalPages(pageModel.getTotalPages());
			model.setTotalRecords(pageModel.getTotalRecords());
			return model;
		}
		return null;
	}

	/**
	 * 设置订单信息
	 * @param orderDto
	 * @param orderRequest
	 */
	private StarOrderResponse setOrderInfo(OrderDtoVo orderDto, HttpOrderListRequest orderRequest) {
		Integer orderType = orderDto.getOrderType();
		StarOrderResponse or = new StarOrderResponse();
		or.setOrderId(orderDto.getOrderId());
		or.setOrderType(orderDto.getOrderType());
		or.setOrderNum(orderDto.getOrderNum());
		or.setProductCount(orderDto.getProductCount());
		or.setTotalPrice(orderDto.getTotalPrice());
		or.setActualPayMent(orderDto.getActualPayMent());
		//订单状态
		or.setState(orderDto.getState());
		or.setStateDesc(OrderStateEnum.getName(orderDto.getState()));
		or.setCommentFlag(false);
		
		Integer state = orderDto.getState();
		Integer orderId = orderDto.getOrderId();
		if (state != null && orderType != null && orderType == OrderConstant.ORDER_TYPE_STAR_ART
				&& state == OrderConstant.ORDER_OMSSTATUS_FINISH) {
			Integer productCommentCount = artCommentService.getCommentCountByOrderId(orderId);
			if(productCommentCount > 0){
				//订单状态
				or.setCommentFlag(true);
			}
		}
		List<StarOrderDetailResp> orderDetailRespList = new ArrayList<StarOrderDetailResp>();
		//订单商品列表
		List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
		
		if (null != orderDetailDtoList) {
			List<Integer> skuIds = new ArrayList<Integer>();//SKU ID集合
			List<Integer> workIds = new ArrayList<Integer>();//原始作品 ID集合
			orderDetailDtoList.forEach(e -> {
				skuIds.add(e.getFidProduct());
				workIds.add(e.getFidStarArt());
			});
			
			//查询SKU信息
			Map<Integer, AppArtStarSku> skuMap = new HashMap<Integer, AppArtStarSku>();
			if(CollectionUtils.isNotEmpty(skuIds)){
				List<AppArtStarSku> skArtStarSkus = starProductProxy.getAllStarSkuListByIds(skuIds);
				if(CollectionUtils.isNotEmpty(skArtStarSkus)){
					skArtStarSkus.forEach(e -> {
						if(!skuMap.containsKey(e.getSkuId().intValue())){
							skuMap.put(e.getSkuId().intValue(), e);
						}
					});
				}
			}

			//查询原始作品信息
			Map<Integer, ArtStarWork> workMap = new HashMap<Integer, ArtStarWork>();
			if(CollectionUtils.isNotEmpty(workIds)){
				List<ArtStarWork> artStarWorks = starProductProxy.getAllStarWorksListByWorkIds(workIds);
				if(CollectionUtils.isNotEmpty(artStarWorks)){
					artStarWorks.forEach(e -> {
						if(!workMap.containsKey(e.getArtId())){
							workMap.put(e.getArtId().intValue(), e);
						}
					});
				}
			}
			
			for (OrderDetailDtoVo orderDetailDto : orderDetailDtoList) {
				StarOrderDetailResp orderDetailResp = new StarOrderDetailResp();
				Integer productId = orderDetailDto.getFidProduct();
				orderDetailResp.setFidProduct(productId);
				orderDetailResp.setProductAmount(orderDetailDto.getProductAmount());
				orderDetailResp.setProductAmountPrice(orderDetailDto.getProductAmountPrice());
				orderDetailResp.setMarkWord(orderDetailDto.getRemarks());
				if (null != productId) {
					//SKU商品信息
					AppArtStarSku appArtStarSku = skuMap.get(productId);
					if(null != appArtStarSku){
						orderDetailResp.setProductName(appArtStarSku.getSkuName());
						orderDetailResp.setProductPrice(appArtStarSku.getPrice());
						if (StringUtils.isNotBlank(appArtStarSku.getImgUrl())) {
							Integer width = orderRequest.getWidth();
							String headImg = appArtStarSku.getImgUrl();
							if (width != null) {
								width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
								headImg = QiniuImageUtils.compressImageAndDiffPic(headImg, width, -1);
								LOG.info(" QiniuImageUtils.compressImageAndDiffPic :" + headImg);
							}
							orderDetailResp.setHeadImage(headImg);
						}
						orderDetailResp.setGoodName(appArtStarSku.getProductName());
						orderDetailResp.setGoodId(appArtStarSku.getProductId().intValue());
						orderDetailResp.setSelectAttr(appArtStarSku.getAttrStr());
					}
				}
				Integer fidStarWorkId = orderDetailDto.getFidStarArt();
				if(null != fidStarWorkId){
					//原始作品信息
					ArtStarWork artStarWork = workMap.get(fidStarWorkId);
					if(null != artStarWork){
						orderDetailResp.setOriginalWorkId(artStarWork.getArtId().intValue());
						orderDetailResp.setOriginalWorkName(artStarWork.getArtName());
					}
				}
				orderDetailRespList.add(orderDetailResp);
			}
		}
		or.setOrderDetailRespList(orderDetailRespList);
		return or;
	}
	
	@Override
	public StarOrderResponse getMyStarOrderDetail(HttpOrderRequest orderRequest) {
		HttpUserInfoRequest userDto = orderRequest.getUserInfo();
		if (null == userDto || userDto.getId() == null) {
			throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		int orderId = orderRequest.getOrderId().intValue();
		try {
			OrderDtoVo orderDto = orderService.queryOrderDetail(orderId);
			if (null == orderDto) {
				throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.DDATA_GET_FAILED);
			}
			Integer fidArea = orderDto.getFidArea();
			Date createTime = orderDto.getCreateTime();
			BigDecimal actualPayMent = orderDto.getActualPayMent();
			StarOrderResponse or = new StarOrderResponse();
			or.setOrderId(orderDto.getOrderId());
			or.setOrderType(orderDto.getOrderType());
			or.setOrderNum(orderDto.getOrderNum());
			or.setProductCount(orderDto.getProductCount());
			//订单状态
			or.setState(orderDto.getState());
			if(orderDto.getState()!=null){
				or.setStateDesc(OrderStateEnum.getName(orderDto.getState()));
			}
			or.setActualPayMent(actualPayMent);
			or.setReceiverName(orderDto.getReceiverName());
			or.setReceiverTel(orderDto.getReceiverTel());
			or.setTotalPrice(orderDto.getTotalPrice());

			Integer state = orderDto.getState();
			Integer orderType = orderDto.getOrderType();
			if (state != null && orderType != null && orderType == OrderConstant.ORDER_TYPE_STAR_ART
					&& state == OrderConstant.ORDER_OMSSTATUS_FINISH) {
				Integer productCommentCount = artCommentService.getCommentCountByOrderId(orderId);
				if(productCommentCount > 0){
					//订单评论状态
					or.setCommentFlag(true);
				}
			}

			//物流信息
			LOG.info("getStarOrderDetail orderService.queryOrderDeliveryInfoByOrderId param:" + JsonUtils.obj2json(orderId));
			LogisticListResponse  logisticListResponse = orderService.queryOrderDeliveryInfoByOrderId(orderId, orderRequest.getWidth());
			LOG.info("getStarOrderDetail orderService.queryOrderDeliveryInfoByOrderId response:" + JsonUtils.obj2json(logisticListResponse));
			if(logisticListResponse != null && CollectionUtils.isNotEmpty(logisticListResponse.getLogisticList())){
				LogisticResponse logisticDetail = logisticListResponse.getLogisticList().get(0);
				if(null != logisticDetail && CollectionUtils.isNotEmpty(logisticDetail.getData())){
					KuaidiProductDeliveryResponseVo data = logisticDetail.getData().get(0);
					or.setLogisticsDesc(data.getContext());
				}
			}

			if (null != createTime) {
				String createDateStr = dayFormat.format(createTime);
				or.setCreateDateStr(createDateStr);
			}

			StringBuilder receiverAddress = new StringBuilder();
			AreaDto area = areaService.getArea(fidArea);
			if (null != area) {
				String countyName = area.getAreaName();
				String cityName = "";
				String provinceName = "";
				area = areaService.getArea(area.getParentId());
				if (null != area) {
					cityName = area.getAreaName();
					area = areaService.getArea(area.getParentId());
					if (null != area) {
						provinceName = area.getAreaName();
					}
				}
				if(orderDto.getCustomerAddress()!=null&&!orderDto.getCustomerAddress().contains(provinceName)){
					receiverAddress.append(provinceName);
				}
				if(orderDto.getCustomerAddress()!=null&&!orderDto.getCustomerAddress().contains(cityName)){
					receiverAddress.append(cityName);
				}
				if(orderDto.getCustomerAddress()!=null&&!orderDto.getCustomerAddress().contains(countyName)){
					receiverAddress.append(countyName);
				}
			}

			receiverAddress.append(orderDto.getCustomerAddress());
			if(StringUtils.isNotBlank(orderDto.getBuildingNo())){
				receiverAddress.append(orderDto.getBuildingNo());
			}
			if(StringUtils.isNotBlank(orderDto.getHouseNo())){
				if(StringUtils.isNotBlank(orderDto.getBuildingNo())){
					receiverAddress.append("-");
				}
				receiverAddress.append(orderDto.getHouseNo());
			}
			or.setReceiverAddress(receiverAddress.toString());

			List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
			List<StarOrderDetailResp> orderDetailRespList = new ArrayList<StarOrderDetailResp>();

			if (null != orderDetailDtoList) {
				List<Integer> skuIds = new ArrayList<Integer>();//SKU ID集合
				List<Integer> workIds = new ArrayList<Integer>();//原始作品 ID集合
				orderDetailDtoList.forEach(e -> {
					skuIds.add(e.getFidProduct());
					workIds.add(e.getFidStarArt());
				});

				//查询SKU信息
				Map<Integer, AppArtStarSku> skuMap = new HashMap<Integer, AppArtStarSku>();
				if(CollectionUtils.isNotEmpty(skuIds)){
					List<AppArtStarSku> skArtStarSkus = starProductProxy.getAllStarSkuListByIds(skuIds);
					if(CollectionUtils.isNotEmpty(skArtStarSkus)){
						skArtStarSkus.forEach(e -> {
							if(!skuMap.containsKey(e.getSkuId().intValue())){
								skuMap.put(e.getSkuId().intValue(), e);
							}
						});
					}
				}

				//查询原始作品信息
				Map<Integer, ArtStarWork> workMap = new HashMap<Integer, ArtStarWork>();
				if(CollectionUtils.isNotEmpty(workIds)){
					List<ArtStarWork> artStarWorks = starProductProxy.getAllStarWorksListByWorkIds(workIds);
					if(CollectionUtils.isNotEmpty(artStarWorks)){
						artStarWorks.forEach(e -> {
							if(!workMap.containsKey(e.getArtId())){
								workMap.put(e.getArtId().intValue(), e);
							}
						});
					}
				}

				for (OrderDetailDtoVo orderDetailDto : orderDetailDtoList) {
					StarOrderDetailResp orderDetailResp = new StarOrderDetailResp();
					Integer productId = orderDetailDto.getFidProduct();
					orderDetailResp.setFidProduct(productId);
					orderDetailResp.setProductAmount(orderDetailDto.getProductAmount());
					orderDetailResp.setProductAmountPrice(orderDetailDto.getProductAmountPrice());

					if (null != productId) {
						AppArtStarSku appArtStarSku = skuMap.get(productId);
						if(null != appArtStarSku){
							orderDetailResp.setProductName(appArtStarSku.getSkuName());
							orderDetailResp.setProductPrice(appArtStarSku.getPrice());
							if (StringUtils.isNotBlank(appArtStarSku.getImgUrl())) {
								Integer width = orderRequest.getWidth();
								String headImg = appArtStarSku.getImgUrl();
								if (width != null) {
									width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
									headImg = QiniuImageUtils.compressImageAndDiffPic(headImg, width, -1);
									LOG.info(" QiniuImageUtils.compressImageAndDiffPic :" + headImg);
								}
								orderDetailResp.setHeadImage(headImg);
							}
							orderDetailResp.setGoodName(appArtStarSku.getProductName());
							orderDetailResp.setGoodId(appArtStarSku.getProductId().intValue());
							orderDetailResp.setSelectAttr(appArtStarSku.getAttrStr());
						}
					}
					Integer fidStarWorkId = orderDetailDto.getFidStarArt();
					if(null != fidStarWorkId){
						//原始作品信息
						ArtStarWork artStarWork = workMap.get(fidStarWorkId);
						if(null != artStarWork){
							orderDetailResp.setOriginalWorkId(artStarWork.getArtId().intValue());
							orderDetailResp.setOriginalWorkName(artStarWork.getArtName());
						}
					}
					orderDetailRespList.add(orderDetailResp);
				}

			}
			or.setOrderDetailRespList(orderDetailRespList);

			return or;
		} catch (Exception e) {
			LOG.error("query order o2o-exception , more info :", e);
			throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
		}

	}

	@Override
	public ArtistInfoResponse getStarArtistById(StarArtistRequest request) {
		UserDto userVo = userProxy.getUserById(request.getUserId());
		if(null == userVo || null == userVo.getId()){
			return null;
		}
		MemberDto member = userVo.getMember();
		ArtistInfoResponse artistInfoResponse = ModelMapperUtil.strictMap(member, ArtistInfoResponse.class);
		artistInfoResponse.setUserName(userVo.getUsername());
		if (null != member && null != member.getBirthDate()) {
			artistInfoResponse.setAge(AgeUtil.getAge(DateUtils.dateToString(member.getBirthDate(), "yyyy-MM-dd")));
		}
		//查询艺术家作品
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userVo.getId());
		List<ArtStarWork> starWorks = starProductProxy.getArtStarWorksListById(paramMap);
		artistInfoResponse.setStarWorkList(starWorks);
		
		return artistInfoResponse;
	}

}

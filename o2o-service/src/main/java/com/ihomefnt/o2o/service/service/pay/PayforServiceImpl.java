/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:LianLianServiceImpl.java 
 */
package com.ihomefnt.o2o.service.service.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.WechatPayResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDtoVo;
import com.ihomefnt.o2o.intf.domain.order.dto.PayInput;
import com.ihomefnt.o2o.intf.domain.pay.dto.*;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.BankCardRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.PayRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.PayforRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.common.AppVersionConstants;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstants;
import com.ihomefnt.o2o.intf.manager.constant.pay.BankErrorCode;
import com.ihomefnt.o2o.intf.manager.constant.pay.BankStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.pay.CardTypeEnum;
import com.ihomefnt.o2o.intf.manager.constant.pay.PayforConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.secure.RSAUtils;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IpUtils;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.pay.PayforProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.pay.PayforService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.collections.Maps;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants.MODULE_CODE_FGW;
import static com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants.PRIVATE_KEY_FGW;

/**
 * @author zhang
 */
@Service
public class PayforServiceImpl implements PayforService {

	@Autowired
	private PayforProxy payforProxy;
	@Autowired
	private DicProxy dicProxy;
	@Autowired
	private UserProxy userProxy;
	@Autowired
	OrderProxy orderProxy;
	@Autowired
    OrderService orderService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PayforServiceImpl.class);

	@Override
	public BankListResponseVo getBankListByLianlian(HttpBaseRequest request) {
		BankListResponseVo info = new BankListResponseVo();
		DicDto vo = dicProxy.queryDicByKey(PayforConstant.LIAN_LIAN_TAG);
		if (vo == null || StringUtils.isBlank(vo.getValueDesc())
				|| !vo.getValueDesc().equals(PayforConstant.OPEN_TAG)) {
			info.setEnabled(false);
			return info;
		}
		info.setEnabled(true);
		List<BankInfoResultDto> list = payforProxy.queryAllSupportPayBankInfo(request.getAppVersion());
		if (CollectionUtils.isEmpty(list)) {
			return info;
		}
		List<BankInfoResponseVo> bankList = new ArrayList<BankInfoResponseVo>();
		for (BankInfoResultDto result : list) {
			// 过滤维护中
			if (BankStatusEnum.STATUS_MAINTAIN.getCode().equals(result.getBankStatus())) {
				continue;
			}
			BankInfoResponseVo bank = ModelMapperUtil.strictMap(result, BankInfoResponseVo.class);
			String cardTypeDesc = CardTypeEnum.getMsg(result.getCardType());
			bank.setCardTypeDesc(cardTypeDesc);
			bank.setBankLogo(result.getBankImg());
			bankList.add(bank);
		}
		info.setBankList(bankList);
		return info;
	}

	@Override
	public BankCardReponseVo getCardInfo(BankCardRequestVo request) {
		ResponseVo<CardBinResultDto> responseVo = payforProxy.cardbin(request.getCardNo());
		if (responseVo == null) {
			throw new BusinessException(BankErrorCode.NOT_VALID.getCode(), BankErrorCode.NOT_VALID.getMsg());
		}
		if (!responseVo.isSuccess()) {
			if (responseVo.getCode() != null) {
				String msg = BankErrorCode.getMsg(responseVo.getCode().longValue());
				if (StringUtils.isBlank(msg)) {
					msg = BankErrorCode.NOT_VALID.getMsg();
				}
				throw new BusinessException(responseVo.getCode().longValue(), msg);
			} else {
				throw new BusinessException(BankErrorCode.NOT_VALID.getCode(), BankErrorCode.NOT_VALID.getMsg());
			}
		}
		CardBinResultDto card = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), CardBinResultDto.class);
		if (card == null || StringUtils.isBlank(card.getBankCode()) || card.getCardType() == null) {
			throw new BusinessException(BankErrorCode.NOT_VALID.getCode(), BankErrorCode.NOT_VALID.getMsg());
		}
		/**
		 * 先判断是否支持卡
		 */
		List<BankInfoResultDto> list = payforProxy.queryAllSupportPayBankInfo(request.getAppVersion());
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		boolean bankTag = false;// true:支持卡 ,false :不支持
		for (BankInfoResultDto vo : list) {
			String bankCode = vo.getBankCode();
			Integer cardType = vo.getCardType();
			// 需要保证 银行code 和 银行卡类型 都 相等
			if (StringUtils.isNotBlank(bankCode) && bankCode.equals(card.getBankCode()) && cardType != null
					&& cardType.compareTo(card.getCardType()) == 0) {
				bankTag = true;
				break;
			}
		}
		if (!bankTag) {
			throw new BusinessException(BankErrorCode.NOT_SUPPROT.getCode(), BankErrorCode.NOT_SUPPROT.getMsg());
		}
		// 支持 的话,则继续判断银行限额
		BankInfoResultDto bank = payforProxy.eachCardInfo(card.getBankCode());
		if (bank == null) {
			throw new BusinessException(BankErrorCode.NOT_VALID.getCode(), BankErrorCode.NOT_VALID.getMsg());
		}
		BankCardReponseVo response = ModelMapperUtil.strictMap(bank, BankCardReponseVo.class);
		response.setBankLogo(bank.getBankImg());
		response.setCardNo(request.getCardNo());
		response.setCardTypeDesc(CardTypeEnum.getMsg(card.getCardType()));
		response.setBankStatusDesc(BankStatusEnum.getMsg(bank.getBankStatus()));
		return response;
	}

	@Override
	public PayResponseVo getOrderInfoForLianlian(PayRequestVo request) {
		if (request == null) {
			return null;
		}
		PayOrderDto payOrder = new PayOrderDto();
		payOrder.setAcct_name(request.getUserName());
		payOrder.setCard_no(request.getCardNo());
		if (request.getOrderId() != null) {
			payOrder.setNo_order(request.getOrderId().toString());
		}
		payOrder.setMoney_order(request.getSelectSum());
		payOrder.setId_no(request.getIdCard());

		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null && userDto.getId() != null) {
			//payOrder.setUser_id("100000");
			payOrder.setUser_id(userDto.getId().toString());
			RiskItem riskItem = new RiskItem();
			riskItem.setUser_info_mercht_userno(userDto.getId().toString());
			//riskItem.setUser_info_mercht_userno("100000");
			riskItem.setFrms_ware_category(PayforConstant.LIANLIAN_CATEGORY);
			riskItem.setUser_info_dt_register((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
			riskItem.setUser_info_bind_phone(userDto.getMobile());
			riskItem.setDelivery_addr_province(PayforConstant.PROVINCE_CODE);
			riskItem.setDelivery_addr_city(PayforConstant.CITY_CODE);
			riskItem.setDelivery_phone(userDto.getMobile());
			riskItem.setGoods_count(PayforConstant.GOODS_COUNT);
			riskItem.setLogistics_mode(PayforConstant.DELIVERY_MODE_COMMON);
			riskItem.setDelivery_cycle(PayforConstant.DELIVERY_CYCLE);
			riskItem.setUser_info_identify_state(PayforConstant.IDENTIFY_YES);
			riskItem.setUser_info_identify_type(PayforConstant.IDENTIFY_CARD_TYPE);
			riskItem.setUser_info_full_name(request.getUserName());
			riskItem.setUser_info_id_no(request.getIdCard());
			payOrder.setRisk_item(JsonUtils.obj2json(riskItem));
		}

		payOrder.setBusi_partner(PayforConstant.ACTUAL_PRODUCT_NO);
		String str = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
		payOrder.setDt_order(str);
		payOrder.setName_goods(request.getUserName() + ":" + request.getOrderId());
		payOrder.setInfo_order(request.getUserName() + ":" + request.getOrderId());
		payOrder = payforProxy.signAndRecord(payOrder);
		PayResponseVo pay = new PayResponseVo();
		pay.setOrderInfo(JsonUtils.obj2json(payOrder));
		return pay;
	}

	@Override
	public BankListResponseVo getBankListByUserId(Integer id) {
		BankListResponseVo info = new BankListResponseVo();
		DicDto vo = dicProxy.queryDicByKey(PayforConstant.LIAN_LIAN_TAG);
		if (vo == null || StringUtils.isBlank(vo.getValueDesc())
				|| !vo.getValueDesc().equals(PayforConstant.OPEN_TAG)) {
			info.setEnabled(false);
			return info;
		}
		info.setEnabled(true);
		List<BankInfoResultDto> list = payforProxy.queryActivationBankInfoByUserId(id);
		if (CollectionUtils.isEmpty(list)) {
			return info;
		}
		List<BankInfoResponseVo> bankList = new ArrayList<BankInfoResponseVo>();
		for (BankInfoResultDto result : list) {
			// 过滤维护中
			if (BankStatusEnum.STATUS_MAINTAIN.getCode().equals(result.getBankStatus())) {
				continue;
			}
			BankInfoResponseVo bank = ModelMapperUtil.strictMap(result, BankInfoResponseVo.class);
			String cardTypeDesc = CardTypeEnum.getMsg(result.getCardType());
			bank.setCardTypeDesc(cardTypeDesc);
			bank.setBankLogo(result.getBankImg());
			bankList.add(bank);
		}
		info.setBankList(bankList);
		return info;
	}

	@Override
	public boolean unbindBankInfo(BankCardRequestVo request) {
		if (request == null || StringUtils.isBlank(request.getCardNo())) {
			return false;
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null && userDto.getId() != null) {
			return payforProxy.unbindCard(userDto.getId(), request.getCardNo());
		}
		return false;
	}


	@Override
	public IdCardResponseVo getIdCardByUserId(Integer id) {
		List<BankInfoResultDto> list = payforProxy.queryActivationBankInfoByUserId(id);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		BankInfoResultDto info = list.get(0);

		String acctName = info.getAcctName();// 姓名

		String idCard = info.getIdCard();// 身份证

		if (StringUtils.isBlank(acctName) || StringUtils.isBlank(idCard)) {
			return null;
		}
		IdCardResponseVo response = new IdCardResponseVo();
		response.setUserName(acctName);
		response.setIdCard(idCard);

		if (idCard.length() == 18) {
			response.setIdCardDesc(idCard.replaceAll("(\\d{3})\\d{11}(\\d{4})", "$1***********$2"));
		} else if (idCard.length() == 15) {
			response.setIdCardDesc(idCard.replaceAll("(\\d{3})\\d{8}(\\d{4})", "$1********$2"));
		}

		return response;
	}
	
    /**
     * 解析data节点 根据 三种情况返回
     * @param data
     * @param param
     * @return
     * @throws RuntimeException
     */
    private Object resolving(JSONObject data,JSONObject param) throws RuntimeException{
        if(null == data){
            throw new RuntimeException("PayforProxy.pay response is null . ");
        }
        //渠道来源WX("WX", 1), ALIPAY("ALIPAY", 2), LIANLIAN("LIANLIAN", 3)
        Integer channelSource = param.getInteger("channelSource");
        data.put("channelSource",null);
        Object obj = null;
        switch (channelSource){
            case 1:
                WechatResponseVo wechat = JsonUtils.json2obj(data.toJSONString(),WechatResponseVo.class);
                obj =wechat;
                break;
            case 2:
                AlipayResultDto alipay = new AlipayResultDto();
                alipay.setOrderNum(param.getString("orderNum"));
                alipay.setPayInfo(data.getString("orderPayInfo"));
                obj =alipay;
                break;
            case 3:
                LianlianDto lianlian = JsonUtils.json2obj(data.toJSONString(),LianlianDto.class);
                JSONObject app_lianlian = new JSONObject();
                PayResponseVo lianlianResponse = new PayResponseVo();
                lianlianResponse.setOrderInfo(JsonUtils.obj2json(lianlian));
                obj =lianlianResponse;
                break;
            case 4:
                WechatPayResponseVo wechatpublic = JsonUtils.json2obj(data.toJSONString(),WechatPayResponseVo.class);
                wechatpublic.setPackageStr(data.getString("package"));
                obj =wechatpublic;
                break;
            case 5:
                obj =JsonUtils.json2map(data.toJSONString());
                break;
			case 6:
				LianlianDto newLianlian = JsonUtils.json2obj(data.toJSONString(),LianlianDto.class);
				PayResponseVo newLianlianResponse = new PayResponseVo();
				newLianlianResponse.setOrderInfo(JsonUtils.obj2json(newLianlian));
				obj =newLianlianResponse;
				break;
        }
        return obj;
    }

	@Override
	public Object payForOrder(PayforRequestVo req, HttpServletRequest httpServletRequest) {
		// 判断用户是否登录 并获取以登录信息
        UserDto user = userProxy.getUserByToken(req.getAccessToken());
        if(null == user){
            LOGGER.info("pay.common user have not login");
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        
        OrderDtoVo order = null;
        try{
            order = orderService.queryOrderDetail(Integer.parseInt(req.getOrderId()));
        }catch (Exception e){
            LOGGER.error("OrderProxy.queryOrderDetail  o2o-exception , more info : ", e);
            throw new BusinessException(MessageConstant.FAILED);
        }
        if(null == order){
            LOGGER.info("pay.common order bean is null . ");
            throw new BusinessException(MessageConstant.FAILED);
        }

        //  其他参数赋值
        req = configRequestParam(req,order,user,httpServletRequest);

        // 调用支付接口
        try{
        	return this.pay(req.parseJson());
        }catch (RuntimeException re){
            LOGGER.error("PayforService.pay o2o-exception , more info : ", re);
            throw new BusinessException(HttpReturnCode.FGW_FAIL,re.getMessage());
        }
	}
	
	@Override
    public Object pay(JSONObject param) throws RuntimeException {
        if(null == param){
            LOGGER.error("PayforServiceImpl.pay param is null . ");
        }
        JSONObject data = null;
        try{
            data = payforProxy.pay(param);
        }catch (RuntimeException re){
            throw re;
        }
        Object response = null;
        try{
            response = resolving(data,param);
        }catch (RuntimeException re){
            throw  re;
        }
        return response;
    }
	
	
	/**
     * 设置请求参数的其他属性
     * @param req req
     * @param order 订单
     * @param user 用户
     * @param httpServletRequest  httpServletRequest
     * @return param
     */
    private PayforRequestVo configRequestParam( final PayforRequestVo req,final OrderDtoVo order,final UserDto user,final HttpServletRequest httpServletRequest){
        PayforRequestVo param  = req;
		param.setPlatform(req.getOsType());
		/**
         * 前端传递是orderId 底层需要的是orderNum
         */
        param.setOrderNum(order.getOrderNum());

         req.setActualPayMent(req.getActualPayMent().setScale(2, BigDecimal.ROUND_DOWN));

        param.setOrderType(order.getOrderType());
        param.setUserId(user.getId());
        //BigDecimal bd = new BigDecimal(0);
        /**
         * 地雷 by cangjifeng 全品家订单订金没有上限 有下线
         *
         */
        if(order.getOrderId() >= OrderConstants.CRITICAL_VALUE_LOWER && order.getOrderId() <= OrderConstants.CRITICAL_VALUE_UPPER){
            LOGGER.info(order.getOrderId() + " is big order ...");
        }
        /*if(null == order.getTotalPrice()){
            param.setTotalPayMent(order.getActualPayMent().add(req.getActualPayMent()));
        } else{
            param.setTotalPayMent(order.getTotalPrice());
        }*/
        param.setTotalPayMent(order.getTotalPrice());
        if(null == param.getSource()){
            Integer source = 2;
            param.setSource(source);
        }
        param.setIp(IpUtils.getIpAddr(httpServletRequest));
        //param.setOrderInfo(user.getUsername()+":"+req.getOrderNum());
        param.setOrderInfo(req.getAcctName()+":"+req.getOrderNum());

        if(PayforConstant.TYPE_PAY_LIANLIAN.equals(param.getChannelSource()) ||
				PayforConstant.TYPE_PAY_NEW_LIANLIAN.equals(param.getChannelSource())){
            param.setRiskItem(riskItem(user,req));
            param.setBusiPartner(PayforConstant.ACTUAL_PRODUCT_NO);
            /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss:SSS");
            format.format(order.getCreateTime());*/
            //param.setOrderDt(new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss:SSS").format(order.getCreateTime()));
            param.setOrderDt(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            param.setGoodsName(req.getAcctName() + ":" + req.getOrderNum());
        }
        if (PayforConstant.TYPE_PAY_WECHAT_APPLET.equals(param.getChannelSource())) {
        	// 微信小程序支付需要查用户中心oppenId
        	 String openId = userProxy.getNewStarMpOpenId(user.getId());
        	 param.setOpenId(openId);
        }
        
        return param;
    }

    /**
     * 生成风控字段
     * @param userDto 用户
     * @param request 请求参数
     * @return 风控字段
     */
    private String riskItem(UserDto userDto,PayforRequestVo request){
        RiskItem riskItem = new RiskItem();
        riskItem.setUser_info_mercht_userno(userDto.getId().toString());
        riskItem.setFrms_ware_category(PayforConstant.LIANLIAN_CATEGORY);
        riskItem.setUser_info_dt_register((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
        riskItem.setUser_info_bind_phone(userDto.getMobile());
        riskItem.setDelivery_addr_province(PayforConstant.PROVINCE_CODE);
        riskItem.setDelivery_addr_city(PayforConstant.CITY_CODE);
        riskItem.setDelivery_phone(userDto.getMobile());
        riskItem.setGoods_count(PayforConstant.GOODS_COUNT);
        riskItem.setLogistics_mode(PayforConstant.DELIVERY_MODE_COMMON);
        riskItem.setDelivery_cycle(PayforConstant.DELIVERY_CYCLE);
        riskItem.setUser_info_identify_state(PayforConstant.IDENTIFY_YES);
        riskItem.setUser_info_identify_type(PayforConstant.IDENTIFY_CARD_TYPE);
        riskItem.setUser_info_full_name(request.getAcctName());
        riskItem.setUser_info_id_no(request.getIdNo());
        return JsonUtils.obj2json(riskItem);
    }

    
    
    
    
    @Override
    public PayResponseVo handlerOldLianlianSignAndRecord(PayRequestVo request) {
    	LOGGER.info("=====================> handlerOldLianlianSignAndRecord request is {}",request);
        if (request == null) {
            return null;
        }
        PayOrderDto payOrder = new PayOrderDto();
        payOrder.setAcct_name(request.getUserName());
        payOrder.setCard_no(request.getCardNo());
        if (request.getOrderId() != null) {
            payOrder.setNo_order(request.getOrderId().toString());
        }
        payOrder.setMoney_order(request.getSelectSum());
        payOrder.setId_no(request.getIdCard());
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null && userDto.getId() != null) {
			//payOrder.setUser_id("100000");
			payOrder.setUser_id(userDto.getId().toString());
			RiskItem riskItem = new RiskItem();
			riskItem.setUser_info_mercht_userno(userDto.getId().toString());
			//riskItem.setUser_info_mercht_userno("100000");
			riskItem.setFrms_ware_category(PayforConstant.LIANLIAN_CATEGORY);
			riskItem.setUser_info_dt_register((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
			riskItem.setUser_info_bind_phone(userDto.getMobile());
			riskItem.setDelivery_addr_province(PayforConstant.PROVINCE_CODE);
			riskItem.setDelivery_addr_city(PayforConstant.CITY_CODE);
			riskItem.setDelivery_phone(userDto.getMobile());
			riskItem.setGoods_count(PayforConstant.GOODS_COUNT);
			riskItem.setLogistics_mode(PayforConstant.DELIVERY_MODE_COMMON);
			riskItem.setDelivery_cycle(PayforConstant.DELIVERY_CYCLE);
			riskItem.setUser_info_identify_state(PayforConstant.IDENTIFY_YES);
			riskItem.setUser_info_identify_type(PayforConstant.IDENTIFY_CARD_TYPE);
			riskItem.setUser_info_full_name(request.getUserName());
			riskItem.setUser_info_id_no(request.getIdCard());
			payOrder.setRisk_item(JsonUtils.obj2json(riskItem));
		}
        payOrder.setBusi_partner(PayforConstant.ACTUAL_PRODUCT_NO);
        String str = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
        payOrder.setDt_order(str);
        payOrder.setName_goods(request.getUserName() + ":" + request.getOrderId());
        payOrder.setInfo_order(request.getUserName() + ":" + request.getOrderId());
		payOrder.setPlatform(request.getOsType());
        payOrder = newSignAndRecord(payOrder, request.getAppVersion());
        PayResponseVo pay = new PayResponseVo();
        pay.setOrderInfo(JsonUtils.obj2json(payOrder));

        LOGGER.info("=====================> handlerOldLianlianSignAndRecord result is {}",pay);
        return pay;
    }

    @Override
    public String handlerOldAliPaySignAndRecord(PayInput payInput) {
    	LOGGER.info("=====================> handlerOldAliPaySignAndRecord request params is {}",payInput);

        Map<String,Object> maps = Maps.newHashMap();
        maps.put("orderNum",payInput.getOrderNum());
        maps.put("orderType",1);

//        maps.put("orderType",payOrder.getNo_order());
        maps.put("userId",payInput.getUserId());
        maps.put("actualPayMent",BigDecimal.valueOf(payInput.getMoney()));
        maps.put("totalPayMent",BigDecimal.valueOf(payInput.getMoney()));
        //订单来源PC("PC网站", 1), APP("APP", 2), H5("H5", 3)
        maps.put("source",2);
        //渠道来源WX("WX", 1), ALIPAY("ALIPAY", 2), LIANLIAN("LIANLIAN", 3)
        maps.put("channelSource",2);
        maps.put("platform",payInput.getPlatform());
		InetAddress inetAddress = getLocalIP();
        if(inetAddress != null){
            maps.put("ip",inetAddress.getHostAddress());
        }

        Object result = getNativePayDataFromFgw(maps, OnlinePayParamsDto.class);
        if(null != result){
            OnlinePayParamsDto onlinePayParams = (OnlinePayParamsDto)result;
            LOGGER.info("=====================> handlerOldAliPaySignAndRecord result is {}",onlinePayParams);
            return onlinePayParams.getOrderPayInfo();
        }
        return null;
    }

    private PayOrderDto newSignAndRecord(PayOrderDto payOrder, String appVersion) {
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("orderNum",payOrder.getNo_order());
        // 可以不传递
        maps.put("orderType",1);
        maps.put("userId",payOrder.getUser_id());
        maps.put("actualPayMent",new BigDecimal(payOrder.getMoney_order()));
        maps.put("totalPayMent",new BigDecimal(payOrder.getMoney_order()));
        //订单来源PC("PC网站", 1), APP("APP", 2), H5("H5", 3)
        maps.put("source",2);
        //渠道来源WX("WX", 1), ALIPAY("ALIPAY", 2), LIANLIAN("LIANLIAN", 3)
		// 5.4.3升级新连连，channelSource修改为 6：新连连
		if(VersionUtil.versionCompare(appVersion, AppVersionConstants.NEW_LIAN_LIAN_VERSION) >= 2){
			maps.put("channelSource",6);
		}else{
			maps.put("channelSource",3);
		}
		InetAddress inetAddress = getLocalIP();
		if(inetAddress != null){
			maps.put("ip",inetAddress.getHostAddress());
		}

        // 连连核心字段
        maps.put("acctName",payOrder.getAcct_name());
        maps.put("cardNo",payOrder.getCard_no());
        maps.put("idNo",payOrder.getId_no());
        maps.put("riskItem",payOrder.getRisk_item());
        maps.put("busiPartner",payOrder.getBusi_partner());
        maps.put("orderDt",payOrder.getDt_order());
        maps.put("goodsName",payOrder.getName_goods());
        maps.put("signType","RSA");
        maps.put("platform",payOrder.getPlatform());
//        maps.put("oidPartner",payOrder.getOid_partner());

        return (PayOrderDto)getNativePayDataFromFgw(maps,PayOrderDto.class);
    }


    public Object getNativePayDataFromFgw(Map<String,Object> params,Class clz){
        LOGGER.info(">>>>>>>>>>>>>>>>>>>> getNativePayDataFromFgw >>>>>>>>>>>>>>>>>");

        try {
            params.put("moduleCode",MODULE_CODE_FGW);
            SortedMap<String,Object> requestParam = new TreeMap<>(params);

            LOGGER.info("requestParam is {}",JSON.toJSONString(requestParam));
            String signContent = RSAUtils.getSignContent(requestParam);
            LOGGER.info("signContent is {}",signContent);
            String sign = RSAUtils.sign(signContent, PRIVATE_KEY_FGW);
            params.put("insideSign" ,sign);
            
            Object obj = payforProxy.pullOnlinePay(params);
            return JSON.parseObject(JSON.toJSONString(obj),clz);
        } catch (Exception e) {
            LOGGER.error("getNativePayDataFromFgw {} Exception", JsonUtils.obj2json(params),  e);
        }

        return null;
    }

    public static InetAddress getLocalIP() {
        InetAddress ip = null;
        try
        {
            boolean bFindIP = false;
            Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                    .getNetworkInterfaces();
            while (netInterfaces.hasMoreElements())
            {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements())
                {
                    if (bFindIP) {
                        break;
                    }
                    ip = ips.nextElement();
                    if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1)
                    {
                        bFindIP = true;
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (null != ip)
        {
            return ip;
        }
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        }
        return null;
    }

}

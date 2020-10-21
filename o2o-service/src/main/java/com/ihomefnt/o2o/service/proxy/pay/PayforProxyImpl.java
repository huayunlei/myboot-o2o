/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:LianLianProxy.java
 */
package com.ihomefnt.o2o.service.proxy.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.pay.dto.BankInfoResultDto;
import com.ihomefnt.o2o.intf.domain.pay.dto.CardBinResultDto;
import com.ihomefnt.o2o.intf.domain.pay.dto.PayOrderDto;
import com.ihomefnt.o2o.intf.manager.constant.common.AppVersionConstants;
import com.ihomefnt.o2o.intf.manager.constant.pay.PayCommonErrorCodeEnum;
import com.ihomefnt.o2o.intf.manager.constant.proxy.FgwWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.secure.RSAUtils;
import com.ihomefnt.o2o.intf.proxy.pay.PayforProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants.PRIVATE_KEY_FGW;

/**
 * @author zhang
 */
@Service
public class PayforProxyImpl implements PayforProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	/**
	 * 增加日志:主要为了方便定位
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PayforProxy.class);

	@Override
	public List<BankInfoResultDto> queryAllSupportPayBankInfo(String appVersion) {
		try {
			Integer version = 1;// 是否新版连连认证支付 1：老版本；2:新版本
			if(null != appVersion &&
					VersionUtil.versionCompare(appVersion, AppVersionConstants.NEW_LIAN_LIAN_VERSION) >= 2){
				version = 2;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("version", version);
			ResponseVo<List<BankInfoResultDto>> responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.QUERY_ALL_SUPPORT_PAY_BANK_INFO, param,
					new TypeReference<ResponseVo<List<BankInfoResultDto>>>() {});
			if (responseVo == null) {
				return null;
			}
			if (responseVo.getData() == null) {
				return null;
			}

			return responseVo.getData();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ResponseVo<CardBinResultDto> cardbin(String cardNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cardNo", cardNo);

		try {
			ResponseVo<CardBinResultDto> responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.LIANLIAN_APP_CARDBIN, param,
					new TypeReference<ResponseVo<CardBinResultDto>>() {});
			if (responseVo == null) {
				return null;
			}
			return responseVo;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public BankInfoResultDto eachCardInfo(String bankCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bankCode", bankCode);

		try {
			ResponseVo<BankInfoResultDto> responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.QUERY_EACH_CARD_INFO, param,
					new TypeReference<ResponseVo<BankInfoResultDto>>() {});
			if (responseVo == null) {
				return null;
			}
			return responseVo.getData();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PayOrderDto signAndRecord(PayOrderDto param) {
		try {
			ResponseVo<PayOrderDto> responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.SIGN_AND_RECORD, param,
					new TypeReference<ResponseVo<PayOrderDto>>() {});
			if (responseVo == null) {
				return null;
			}
			return responseVo.getData();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<BankInfoResultDto> queryActivationBankInfoByUserId(Integer id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", id);

		try {
			ResponseVo<List<BankInfoResultDto>> responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.QUERY_ACTIVATION_BANK_INFO_BY_USER_ID, param,
					new TypeReference<ResponseVo<List<BankInfoResultDto>>>() {});
			if (responseVo == null) {
				return null;
			}
			return responseVo.getData();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean unbindCard(Integer id, String cardNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cardNo", cardNo);
		param.put("userId", id);

		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.UN_BIND_CARD, param, ResponseVo.class);
			if (responseVo == null) {
				return false;
			}
			return responseVo.isSuccess();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
    public JSONObject pay(JSONObject param) throws RuntimeException {
        if(null == param){
            throw new RuntimeException("PayforProxyImpl.pay param is null . ");
        }
        LOG.info("pay native param is {}",param.toJSONString());

        ResponseVo<?> responseVo = null;
        try{
            SortedMap<String,Object> requestParam = new TreeMap<>(JsonUtils.json2map(param.toJSONString()));

            LOG.info("requestParam is {}",JSON.toJSONString(requestParam));
            String signContent = RSAUtils.getSignContent(requestParam);
            LOG.info("signContent is {}",signContent);
            String sign = RSAUtils.sign(signContent, PRIVATE_KEY_FGW);
            param.put("insideSign" ,sign);
            responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.PULL_ONLINE_PAY,param,ResponseVo.class);
        }catch (Exception e){
            throw new RuntimeException(FgwWebServiceNameConstants.PULL_ONLINE_PAY +" exception , more info : "+e);
        }
        // 成功 code=1 success true
       Integer code_success = 1;
       if(null == responseVo ){
		   throw new BusinessException(HttpReturnCode.FGW_FAIL, MessageConstant.FGW_RESPONSE_EMPTY);
	   } else if(code_success != responseVo.getCode() || false == responseVo.isSuccess()) {
       	   throw new BusinessException(HttpReturnCode.FGW_FAIL, PayCommonErrorCodeEnum.getShowMsg(responseVo.getCode()));
       }
       JSONObject data = JSON.parseObject(JsonUtils.obj2json(responseVo.getData()));
       if(null == data){
           throw new BusinessException(HttpReturnCode.FGW_RESPONSE_EMPTY, MessageConstant.FGW_RESPONSE_EMPTY);
       }
       // 1 支付宝 2 微信 3 lianlian
       //data.put("channelSource",param.getString("channelSource"));
        return data;
    }

	@Override
	public Object pullOnlinePay(Map<String, Object> params) {
		ResponseVo<?> responseVo = strongSercviceCaller.post(FgwWebServiceNameConstants.PULL_ONLINE_PAY, params,
                ResponseVo.class);
		if(responseVo != null && responseVo.isSuccess()){
			return responseVo.getData();
		}
		return null;
	}

}

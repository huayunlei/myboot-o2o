/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月13日
 * Description:ArtistProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.artist;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.artist.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.artist.ApplyEnum;
import com.ihomefnt.o2o.intf.manager.constant.artist.ArtistRegisterErrorEnum;
import com.ihomefnt.o2o.intf.proxy.artist.ArtistProxy;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
@Service
public class ArtistProxyImpl implements ArtistProxy {

	@Resource
	private ServiceCaller serviceCaller;

	private static final Logger LOG = LoggerFactory.getLogger(ArtistProxyImpl.class);

	@Override
	public Long registerDesigner(RegisterRequestVo param) {
		LOG.info("dolly-web.designer-external.registerDesigner params:{}", JsonUtils.obj2json(param));
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("dolly-web.designer-external.registerDesigner", param,
					ResponseVo.class);
		} catch (Exception e) {
			LOG.error("dolly-web.designer-external.registerDesigner ERROR:{}", e.getMessage());
			return ArtistRegisterErrorEnum.SYS_ERROR.getCode();
		}
		if (responseVo == null || responseVo.getData() == null) {
			return ArtistRegisterErrorEnum.SYS_ERROR.getCode();
		} else {

			LOG.info("dolly-web.designer-external.registerDesigner result :{}",
					JsonUtils.obj2json(responseVo));
			ArtistRegisterResponse vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
					ArtistRegisterResponse.class);
			if (vo.getCode() == null) {
				return ArtistRegisterErrorEnum.SYS_ERROR.getCode();
			}
			return vo.getCode();
		}
	}

	@Override
	public ArtistResponseVo queryDesignerByUserId(Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		LOG.info("dolly-web.designer-external.queryDesignerByUserId params:{}", JsonUtils.obj2json(param));
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("dolly-web.designer-external.queryDesignerByUserId", param,
					ResponseVo.class);
		} catch (Exception e) {
			LOG.error("dolly-web.designer-external.queryDesignerByUserId ERROR:{}", e.getMessage());
			return null;
		}
		if (responseVo == null || responseVo.getData() == null) {
			return null;
		} else {
			LOG.info("dolly-web.designer-external.queryDesignerByUserId result :{}",
					JsonUtils.obj2json(responseVo));
			if (responseVo.isSuccess()) {
				ArtistResponseVo vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
						ArtistResponseVo.class);
				return vo;
			}
			return null;
		}

	}

	@Override
	public DesignFeeResponseVo queryDesignFee(Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		LOG.info("finalkeeper-web.designfee.queryDesignFee params:{}", JsonUtils.obj2json(param));
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("finalkeeper-web.designfee.queryDesignFee", param, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("finalkeeper-web.designfee.queryDesignFee ERROR:{}", e.getMessage());
			return null;
		}
		if (responseVo == null || responseVo.getData() == null) {
			return null;
		} else {
			LOG.info("finalkeeper-web.designfee.queryDesignFee result :{}", JsonUtils.obj2json(responseVo));
			if (responseVo.isSuccess()) {
				DesignFeeResponseVo vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
						DesignFeeResponseVo.class);
				return vo;
			}
			return null;
		}

	}

	@Override
	public Integer applyWithdrawal(Integer userId, BigDecimal amount) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("amount", amount);
		LOG.info("aladdin-commission.designfee.applyWithdrawal params:{}", JsonUtils.obj2json(param));
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("aladdin-commission.designfee.applyWithdrawal", param, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("aladdin-commission.designfee.applyWithdrawal ERROR:{}", e.getMessage());
			return ApplyEnum.SYS_ERROR.getCode();
		}
		if (responseVo == null || responseVo.getCode() == null) {
			return ApplyEnum.SYS_ERROR.getCode();
		} else {
			LOG.info("aladdin-commission.designfee.applyWithdrawal result :{}", JsonUtils.obj2json(responseVo));
			return responseVo.getCode();
		}
	}

	@Override
	public DesignDealNumsResponseVo queryDealNums(DesignerRequestVo param) {
		LOG.info("aladdin-commission.designfee.queryDealNums params:{}", JsonUtils.obj2json(param));
		long t1 = System.currentTimeMillis();
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("aladdin-commission.designfee.queryDealNums", param,
					ResponseVo.class);
		} catch (Exception e) {
			LOG.error("aladdin-commission.designfee.queryDealNums ERROR:{}", e.getMessage());
			long t2 = System.currentTimeMillis();
			LOG.info("aladdin-commission.designfee.queryDealNums times :{} ms", t2 - t1);
			return null;
		}
		if (responseVo == null || responseVo.getData() == null) {
			return null;
		} else {
			long t2 = System.currentTimeMillis();
			LOG.info("aladdin-commission.designfee.queryDealNums times :{} ms", t2 - t1);
			LOG.info("aladdin-commission.designfee.queryDealNums result :{}", JsonUtils.obj2json(responseVo));
			if (responseVo.isSuccess()) {
				DesignDealNumsResponseVo vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
						DesignDealNumsResponseVo.class);
				return vo;
			}
			return null;
		}
	}

	@Override
	public List<DesignerInfoByRoomResponse> queryCopyrigBySolutionRoomIds(List<Integer> roomIds) {
		LOG.info("dolly-web.designer-external.queryCopyrigBySolutionRoomIds params:{}",
				JsonUtils.obj2json(roomIds));
		long t1 = System.currentTimeMillis();
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("dolly-web.designer-external.queryCopyrigBySolutionRoomIds",
					roomIds, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("dolly-web.designer-external.queryCopyrigBySolutionRoomIds ERROR:{}", e.getMessage());
			long t2 = System.currentTimeMillis();
			LOG.info("dolly-web.designer-external.queryCopyrigBySolutionRoomIds times :{} ms", t2 - t1);
			return null;
		}
		if (responseVo == null || responseVo.getData() == null) {
			return null;
		} else {
			long t2 = System.currentTimeMillis();
			LOG.info("dolly-web.designer-external.queryCopyrigBySolutionRoomIds times :{} ms", t2 - t1);
			LOG.info("dolly-web.designer-external.queryCopyrigBySolutionRoomIds result :{}",
					JsonUtils.obj2json(responseVo));
			if (responseVo.isSuccess()) {
				List<DesignerInfoByRoomResponse> vo = JsonUtils.json2list(JsonUtils.obj2json(responseVo.getData()),
						DesignerInfoByRoomResponse.class);
				return vo;
			}
			return null;
		}
	}

	@Override
	public DesignerMoreInfoByDnaResponseVo queryDegignerDetailByUserId(DesignerRequestVo param) {
		LOG.info("dolly-web.designer-external.queryDegignerDetailByUserId params:{}",
				JsonUtils.obj2json(param));
		long t1 = System.currentTimeMillis();
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("dolly-web.designer-external.queryDegignerDetailByUserId", param,
					ResponseVo.class);
		} catch (Exception e) {
			LOG.error("dolly-web.designer-external.queryDegignerDetailByUserId ERROR:{}", e.getMessage());
			long t2 = System.currentTimeMillis();
			LOG.info("dolly-web.designer-external.queryDegignerDetailByUserId times :{} ms", t2 - t1);
			return null;
		}
		if (responseVo == null || responseVo.getData() == null) {
			return null;
		} else {
			long t2 = System.currentTimeMillis();
			LOG.info("dolly-web.designer-external.queryDegignerDetailByUserId times :{} ms", t2 - t1);
			LOG.info("dolly-web.designer-external.queryDegignerDetailByUserId result :{}",
					JsonUtils.obj2json(responseVo));
			if (responseVo.isSuccess()) {
				DesignerMoreInfoByDnaResponseVo vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
						DesignerMoreInfoByDnaResponseVo.class);
				return vo;
			}
			return null;
		}
	}

	@Override
	public DesignerSimpleInfoResponse querySimpleDesingerInfoByDnaId(Integer dnaId) {
		LOG.info("dolly-web.designer-external.querySimpleDesingerInfoByDnaId params:{}",
				JsonUtils.obj2json(dnaId));
		long t1 = System.currentTimeMillis();
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("dolly-web.designer-external.querySimpleDesingerInfoByDnaId",
					dnaId, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("dolly-web.designer-external.querySimpleDesingerInfoByDnaId ERROR:{}", e.getMessage());
			long t2 = System.currentTimeMillis();
			LOG.info("dolly-web.designer-external.querySimpleDesingerInfoByDnaId times :{} ms", t2 - t1);
			return null;
		}
		if (responseVo == null || responseVo.getData() == null) {
			return null;
		} else {
			long t2 = System.currentTimeMillis();
			LOG.info("dolly-web.designer-external.querySimpleDesingerInfoByDnaId times :{} ms", t2 - t1);
			LOG.info("dolly-web.designer-external.querySimpleDesingerInfoByDnaId result :{}",
					JsonUtils.obj2json(responseVo));
			if (responseVo.isSuccess()) {
				DesignerSimpleInfoResponse vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
						DesignerSimpleInfoResponse.class);
				return vo;
			}
			return null;
		}
	}

}

/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月13日
 * Description:ArtistProxy.java
 */
package com.ihomefnt.o2o.intf.proxy.artist;

import com.ihomefnt.o2o.intf.domain.artist.dto.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhang
 * @see http://192.168.1.12:10025/dolly-web/swagger
 * @see http://192.168.1.11:10041/aladdin-commission/swagger
 * @see http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=11245804
 */
public interface ArtistProxy {

	/**
	 * 申请设计师注册
	 * 
	 * @param param
	 * @return
	 */
	Long registerDesigner(RegisterRequestVo param);

	/**
	 * 根据userId 查询设计师信息
	 * 
	 * @param userId
	 * @return
	 */
	ArtistResponseVo queryDesignerByUserId(Integer userId);

	/**
	 * 我的收益
	 * 
	 * @param id
	 * @return
	 */
	DesignFeeResponseVo queryDesignFee(Integer id);

	/**
	 * 提现申请
	 * 
	 * @param id
	 * @param applyCashMoney
	 * @return
	 */
	Integer applyWithdrawal(Integer id, BigDecimal applyCashMoney);

	/**
	 * 查询设计师dna空间成交数
	 * 
	 * @param param
	 * @return
	 */
	DesignDealNumsResponseVo queryDealNums(DesignerRequestVo param);

	/**
	 * 根据方案空间ID查询引用的设计师信息（APP）
	 * 
	 * @param roomIds
	 * @return
	 */
	List<DesignerInfoByRoomResponse> queryCopyrigBySolutionRoomIds(List<Integer> roomIds);

	/**
	 * 根据用户id获取设计师信息接口（app）
	 * 
	 * @param param
	 * @return
	 */
	DesignerMoreInfoByDnaResponseVo queryDegignerDetailByUserId(DesignerRequestVo param);

	/**
	 * 根据DNAID查询简单的设计师信息（APP）
	 * 
	 * @param dnaId
	 * @return
	 */
	DesignerSimpleInfoResponse querySimpleDesingerInfoByDnaId(Integer dnaId);
}

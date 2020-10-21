/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月22日
 * Description:KuaidiProductInfo.java 
 */
package com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class KuaidiProductInfoResponseVo {

	private List<KuaidiProductDeliveryResponseVo> data; // 运输记录

	private String com;// 运输公司

	private String nu;// 快递单号

	/* 快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态 */
	private Integer state;
	
	private String stateDesc;//中文名称

	private String message;// 请求后内容

	private Boolean result; /* 查询结果：false表示查询失败,为空和true表示查询成功 */
}

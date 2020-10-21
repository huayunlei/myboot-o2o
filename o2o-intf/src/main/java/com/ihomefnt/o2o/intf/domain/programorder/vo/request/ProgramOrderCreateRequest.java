/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年3月15日
 * Description:ProgramOrderCreateRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.program.vo.request.AddBagCreateRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardReplace;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("产品方案-创建订单参数")
public class ProgramOrderCreateRequest extends HttpBaseRequest {

	@ApiModelProperty("用户id")
	private Integer userId;

	@ApiModelProperty("订单类型(套装:9,和空间组合:10)")
	private Integer orderType;

	@ApiModelProperty("套装Id")
	private Integer suitId;

	@ApiModelProperty("空间Id组合")
	private List<Integer> roomIdList;

	@ApiModelProperty("全品家大订单Id")
	private Integer masterOrderId;

	@ApiModelProperty("房产Id")
	@Deprecated
	private Integer houseId;

	@ApiModelProperty("房产Id")
	private Integer customerHouseId;

	@ApiModelProperty("增配包商品集合")
	private List<AddBagCreateRequest> addBagList;
	
	@ApiModelProperty("升级包商品集合")
	private List<StandardCreateRequest> standardDtos;

	@ApiModelProperty("空间商品调整对象集合")
	private List<ReplaceProductCreateRequest> replaceProductDtos;

	@ApiModelProperty("空间硬装调整对象集合")
	private List<HardReplace> replaceHardProductDtos;

	@ApiModelProperty("空间新增硬装商品集合")
	private List<HardReplace> addHardProductDtoList;
}

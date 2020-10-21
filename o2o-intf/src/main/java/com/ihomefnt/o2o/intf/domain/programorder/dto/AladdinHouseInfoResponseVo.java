package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import lombok.Data;

/**
 * 全品家大订单房产全信息集合
 * @author ZHAO
 */
@Data
public class AladdinHouseInfoResponseVo {
	private HouseInfoResponseVo houseInfo;//房产户型信息
	
	private AladdinCustomerInfoVo userInfo;//用户信息
	
	private AladdinAdviserInfoVo adviserInfoVo;//置家顾问信息
	
	private AladdinDealInfoVo transactionInfoVo;//付款信息

}

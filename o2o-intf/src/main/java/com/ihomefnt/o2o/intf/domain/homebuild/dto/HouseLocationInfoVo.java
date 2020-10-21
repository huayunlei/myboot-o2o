package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 户型信息
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
public class HouseLocationInfoVo implements Serializable {
	private String roomNo;//房号
	
	private Integer houseId;//户型id
	
	private String houseName;//户型

	private BigDecimal area;//面积

	private Integer location;//户型位置:0东西通用 1东边户 2西边户

	private String pattern;//户型格局：*室*厅..
	
	private Integer deliveryType;//交付类型：1,全品家 2.全品家软
	
	private String expectedSubmitData;//预计交房日期
	
	private String expectedSubmitDataStr;//预计交房日期Str
	
	private Integer companyId;//所属公司ID
	
	private Integer layoutRoom;//几室
	
	private Integer layoutLiving;//几厅
	
	private Integer layoutKitchen;//几厨
	
	private Integer layoutToliet;//几卫

	private Integer layoutBalcony;//几阳台
	
	private Integer layoutStorage;//几储物间
	
	private Integer layoutCloak;//几衣帽间
	
	private String layoutImgUrl;//户型图
}

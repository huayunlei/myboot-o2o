package com.ihomefnt.o2o.intf.domain.promotion.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 1219置家狂欢节房产信息
 * @author ZHAO
 */
@Data
public class OrderHouseInfoResultVo {
	private Integer orderNum;//订单编号
	
	private Integer orderStatus;//订单阶段
	
	private String orderStatusStr;//订单阶段描述
	
	private BigDecimal contractAmount;//合同金额
	
	private Integer source;//0:方案订单 1:代客下单
	
	private Integer joinActFlag;//是否参加了活动 0：否 1：是
	
	private Timestamp joinTime;//参加活动时间
	
	private String joinTimeStr;//参加活动日期描述
	
	private Integer buildingId;//项目（楼盘）ID
	
	private String buildingName;//项目（楼盘）名称
	
	private String buildingType;//项目类型
	
	private Integer layoutId;//户型ID
	
	private String layoutName;//户型名称
	
	private Integer layoutRoom;//室
	
	private Integer layoutLiving;//厅
	
	private Integer layoutKitchen;//厨房
	
	private Integer layoutToilet;//卫
	
	private Integer layoutStorage;//储藏室
	
	private Integer layoutCloak;//衣帽间
	
	private Integer layoutBalcony;//阳台
	
	private String housingNum;//楼号
	
	private String roomNum;//房号
	
	private Integer adviser;//置家顾问
	
	private String adviserName;//置家顾问姓名
	
	private String adviserMobile;//置家顾问手机号
	
	private String layoutInfo;//户型信息
	
	private String housePropertyInfo;//房产信息
	
	private Integer houseId;//房产id
	
	private Integer userId;//用户id
	
	private String area;//房屋面积
	
	private Integer companyId;//所属公司ID
	
	private Integer agentId;//经纪人id
	
	private String agentName;//经纪人姓名
	
	private String address;//地址

}

package com.ihomefnt.o2o.intf.domain.coupon.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * 卡券类
 * @author Administrator
 *
 */
@Data
public class CenterCouponDto {
	
	private Long couponId;// 券ID
	
	private String couponName;//券名
	
	private String couponDesc;// 券描述
	
	private String couponImage;// 券图片
	
	private Double couponMoney;// 券金额
	
	private Integer couponType;// 券类型 1:现金券 2:抵用券
	
	private String payMoney;//购券所需金额
	
	private String moneyLimitDesc;// 券最低消费限制
	
	private String purchaseLimitDesc;// 券购买商品限制
	
	private String timeStart;//券开始时间
	
	private String timeEnd;//券结束时间
	
	private String timeDesc;// 券使用时间，开始时间到 结束时间 例：2016.5.1-2016.5.7
	
	private Integer status;// 券领取状态 1：未领取 2：已领取
	
	private Integer payment;// 券领取方式 1:免费领取 2:需要购买
	
	private List<CouponRemarkDto> voucherRemarkList;//抵用券使用帮助
	
	private Long maxCount;//抵用券可领的总次数
	
	private Long currentCount;//抵用券已领次数
	
	@JsonIgnore
	private String remark;//券的使用帮助
	
	private String timeStartReceive;//收到券的时间
	
	private String timeEndReceive;//收到券的时间
	
}

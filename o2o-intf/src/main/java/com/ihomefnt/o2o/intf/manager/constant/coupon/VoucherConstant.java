/**
 * 
 */
package com.ihomefnt.o2o.intf.manager.constant.coupon;

/**
 * @author zhang
 *
 */
public interface VoucherConstant {
	
	//抵用券描述
	final String VOUCHER_DESC="产品不限";	
	
	//查询为空
	final String QUERY_EMPTY="查询为空";	
	
	//抵用券状态 1:待确认收款2:已生效3:已使用4:已失效5:使用中
	final Long STATUS_OK =2L;
	final Long STATUS_USED =3L;
	final Long STATUS_USING =5L;
	
	//操作类型 1:注册 2:确认收款 3:已使用 ,4取消，5使用中
	final Long OPERATE_TYPE_USED =3L;
	final Long OPERATE_TYPE_CANCLE =4L;
	final Long OPERATE_TYPE_USING =5L;
	
	//给前台展示用1正常2快过期;3已过期
	
	final Long TIME_OK =1L;
	final Long TIME_QUICK=2L;
	final Long TIME_OVER =3L;
	
	//month 时间
	final Long MONTH_TIME=30*24*3600*1000L;//

}

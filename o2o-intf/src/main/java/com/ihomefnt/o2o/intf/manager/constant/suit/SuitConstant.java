/**
 * 
 */
package com.ihomefnt.o2o.intf.manager.constant.suit;

/**
 * @author zhang<br/>
 * 常量接口类<br/>
 *
 */
public interface SuitConstant {
	
	//返回前台的提示信息
	final String MESSAGE_PARAMETERS_DATA_EMPTY="传入参数为空";	 
	final String MESSAGE_RESULT_DATA_EMPTY="查询结果为空";
	
	//状态: 0删除, 1正常
	final Long IMAGE_STATUS_YES=1L;
	final Long IMAGE_STATUS_NO=0L;
	
	//套装上下架状态 0:下架,1:上架
	final Long SUIT_STATUS_YES=1L;
	final Long SUIT_STATUS_NO=0L;	
	
	//商品上下架状态 0:下架,1:上架
	final Long PRODUCT_STATUS_YES=1L;
	final Long PRODUCT_STATUS_NO=0L;
	
	//0：不是平面设计图, 1：是平面设计图
	final Long DESIGN_STATUS_YES=1L;
	final Long DESIGN_STATUS_NO=0L;
	
	//是否用于图文详情页 1：用于图文详情页
	final Long DETAIL_PAGE_YES=1L;
	
	final double SERVICE_PIC_RATIO=1.29;
	
	//限制数量：3款套装
	final Long LIMIT_COUNT=3L;
		
}

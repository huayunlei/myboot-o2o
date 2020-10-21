package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 电子合同
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
public class QueryContractListResponseVo {
	private Integer orderNum;//订单编号
	
	private String filePath;//文件路径
	
	private Integer type;//合同类型
	
	private String typeName;//合同类型名称
	
	private String createTime;//创建时间
}

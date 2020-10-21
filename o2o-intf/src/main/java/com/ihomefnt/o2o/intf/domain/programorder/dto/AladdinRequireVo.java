package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 需求确认信息
 * @author ZHAO
 */
@Data
public class AladdinRequireVo {
	private Boolean checkResult;//是否需要需求确认
	
	private String planBeginDateStr;//预计开工时间字符串
	
	private String dateStr;//需求确认时间
}

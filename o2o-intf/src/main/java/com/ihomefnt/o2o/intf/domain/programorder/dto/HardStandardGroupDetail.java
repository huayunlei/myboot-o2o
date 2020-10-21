package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.List;

/**
 * 标准分组详情信息
 * @author ZHAO
 */
@Data
public class HardStandardGroupDetail {
private Integer sameFlag;//相同标志
	
	private String spaceName;//空间名称 
	
	private List<String> material;//明细
}

package com.ihomefnt.o2o.intf.domain.lechange.dto;

import lombok.Data;

import java.util.List;

/**
 * 乐橙设备列表
 * @author Charl
 */
@Data
public class PagesVo<T> {
	
	private List<T> list; //设备列表
	private Integer totalRecords; //列表总数
}

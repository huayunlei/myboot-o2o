package com.ihomefnt.o2o.api.controller.lechange.vo;

import java.util.List;

/**
 * 乐橙设备列表
 * @author Charl
 */
public class PagesVo<T> {
	
	private List<T> list; //设备列表
	private Integer totalRecords; //列表总数
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	
	
}

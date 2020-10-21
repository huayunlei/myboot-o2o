package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.Date;

/**
 * DNA 浏览
 * @author ZHAO
 */
@Data
public class DnaVisitResponse {
	private Integer id;
	
	private Integer dnaId;//DNA id
	
	private Date createTime;//添加时间
	
	private Integer visitNum;//浏览量
	
	private Date visitTime;//最后一次访问时间
	
	private Integer visitType;//浏览类型  1DNA  2产品方案

}

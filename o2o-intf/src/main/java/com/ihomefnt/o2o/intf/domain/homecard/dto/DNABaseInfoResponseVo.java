package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.List;
@Data
public class DNABaseInfoResponseVo {
	private List<DNABaseInfoVo> searchResultList;//产品集合
	
	private Integer totalCount;//总共多少条
	
	private Integer totalPageNum;//总共多少页

	public List<DNABaseInfoVo> getSearchResultList() {
		return searchResultList;
	}

}

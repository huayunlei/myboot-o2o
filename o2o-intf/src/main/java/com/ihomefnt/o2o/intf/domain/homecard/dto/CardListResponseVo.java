package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.List;

/**
 * 推荐卡片配置
 * @author ZHAO
 */
@Data
public class CardListResponseVo {
	private List<CardResponseVo> list;//卡片集合
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总共多少条
	
	private Integer totalPage;//总共多少页
}

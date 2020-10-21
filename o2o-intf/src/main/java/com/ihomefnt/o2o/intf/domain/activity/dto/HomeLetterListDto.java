package com.ihomefnt.o2o.intf.domain.activity.dto;

import lombok.Data;

import java.util.List;

/**
 * 1219活动 家书信息
 * @author ZHAO
 */
@Data
public class HomeLetterListDto {
	private List<HomeLetterVo> list;//家书集合
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总共多少条
	
	private Integer totalPage;//总共多少页
}

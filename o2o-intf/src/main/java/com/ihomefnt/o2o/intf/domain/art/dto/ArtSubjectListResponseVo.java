package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.util.List;

/**
 * 艺术品专题
 * @author ZHAO
 */
@Data
public class ArtSubjectListResponseVo {
	private List<SubjectInfoVo> list;//专题集合
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总共多少条
	
	private Integer totalPage;//总共多少页
}

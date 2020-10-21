package com.ihomefnt.o2o.intf.domain.art.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 艺术品专题（分页）返回数据
 * @author ZHAO
 */
@Data
public class ArtSubjectPageResponse {
	private List<ArtSubjectResponse> artSubjectList;
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总条数
	
	private Integer totalPage;//总页数

}

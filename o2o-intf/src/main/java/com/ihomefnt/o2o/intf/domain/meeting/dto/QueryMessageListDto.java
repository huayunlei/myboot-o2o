package com.ihomefnt.o2o.intf.domain.meeting.dto;

import lombok.Data;

import java.util.List;

@Data
public class QueryMessageListDto {

	private List<QueryMessageDto> list;

	private Integer pageNo;// 当前第几页

	private Integer pageSize;// 每页显示多少条

	private Integer totalCount;// 总共多少条

	private Integer totalPage;// 总共多少页
}

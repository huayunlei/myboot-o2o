package com.ihomefnt.o2o.intf.domain.activity.dto;

import lombok.Data;

import java.util.List;

/**
 * 1219活动 三行家书投票记录
 * @author ZHAO
 */
@Data
public class VoteRecordListDto {
	private List<VoteRecordVo> list;//记录集合
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总共多少条
	
	private Integer totalPage;//总共多少页
}

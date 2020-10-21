package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 艺术品数据集合（分页）
 * @author ZHAO
 */
@Data
public class ArtListResponseVo implements Serializable{
	private List<ArtInfoVo> list;
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalRecords;//总共多少条
	
	private Integer totalPages;//总共多少页
}

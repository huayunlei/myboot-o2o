package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.DnaComment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * DNA评论分页查询
 * @author ZHAO
 */
@Data
@ApiModel(value="DnaCommentListResponse",description="DNA评论分页查询")
public class DnaCommentListResponse {
	private List<DnaComment> commentList;//评论内容集合
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总共多少条
	
	private Integer totalPage;//总共多少页
}

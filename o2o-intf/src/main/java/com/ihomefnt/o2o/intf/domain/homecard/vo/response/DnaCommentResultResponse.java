package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.DnaComment;
import lombok.Data;

/**
 * 新增DNA评论
 * @author ZHAO
 */
@Data
public class DnaCommentResultResponse extends DnaComment{
	private Integer commentNum;//评论总数
	
	private String commentPraise;//发表成功文案

}

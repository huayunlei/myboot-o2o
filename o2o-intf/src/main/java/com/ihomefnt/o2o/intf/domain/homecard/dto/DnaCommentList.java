package com.ihomefnt.o2o.intf.domain.homecard.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * DNA详情用户评论版块
 * @author ZHAO
 */
@Data
public class DnaCommentList {
	private String commentTitle = "";//评论版块标题：他们说
	
	private Integer commentNum = 0;//评论条数
	
	private List<DnaComment> commentList = Lists.newArrayList();//评论内容集合

}

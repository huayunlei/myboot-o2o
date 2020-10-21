package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * 评论权限
 * @author ZHAO
 */
@Data
public class CommentLimitResponseVo {
	private Integer id;
	
	private String code;
	
	private String name;
	
	private Integer activeFlag;

}

package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.Date;

/**
 * DNA点赞
 * @author ZHAO
 */
@Data
public class DnaFavoriteResponseVo {
	private Integer id;
	
	private Integer userId;//用户ID
	
	private Integer dnaId;//DNA id
	
	private Date createTime;//添加时间
	
	private Integer favoriteFlag;//点赞标志  0未点赞1已点赞
	
	private Date favoriteTime;//点赞时间

}

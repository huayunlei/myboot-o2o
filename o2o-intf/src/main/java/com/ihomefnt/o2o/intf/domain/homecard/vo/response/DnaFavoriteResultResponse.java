package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import lombok.Data;

/**
 * 点赞成功返回文案
 * @author ZHAO
 */
@Data
public class DnaFavoriteResultResponse {
	private Integer favoriteNum;//点赞总数
	
	private boolean favoriteResult;//点赞是否成功
	
	private String favoritePraise;//点赞成功文案

}

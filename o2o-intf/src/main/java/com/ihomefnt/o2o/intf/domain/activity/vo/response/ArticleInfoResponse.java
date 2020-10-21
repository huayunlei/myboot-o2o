package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import lombok.Data;

/**
 * 投稿文章信息
 * @author ZHAO
 */
@Data
public class ArticleInfoResponse {
	private Integer articleId = -1;//文章ID
	
	private String headImgUrl = "";//头像
	
	private String nickName = "";//昵称
	
	private String content = "";//投稿内容
	
	private Integer voteNum = 0;//投票数

	private String buildingName = "";//楼盘名称

	private Integer rankingNum = 0;//排名
	
	private String mobile = "";//手机号
}

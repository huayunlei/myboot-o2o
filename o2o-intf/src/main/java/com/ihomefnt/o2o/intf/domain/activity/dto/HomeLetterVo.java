package com.ihomefnt.o2o.intf.domain.activity.dto;

import lombok.Data;

@Data
public class HomeLetterVo {
	private Integer articleId;//文章ID
	
	private String headImgUrl;//头像
	
	private String nickName;//昵称
	
	private String content;//投稿内容
	
	private Integer voteNum;//投票数

	private Integer buildingId;//楼盘ID
	
	private String buildingName;//楼盘名称

	private Integer rankingNum;//排名
	
	private String mobile;//手机号
}

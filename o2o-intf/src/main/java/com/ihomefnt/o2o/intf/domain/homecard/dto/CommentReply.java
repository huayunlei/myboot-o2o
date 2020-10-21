package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.Date;

/**
 * 评论回复
 * @author ZHAO
 */
@Data
public class CommentReply {
	private String commentId;//评论ID
	
	private String userHeadImgUrl;//用户头像
	
	private String userNickName;//用户昵称
	
	private Date createTime;//发表日期2017.3.25
	
	private String createTimeDesc;//几天前
	
	private String content;//评论内容
	
	private Integer starNum;//评价星数
	
	private Integer replyUserId;//回复评论用户ID
	
	private String replyNickName;//回复评论用户昵称
	
	private Integer officialFlag;//官方账号标志：1是（艾佳生活）  2不是

}

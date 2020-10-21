package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * DNA用户评论
 * @author ZHAO
 */
@Data
public class DnaComment {
	private Integer commentId;//评论ID
	
	private String userHeadImgUrl;//用户头像
	
	private String userNickName;//用户昵称
	
	private Date createTime;//发表日期2017.3.25
	
	private String createTimeDesc;//几天前
	
	private String content;//评论内容
	
	private Integer starNum;//评价星数
	
	private String mobileHide;//1888****021

	private Integer userId;// 用户ID

	private List<CommentReply> commentReplyList;//评论回复集合
}

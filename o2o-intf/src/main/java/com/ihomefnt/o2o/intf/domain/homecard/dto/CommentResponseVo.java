package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户评论
 * @author ZHAO
 */
@Data
public class CommentResponseVo {
	private String id;
	
	private Integer goodId;
	
	private Integer userId;
	
	private String headImg;
	
	private String mobile;
	
	private String nickName;
	
	private String content;
	
	private Integer starNum;
	
	private Date createTime;
	
	//1DNA、2艺术品、3晒家
	private Integer commentType;
	
	//0未删除，1删除
	private Integer deleteFlag;
	
	private Date deleteTime;

	private Integer replyUserId;//回复评论用户ID
	
	private String replyNickName;//回复评论用户昵称
	
	private String pId;//评论父节点
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

}

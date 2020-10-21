package com.ihomefnt.o2o.intf.domain.meeting.dto;

import lombok.Data;

/**
 * 获取留言
 * 
 * @author czx
 */
@Data
public class QueryMessageDto {
	
	private String nickName; // 昵称

	private String url;// 头像 URL
	
	private String content; // 发布内容
	
	private String msgTime;// 发布时间
}

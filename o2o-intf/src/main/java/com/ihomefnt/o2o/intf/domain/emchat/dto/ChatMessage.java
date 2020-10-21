package com.ihomefnt.o2o.intf.domain.emchat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created by wangxiao on 2015-11-25.
 */
@Data
@NoArgsConstructor
public class ChatMessage {
	
	/*
	 * 主键ID
	 */
	private String id;
	
	/*
	 * 聊天信息ID
	 */
	private String msgId;
	
	/*
	 * 信息发起人
	 */
	private String chatFrom;
	
	/*
	 * 信息接收人
	 */
	private String chatTo;
	
	/*
	 * 群聊时群组ID
	 */
	private String groupId;
	
	/*
	 * 聊天类型：单人或群组
	 */
	private String chatType;
	
	/*
	 * 信息发送时间
	 */
	private Timestamp createTime;
	
	/*
	 * 信息类型
	 */
	private String msgType;
	
	/*
	 * 信息内容
	 */
	private String msg;
	
	/*
	 * 图片、视频等文件存放路径
	 */
	private String url;
	
	/*
	 * 地址信息
	 */
	private String addr;
	
	/*
	 * 地址坐标
	 */
	private String lat;
	
	/*
	 * 地址坐标
	 */
	private String lng;
	
	/*
	 * 文件名
	 */
	private String fileName;
	
	/*
	 * 文件名
	 */
	private String fileurl;
	public ChatMessage(String msgId, String chatFrom, String chatTo,
			String groupId, String chatType, Timestamp createTime,
			String msgType, String msg, String url, String addr, String lat,
			String lng, String fileName, String fileurl) {
		super();
		this.msgId = msgId;
		this.chatFrom = chatFrom;
		this.chatTo = chatTo;
		this.groupId = groupId;
		this.chatType = chatType;
		this.createTime = createTime;
		this.msgType = msgType;
		this.msg = msg;
		this.url = url;
		this.addr = addr;
		this.lat = lat;
		this.lng = lng;
		this.fileName = fileName;
		this.fileurl = fileurl;
	}
}

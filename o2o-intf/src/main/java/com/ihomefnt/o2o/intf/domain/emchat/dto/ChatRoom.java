package com.ihomefnt.o2o.intf.domain.emchat.dto;

import lombok.Data;

/**
 * Created by wangxiao on 2015-11-30.
 */
@Data
public class ChatRoom {
	
	/*
	 * 主键ID
	 */
	private String id;
	
	/*
	 * 聊天室名称
	 */
	private String name;
	
	/*
	 * 聊天室描述
	 */
	private String description;
	
	/*
	 * 最大人数
	 */
	private Integer maxusers;
	
	/*
	 * 管理员
	 */
	private String owner;
	
	/*
	 * 成员
	 */
	private String members;
}

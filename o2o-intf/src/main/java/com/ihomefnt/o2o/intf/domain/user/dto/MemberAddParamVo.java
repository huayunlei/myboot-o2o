/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月25日
 * Description:MemberAddParamVo.java 
 */
package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 */
@Data
public class MemberAddParamVo {

	private Integer userId;// 用户id

	private String nickName;// 昵称

	private String uImg;// 头像

	private Integer gender;// 1.男2.女 0.未知

	private Integer age;// 年龄

	private String brief;// 个人简介
	
	private Date createTime;
	
	private Date updateTime;

	public String getuImg() {
		return uImg;
	}

	public void setuImg(String uImg) {
		this.uImg = uImg;
	}
}

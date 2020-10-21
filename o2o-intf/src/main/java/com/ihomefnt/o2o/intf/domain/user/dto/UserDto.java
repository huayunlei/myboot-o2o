/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月18日
 * Description:UserVo.java 
 */
package com.ihomefnt.o2o.intf.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class UserDto {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 用户类型：1.普通用户 2.管理员
	 */
	private Short type;

	/**
	 * 状态：0.未激活 1.可用 2.锁定
	 */
	private Integer status;

	/**
	 * 微信id
	 */
	private String wechatId;

	/**
	 * 会员信息
	 */
	private MemberDto member;

	/**
	 * 设计师信息
	 */
	private DesignerVo designer;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 用户角色
	 */
	private List<RoleDto> roles;


}

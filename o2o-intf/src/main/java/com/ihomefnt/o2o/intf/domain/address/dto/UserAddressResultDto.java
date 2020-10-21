/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月24日
 * Description:UserAddressResultVo.java 
 */
package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 */
@Data
public class UserAddressResultDto {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 省id
	 */
	private Integer provinceId;

	/**
	 * 城市id
	 */
	private Integer cityId;

	/**
	 * 区县id
	 */
	private Integer countryId;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 是否默认地址 0 不是 1是
	 */
	private Boolean isDefault;

	/**
	 * 邮政编号
	 */
	private String postCode;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话号码
	 */
	private String telephone;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 收货人姓名
	 */
	private String consignee;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}

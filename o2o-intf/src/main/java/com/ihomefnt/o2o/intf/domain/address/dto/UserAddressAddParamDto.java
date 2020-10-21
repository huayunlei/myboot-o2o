/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月24日
 * Description:UserAddressAddParamVo.java 
 */
package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhang
 */
@Data
public class UserAddressAddParamDto {

	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空")
	private Integer userId;

	/**
	 * 省id
	 */
	@NotNull(message = "省份id不能为空")
	private Integer provinceId;

	/**
	 * 城市id
	 */
	@NotNull(message = "城市id不能为空")
	private Integer cityId;

	/**
	 * 区县id
	 */
	@NotNull(message = "区县id不能为空")
	private Integer countryId;

	/**
	 * 详细地址
	 */
	@NotNull(message = "详细地址不能为空")
	private String address;

	/**
	 * 是否默认地址 0不是 1是
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
	@NotNull(message = "手机号码不能为空")
	private String mobile;

	/**
	 * 收货人姓名
	 */
	@NotNull(message = "收货人姓名不能为空")
	private String consignee;
}

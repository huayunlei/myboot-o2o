/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月23日
 * Description:ArtistRegisterResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class ArtistRegisterResponse {

	private Long code;

	private String token;

	private Integer userId;

	private boolean success;

	private String msg;
}

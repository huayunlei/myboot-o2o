/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月16日
 * Description:ArtistLoginResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class ArtistLoginResponse {

	private String accessToken;

	private String mobile;// 手机号
}

package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

@Data
public class LogoutParamVo {

	private String token; // 登录token

	private String loginIp;// 登录id

	private String deviceId;// 设备id

	private Integer osType;// 设备类型

}

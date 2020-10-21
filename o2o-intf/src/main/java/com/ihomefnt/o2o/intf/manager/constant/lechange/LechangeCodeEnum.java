/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月7日
 * Description:LechangeCodeEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.lechange;

/**
 * @author zhang
 */
public enum LechangeCodeEnum {

	SN1001("签名异常", "SN1001"), SN1002("签名超时", "SN1002"), SN1003("签名参数错误", "SN1003"), OP1002("参数缺失，请确认参数是否有缺失", "OP1002"), OP1003(
			"参数取值不合法(参数格式有误或为空)，请修正参数值", "OP1003"), OP1007("无效的方法调用", "OP1007"), DV1001("设备已被其他账号绑定", "DV1001"), DV1002(
			"设备不存在", "DV1002"), DV1003("设备已被当前账号绑定", "DV1003"), DV1005("设备验证码错误", "DV1005"), DV1007("设备离线", "DV1007"), DV1013(
			"设备注册3天后绑定,客户端与设备必须在同一个局域网内", "DV1013"), DV1014("设备绑定错误连续超过10次，限制绑定30分钟", "DV1014"), DV1015(
			"设备绑定错误连续超过20次，限制绑定24小时", "DV1015"), CODE_0("操作成功", "0"), CODE_1("请输入正确的序列号和安全码", "-1"), CODE_2("操作失败",
			"-2") ,CODE_NULL("参数为空", "-3");

	private String msg;

	private String code;

	private LechangeCodeEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(String code) {
		for (LechangeCodeEnum c : LechangeCodeEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.msg;
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

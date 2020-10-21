package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class WechatAppletRegisterParamVo implements Serializable{

	private static final long serialVersionUID = -8904902740165522739L;

	private String mobile;// 手机号码

	/**用户来源
     *1.BOSS
     2.IOS
     3.Android
     4.M站
     5.PC站
     6.WeChat
     7.活动
     */
	private int source;// 用户来源

	private String wechatId;// 微信号

	private String wecharNickName;// 微信昵称
	
	private String uImg;//头像

	public String getuImg() {
		return uImg;
	}

	public void setuImg(String uImg) {
		this.uImg = uImg;
	}

}

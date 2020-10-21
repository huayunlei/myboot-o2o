package com.ihomefnt.o2o.intf.domain.user.vo.response;

import lombok.Data;

/**
 * 检查用户信息是否完全
 * @author Charl
 */
@Data
public class CheckUserInfoResponseVo {
	
	private boolean avast; //用户头像：true 存在 ; false 不存在
	
	private boolean nickName; //用户昵称：true 存在 ; false 不存在
	
	private boolean userAddress; //用户地址：true 存在 ; false 不存在
	
	private boolean infoComplete; //用户信息是否完全：true 是; false 否

	private boolean idCardCertification;//用户是否已实名认证：true 是；false 否
}

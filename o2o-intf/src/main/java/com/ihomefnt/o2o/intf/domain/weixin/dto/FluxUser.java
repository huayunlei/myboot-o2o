/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.weixin.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author zhang
 *
 */
@Data
public class FluxUser {
	
	private Long userId; //主键
	
	private String openId;//微信openId
	
	private String unionId; //微信unionId
	
	private String nickName;//用户昵称
	
	private Integer status ;//关注类型：1 关注 0 非关注
	
	private Timestamp createTime;//请求时间

}

package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 小星星用户登录返回值
 */
@Data
@ApiModel("小星星用户登录返回参数")
public class StarUserLoginResponse {
	
	@ApiModelProperty("accessToken")
	private String accessToken = "";
	
	@ApiModelProperty("姓名")
	private String userName = "";//姓名
	
	@ApiModelProperty("手机号码")
	private String mobile = "";//手机号
	
	@ApiModelProperty("昵称")
	private String nickName = "";//昵称
	
	@ApiModelProperty("头像")
	private String uImg = "";//头像
	
	@ApiModelProperty("性别1.男2.女 0.未知")
	private Integer gender = 0;//性别1.男2.女 0.未知
	
	@ApiModelProperty("年龄")
	private Integer age = 0;//年龄
	
	@ApiModelProperty("标识")
	private Integer flag = 0;// 标识
}

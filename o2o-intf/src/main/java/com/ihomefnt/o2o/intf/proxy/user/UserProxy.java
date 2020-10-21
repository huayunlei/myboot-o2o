package com.ihomefnt.o2o.intf.proxy.user;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistListDto;
import com.ihomefnt.o2o.intf.domain.user.dto.IdCardCertifincationDto;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserRealNameCertifincationDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserIdResultDto;
import com.ihomefnt.o2o.intf.domain.user.vo.response.CertificationResultResponseVo;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtistListRequest;
import com.ihomefnt.o2o.intf.domain.user.dto.LoginParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.LoginResultVo;
import com.ihomefnt.o2o.intf.domain.user.dto.LogoutParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberAddParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberUpdateParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.MobileRegisterParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.SmsCodeRegisterVo;
import com.ihomefnt.o2o.intf.domain.user.dto.SmsLoginUserParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.WechatAppletRegisterParamVo;

/** 
* @ClassName: UserProxy 
* @Description: 用户信息模块接口服务代理层 
* @author huayunlei
* @date Feb 18, 2019 10:11:03 AM 
*  
*/
public interface UserProxy {

	/** 
	* @Title: loginByPassword 
	* @Description: 根据用户和密码登录,返回token
	* @param @param loginUser
	* @return String    返回类型 
	* @throws 
	*/
	String loginByPassword(LoginParamVo loginUser);

	/** 
	* @Title: getUserByToken 
	* @Description: 根据token查询用户信息
	* @param @param token
	* @return UserVo    返回类型 
	* @throws 
	*/
	UserDto getUserByToken(String token);

	/** 
	* @Title: checkNickNameExist 
	* @Description: 判断昵称是否存在 
	* @param @param nickName
	* @return boolean    返回类型 
	* @throws 
	*/
	boolean checkNickNameExist(String nickName);

	/** 
	* @Title: addMemberInfo 
	* @Description: 普通用户手机注册时没有会员信息，用户想完善信息时调用此接口，比如添加昵称、上传头像等
	* @param @param params
	* @return Integer    返回类型 
	* @throws 
	*/
	Integer addMemberInfo(MemberAddParamVo params);

	/** 
	* @Title: updateMemberInfo 
	* @Description: 修改会员信息
	* @param @param params
	* @return Integer    返回类型 
	* @throws 
	*/
	Integer updateMemberInfo(MemberUpdateParamVo params);

	/** 
	* @Title: loginBySmsCode 
	* @Description: 根据用户和手机验证码,返回token
	* @param @param params
	* @return LoginResultVo    返回类型 
	* @throws 
	*/
	LoginResultVo loginBySmsCode(SmsLoginUserParamVo params);

	/** 
	* @Title: logout 
	* @Description: 退出登录
	* @param @param params
	* @return boolean    返回类型 
	* @throws 
	*/
	boolean logout(LogoutParamVo params);

	/** 
	* @Title: checkLegalUser 
	* @Description: 校验用户token合法性
	* @param @param token
	* @param @return  参数说明 
	* @return boolean    返回类型 
	* @throws 
	*/
	boolean checkLegalUser(String token);

	/** 
	* @Title: registerBySmsCode 
	* @Description: 通过手机验证码用户注册,返回用户id
	* @param @param smsCodeRegisterDto
	* @return UserIdResultVo    返回类型 
	* @throws 
	*/
	UserIdResultDto registerBySmsCode(SmsCodeRegisterVo smsCodeRegisterDto);

	/** 
	* @Title: getUserByMobile 
	* @Description: 根据用户手机号查询用户信息
	* @param @param mobile
	* @return UserVo    返回类型 
	* @throws 
	*/
	UserDto getUserByMobile(String mobile);

	/** 
	* @Title: queryCertifincationResult 
	* @Description: 查询实名认证结果 
	* @param @param userId
	* @return IdCardCertifincationResponseVo    返回类型 
	* @throws 
	*/
	IdCardCertifincationDto queryCertifincationResult(Integer userId);

	/** 
	* @Title: setPassword 
	* @Description: 修改用户密码
	* @param @param userId
	* @param @param password
	* @return boolean    返回类型 
	* @throws 
	*/
	boolean setPassword(Integer userId, String password);

	/** 
	* @Title: realNameCertifincation 
	* @Description: 用户实名认证
	* @param @param certifincationParams
	* @return CertificationResultResponseVo    返回类型 
	* @throws 
	*/
	CertificationResultResponseVo realNameCertifincation(UserRealNameCertifincationDto certifincationParams);
	
	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	UserDto getUserById(Integer id);
	
	/**
	 * 根据用户id集合查询用户信息集合
	 * 
	 * @param list
	 * @return
	 */
	List<UserDto> batchQueryUserInfo(List<Integer> list);
	
	/**
	 * 根据手机号查询经纪人token
	 * @param mobile
	 * @return
	 * Author: ZHAO
	 * Date: 2018年6月25日
	 */
	Map<String, Object> getAgentTokenByMobile(String mobile);

	StarArtistListDto getStarArtistList(StarArtistListRequest request);

	/**
	 * @param userName
	 * @param roleCode
	 * @return
	 */
	List<MemberDto> getUserInfoByNameAndCode(String userName, String roleCode);

	/** 微信小程序注册
	 * @param vo
	 * @return
	 */
	UserIdResultDto registerByWeChatApplet(WechatAppletRegisterParamVo vo);

	/**根据userId获取小星星小程序的openId
	 * @param userId
	 * @return
	 */
	String getNewStarMpOpenId(Integer userId);
	
	/** 
	* @Title: register 
	* @Description: 用户注册（手机号）
	* @param @param params
	* @return UserIdResultVo    返回类型 
	* @throws 
	*/
	UserIdResultDto register(MobileRegisterParamVo params);

}

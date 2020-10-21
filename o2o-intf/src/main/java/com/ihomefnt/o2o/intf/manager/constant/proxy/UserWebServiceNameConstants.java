package com.ihomefnt.o2o.intf.manager.constant.proxy;

/** 
* @ClassName: UserWebServiceNameConstants 
* @Description: user-web服务名称常量池
* @author huayunlei
* @date Jan 30, 2019 4:26:48 PM 
*  
*/
public interface UserWebServiceNameConstants {
	
	/**
	 * 根据用户ID查询用户地址List
	 */
	String QUERY_USER_ADDRESS_LIST = "user-web.address.queryUserAddressList";
	
	/**
	 * 根据账户登录
	 */
	String LOGIN_BY_USER_PSD = "user-web.user.loginByPassword";
	
	/**
	 * 根据token获取用户信息
	 */
	String GET_USER_BY_TOKEN = "user-web.user.getUserByToken";
	
	/**
	 * 判断昵称是否存在
	 */
	String CHECK_NICKNAME_EXIST = "user-web.user.checkNickNameExist";
	
	/**
	 * 添加会员信息
	 */
	String ADD_MEMBER_INFO = "user-web.user.addMemberInfo";
	
	/**
	 * 修改会员信息
	 */
	String UPDATE_MEMBER = "user-web.user.updateMember";
	
	/**
	 * 根据用户和手机验证码,返回token
	 */
	String LOGIN_BY_SMS_CODE = "user-web.user.loginBySmsCode";
	
	/**
	 * 根据用户和手机验证码注册
	 */
	String REGISTER_BY_SMS_CODE = "user-web.user.registerBySmsCode";
	
	/**
	 * 用户退出登录
	 */
	String USER_LOGOUT = "user-web.user.logout";
	
	/**
	 * 校验用户token合法性
	 */
	String CHECK_LEGAL_USER = "user-web.user.checkLegalUser";
	
	/**
	 * 根据用户手机号查询用户信息
	 */
	String GET_USER_BY_MOBILE = "user-web.user.getUserByMobile";
	
	/**
	 * 查询实名认证结果 
	 */
	String QUERY_CERTIFINCATION_RESULT = "user-web.verification.queryCertifincationResult";
	
	/**
	 * 修改用户密码
	 */
	String USER_SET_USER_PSD = "user-web.user.setPassword";
	
	/**
	 * 用户实名认证
	 */
	String REALNAME_CERTIFINCATION = "user-web.verification.realNameCertifincation";
	
	/**
	 * 根据用户id查询用户信息
	 */
	String GET_USER_BY_ID = "user-web.user.getUserById";
	
	/**
	 * 根据用户id集合查询用户信息集合
	 */
	String BATCH_QUERY_USER_INFO = "user-web.user.batchQueryUserInfo";
	
	/**
	 * 根据手机号查询经纪人token
	 */
	String GET_AGENT_TOKEN_BY_MOBILE = "user-web.user.getAgentTokenByMobile";
	
	/**
	 * 查询小星星艺术家列表
	 */
	String GET_STARARTIST_LIST = "user-web.user.getStarArtistList";
	
	/**
	 * 根据用户名和角色查询用户信息
	 */
	String GET_USER_INFO_BY_NAME_AND_CODE = "user-web.user.getUserInfoByNameAndCode";
	
	/**
	 * 微信小程序注册
	 */
	String REGISTER_BY_WECHAT_APPLET = "user-web.user.registerByWeChatApplet";
	
	/**
	 * 根据userId获取小星星小程序的openId
	 */
	String GET_NEWSTAR_MP_OPENID = "user-web.user.getNewStarMpOpenId";
	
	/**
	 * 手机号注册
	 */
	String USER_REGISTER_BY_MOBILE = "user-web.user.register";
	
	/**
	 * 根据用户id查询默认收货地址
	 */
	String ADDRESS_QUERY_DEFAULT_BY_USER_ID = "user-web.address.queryDefaultByUserId";
			
	/**
	 * 根据地址id更新地址
	 */
	String ADDRESS_UPDATE_BY_ID = "user-web.address.updateById";
	
	/**
	 * 增加用户收货地址
	 */
	String ADDRESS_ADD_USER_ADDRESS = "user-web.address.addUserAddress";
	
	/**
	 * 根据用户id查询到所有收货地址列表
	 */
	String ADDRESS_QUERY_USER_ADDRESS_LIST = "user-web.address.queryUserAddressList";
	
}

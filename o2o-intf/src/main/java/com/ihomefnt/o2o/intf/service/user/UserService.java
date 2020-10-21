package com.ihomefnt.o2o.intf.service.user;

import com.ihomefnt.o2o.intf.domain.user.doo.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.user.vo.request.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.*;
import com.ihomefnt.o2o.intf.manager.exception.UserNotExistException;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.user.dto.TUserRelationResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: UserService 
* @Description: 用户信息service层 
* @author huayunlei
* @date Feb 18, 2019 9:52:42 AM 
*  
*/
public interface UserService {

	/** 
	* @Title: login 
	* @Description: 根据用户和密码登录,返回token
	* @param @param req
	* @param @param loginRequest
	* @param @return  参数说明 
	* @return LoginResponse    返回类型 
	* @throws 
	*/
	LoginResponseVo loginByPassword(HttpServletRequest req,LoginRequestVo loginRequest);

    /** 
    * @Title: refreshUserTag 
    * @Description: 更新及获取用户tag
    * @param @param userId
    * @param @param loginRequest
    * @param @return  参数说明 
    * @return List<String>    返回类型 
    * @throws 
    */
    List<String> refreshUserTag(Long userId, LoginRequestVo loginRequest);

	/** 
	* @Title: changeNick 
	* @Description: 修改昵称 
	* @param @param request
	* @param @return  参数说明 
	* @return void    返回类型 
	* @throws 
	*/
	void changeNick(ChangeNickRequestVo request);

	/** 
	* @Title: registerByMobileAndPassword 
	* @Description: 用户注册（手机号、密码）
	* @param @param registerRequest
	* @return RegisterResponse    返回类型 
	* @throws 
	*/
	RegisterResponseVo registerByMobileAndPassword(RegisterRequestVo registerRequest);

	/** 
	* @Title: resendsms 
	* @Description: 注册，重新发送短信
	* @param @param sendSmsRequest
	* @return void    返回类型 
	* @throws 
	*/
	void resendsms(ReSendSmsRequestVo sendSmsRequest);

	/** 
	* @Title: registerValidateSms 
	* @Description: 注册验证短信 
	* @param @param registerValidateRequest
	* @return LoginResponse    返回类型 
	* @throws 
	*/
	LoginResponseVo registerValidateSms(RegisterValidateRequestVo registerValidateRequest);
	
	/**
     * @return false: user not exist, or user and password does not match true: user exist
     */
    LogDo auth(String mobile, String password);

	/** 
	* @Title: retrievepass 
	* @Description: 初始化密码
	* @param @param retrievePassRequest
	* @return RetrievePassResponse    返回类型 
	* @throws 
	*/
	RetrievePassResponseVo retrievepass(RetrievePassRequestVo retrievePassRequest);

	/** 
	* @Title: retriveValidate 
	* @Description: 重置密码验证短信 
	* @param @param retrieveValidateRequest
	* @return void    返回类型 
	* @throws 
	*/
	void retriveValidate(RetrieveValidateRequestVo retrieveValidateRequest);

	/** 
	* @Title: resetPass 
	* @Description: 重置修改密码
	* @param @param resetPassRequest
	* @return LoginResponse    返回类型 
	* @throws 
	*/
	LoginResponseVo resetPass(ResetPassRequestVo resetPassRequest);

	/** 
	* @Title: logout 
	* @Description: 退出登录
	* @param @param logoutRequest
	* @return void    返回类型 
	* @throws 
	*/
	void logout(LogoutRequestVo logoutRequest);

	/** 
	* @Title: addFeedback 
	* @Description: 添加用户反馈
	* @param @param feedbackRequest
	* @return HttpFeedbackResponse    返回类型 
	* @throws 
	*/
	FeedbackResponseVo addFeedback(FeedbackRequestVo feedbackRequest);

	/** 
	* @Title: checkAccessToken 
	* @Description: 检查用户合法性 
	* @param @param request
	* @return void    返回类型 
	* @throws 
	*/
	void checkAccessToken(HttpBaseRequest request);

	/** 
	* @Title: sendlogsmsV2 
	* @Description: 登录发送短信 
	* @param @param req
	* @param @param request
	* @return void    返回类型 
	* @throws 
	*/
	Integer sendlogsmsV2(HttpServletRequest req, SendLoginSmsRequestVo request);

	/** 
	* @Title: sendlogsms 
	* @Description: 登录发送短信
	* @param @param req
	* @param @param request
	* @return void    返回类型 
	* @throws 
	*/
	Integer sendlogsms(HttpServletRequest req, SendLoginSmsRequestVo request);

	/**
	 * @param openTagShow  
	* @Title: newvalidelogsms 
	* @Description: 短信注册或登录 
	* @param @param req
	* @param @param request
	* @param @param openTagShow
	* @return LoginResponse    返回类型 
	* @throws 
	*/
	LoginResponseVo newvalidelogsms(HttpServletRequest req, ValidateLoginSmsRequestVo request, String openTagShow);

	/** 
	* @Title: queryCertifincationResult 
	* @Description: 查询实名认证信息
	* @param @param userId
	* @return IdCardCertifincationResponse    返回类型 
	* @throws 
	*/
	IdCardCertifincationResponseVo queryCertifincationResult(Integer userId);

	/** 
	* @Title: validelogsms 
	* @Description: 短信注册或登录
	* @param @param req
	* @param @param request
	* @param @param openTagShow
	* @return LoginResponse    返回类型 
	* @throws 
	*/
	LoginResponseVo validelogsms(HttpServletRequest req, ValidateLoginSmsRequestVo request, String openTagShow);

	/**
	* @Title: setsmspass 
	* @Description: 设置密码 
	* @param @param req
	* @param @param request
	* @param openTagShow  
	* @return LoginResponse    返回类型 
	* @throws 
	*/
	LoginResponseVo setsmspass(HttpServletRequest req, SMSSetPassRequestVo request, String openTagShow);

	/** 
	* @Title: changeavatar 
	* @Description: 更改用户头像
	* @param @param request  参数说明
	* @return UserAvatarResponseVo    返回类型
	* @throws 
	*/
	UserAvatarResponseVo changeavatar(ChangeAvatarRequestVo request);

	/** 
	* @Title: getUserAvatar 
	* @Description: 获取用户头像 
	* @param @param request
	* @return UserAvatarResponseVo    返回类型 
	* @throws 
	*/
	UserAvatarResponseVo getUserAvatar(HttpBaseRequest request);

	/** 
	* @Title: checkUserInfo 
	* @Description: 检查用户信息 
	* @param @param request
	* @return HttpCheckUserInfo    返回类型 
	* @throws 
	*/
	CheckUserInfoResponseVo checkUserInfo(HttpBaseRequest request);
	
	/** 
	* @Title: judgeUserIsCertification 
	* @Description: 判断用户是否已实名认证
	* @param @param userId
	* @return boolean    返回类型 
	* @throws 
	*/
	boolean judgeUserIsCertification(Integer userId);

	/** 
	* @Title: completeUserInfo 
	* @Description: 用户完善信息，充值50艾积分
	* @param @param request
	* @return AjbBillNo    返回类型 
	* @throws 
	*/
	AjbBillNoResponseVo completeUserInfo(CompleteUserRequestVo request);

	/** 
	* @Title: judgeUserIsRegister 
	* @Description: 查询用户是否已注册
	* @param @param request
	* @return UserIsRegisterResponseVo    返回类型 
	* @throws 
	*/
	UserIsRegisterResponseVo judgeUserIsRegister(LoginRequestVo request);

	/** 
	* @Title: setUserIdCard 
	* @Description: 用户实名认证
	* @param @param request
	* @return IdCardCertifincationResponse    返回类型 
	* @throws 
	*/
	IdCardCertifincationResponseVo setUserIdCard(UserIdCardRequestVo request);

	/**
	* @Title: loginInWeChatH5 
	* @Description: 微信H5活动登录
	* @param req  
	* @param @param request
	* @return String    返回类型 
	* @throws 
	*/
	String loginInWeChatH5(HttpServletRequest req, ValidateLoginSmsRequestVo request);
	
	
	
	
	
	/**
     * M站接口
     */
	
    UserDo queryUserInfo(long userId);
    
    /**
     * 根据iphone去查找用户信息
     *
     * @param mobile
     * @return
     */
    UserDo queryUserInfoByMobile(String mobile);
    
    /**
     * 根据别名来查询
     * @param nickName
     * @return
     */
    UserDo queryUserByNickName(String nickName);
    
    LogDo validateSms(String mobile, String registerKey, String activateCode, HttpBaseRequest request);
    
    boolean retrieveValidate(String mobile, String registerKey, String activateCode);

    boolean resendSms(String mobile, String registerKey);

    String retrievePassword(String mobile) throws UserNotExistException;

    LogDo resetPassword(String mobile, String registerKey, String password) throws UserNotExistException;
    
    LogDo isLoggedIn(String accessToken);

    /**
     * 插入用户绑定 推荐关系
     *
     * @param userRelation
     * @return
     */
    int createUserRelation(UserRelationDo userRelation);

    /**
     * 查询
     *
     * @param userRelation
     * @return
     */
    int queryUserRelation(UserRelationDo userRelation);

    /**
     * 更新
     *
     * @param userRelation
     * @return
     */
    int updateUserRelation(UserRelationDo userRelation);
    
    /**
     * 查询我的钱袋子
     *
     * @param map
     * @return
     */
    List<WalletDo> queryMyWallet(Map<String, Object> map);

    /**
     * 查询我的受邀用户
     *
     * @param map
     * @return
     */
    List<TUserRelationResponse> queryMyInvitedUsers(Map<String, Object> map);

    /**
     * 我的钱袋子余额
     *
     * @param map
     * @return
     */
    Double queryMyWalletSum(Map<String, Object> map);

    /**
     * 统计
     *
     * @param map
     * @return
     */
    int queryMyInvitedUsersCount(Map<String, Object> map);

    /**
     * 绑定用户的业务逻辑
     *
     * @param userRelation
     * @return
     */
    Long bindingUser(UserRelationDo userRelation);

    /**
     * 我的钱袋子 总记录数
     *
     * @param mobile
     * @return
     */
    Long querMyWalletCount(String mobile);
    
    /**
     * 统一处理用户的状态
     *
     * @param
     */
    void unboundRelationShip();

    boolean logoutByAccessToken(String accessToken);

    void addAccessLog(Long userId, Integer osType);
    
    /**
	 * 记录用户关键点访问记录.
	 * @param userVisitLog
	 * @return
	 */
	Integer saveUserVisitLog(UserVisitLogDo userVisitLog);
    
	/** 
	* @Title: getUserByToken 
	* @Description: 根据token查询用户信息
	* @param @param token
	* @return UserVo    返回类型 
	* @throws 
	*/
	UserDto getUserByToken(String token);
}

package com.ihomefnt.o2o.intf.dao.user;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.domain.user.doo.RegisterDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserRelationDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserRelationInfoDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserVisitLogDo;
import com.ihomefnt.o2o.intf.domain.user.doo.WalletDo;

/**
 * 
 * 功能描述：  演示持久层DAO
 * @author piweiwen@126.com
 */
public interface UserDao {
	
	/**
	 * 获取此IP登陆信息
	 * @param ip
	 * @return
	 */
	int getLoginLogCountByIp(String ip);
	
	long addLog(LogDo log);
	LogDo queryLogByUId(Long uId);
	LogDo isLoggedIn(String accessToken);
	long  addUser (UserDo user);
	void deleteRegister(long rId);
	boolean deleteLog(String refreshToken);

	UserDo queryUser(long userId);
	
    /**
     * 根据别名来查询
     * @param nickName
     * @return
     */
    UserDo queryUserByNickName(String nickName);

	void updatePassword(String mobile,String password);
    void updateSmsCode(UserDo user);
	UserDo queryUserByMobilePassword(String mobile,String password);
	void  addRegistration(String mobile, String password,String activateCode,String registerKey);
	RegisterDo queryRegistration(String mobile, String registerKey, String activateCode);
	void updateRegActivateCode(String mobile, String registerKey ,String activateCode);
	/**
     * 根据iphone去查找用户信息
     * @param mobile
     * @return
     */
	UserDo queryUserInfoByMobile(String mobile);
	/**
	 * 建立 绑定推荐关系 
	 * @param userRelation
	 * @return
	 */
	
	int createUserRelation(UserRelationDo userRelation);
	List<UserRelationDo> queryUserRelation(UserRelationDo userRelation);
	int updateUserRelation(UserRelationDo userRelation);
	/**
	 * 查询钱袋子
	 * @param map
	 * @return
	 */
	List<WalletDo> queryMyWallet(Map<String, Object> map );
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<UserRelationInfoDo> queryMyInvitedUsers(Map<String, Object> map);
	/**
	 * 
	 * @param map
	 * @return
	 */
	
	Double queryMyWalletSum(Map<String, Object> map);
	int queryMyInvitedUsersCount(Map<String, Object> map);
	/**
	 * 根据注册用户电话号码得到用户的绑定关系
	 * 1.有绑定关系
	 * 2.或者没有
	 * @param map
	 * @return
	 */
	UserRelationDo queryInvitedUserByRegMobile(Map<String, Object> map);
	/**
	 * 插入钱袋子
	 * @param wallet
	 * @return
	 */
	Long saveWallet(WalletDo wallet);
	Long querMyWalletCount(String mobile);
	void unboundRelationShip();
	
	boolean deleteLogByAccessToken(String accessToken);
	
	void addAccessLog(Long userId, Integer osType);
	
	/**
	 * 查询用户标签
	 * @return
	 */
	List<String> queryUserTag(Map<String, Object> param);
	
	/**
	 * 更新用户标签
	 * @return
	 */
	int updateUserTag(Map<String, Object> param);
	
	/**
	 * 添加用户标签
	 * @return
	 */
	int addUserTag(Map<String, Object> param);
	
	/**
	 * 根据用户id来更新昵称
	 * @param userId
	 * @param nickName
	 * @return
	 */
	int updateUserNickById(Long userId,String nickName);
	
	/**
	 * 记录用户关键点访问记录.
	 * @param userVisitLog
	 * @return
	 */
    Integer saveUserVisitLog(UserVisitLogDo userVisitLog);
}

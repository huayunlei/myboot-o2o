package com.ihomefnt.o2o.service.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.user.UserDao;
import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.domain.user.doo.RegisterDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserRelationDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserRelationInfoDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserVisitLogDo;
import com.ihomefnt.o2o.intf.domain.user.doo.WalletDo;

/**
 * 功能描述
 * ：
 * 演示持久层DAO实现
 *
 * @author 作者
 *         12074272
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.user.UserDao";

    @Override
    public long addUser(UserDo user) {
        LOG.info("UserDao.addUser() Start");
        /**
         * 0
         * or
         * 1,
         * 1
         * means
         * success,
         * is
         * want
         * to
         * return
         * primary
         * key,
         * should
         * config
         * mapper_user
         * .
         * xml
         */
        return sqlSessionTemplate.insert(NAME_SPACE + ".addUser", user);
    }

    @Override
    public UserDo queryUser(long userId) {
        LOG.info("UserDao.queryUser() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryUser", paramMap);
    }

    @Override
    public UserDo queryUserByMobilePassword(String mobile, String password) {
        LOG.info("UserDao.queryUserByMobilePassword() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        paramMap.put("password", password);
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryUserByMobilePassword", paramMap);
    }

    @Override
    public void addRegistration(String mobile, String password, String activateCode,
            String registerKey) {
        LOG.info("UserDao.addRegistration() start");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        paramMap.put("registerKey", registerKey);
        paramMap.put("password", password);
        paramMap.put("activateCode", activateCode);

        sqlSessionTemplate.insert(NAME_SPACE + ".addRegistration", paramMap);
    }

    /**
     * @param mobile
     * @param registerKey
     * @param activateCode
     * @return
     */
    @Override
    public RegisterDo queryRegistration(String mobile, String registerKey, String activateCode) {

        LOG.info("UserDao.queryRegistration() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        paramMap.put("registerKey", registerKey);
        paramMap.put("activateCode", activateCode);

        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryRegistration", paramMap);
    }

    @Override
    public void updateRegActivateCode(String mobile, String registerKey, String activateCode) {
        LOG.info("UserDao.updateRegActivateCode() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        paramMap.put("registerKey", registerKey);
        paramMap.put("activateCode", activateCode);

        sqlSessionTemplate.update(NAME_SPACE + ".updateActivateCode", paramMap);
    }

    @Override
    public void deleteRegister(long rId) {
        LOG.info("UserDao.deleteRegister() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("rId", rId);
        sqlSessionTemplate.delete(NAME_SPACE + ".deleteRegister", paramMap);
    }

    @Override
    public void updatePassword(String mobile, String password) {
        LOG.info("UserDao.updatePassword() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        paramMap.put("password", password);
        sqlSessionTemplate.update(NAME_SPACE + ".updatePassword", paramMap);
    }

    @Override
    public long addLog(LogDo log) {
        LOG.info("UserDao.addLog() Start");
        return sqlSessionTemplate.insert(NAME_SPACE + ".addLog", log);
    }

    @Override
    public boolean deleteLog(String refreshToken) {
        LOG.info("UserDao.addUser() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("refreshToken", refreshToken);
        return sqlSessionTemplate.delete(NAME_SPACE + ".deleteLog", paramMap) > 0;
    }

    /**
     * if
     * TLog
     * is
     * not
     * null,
     * it
     * means
     * user
     * exists
     * and
     * login
     * status
     *
     * @param accessToken
     * @return
     */
    @Override
    public LogDo isLoggedIn(String accessToken) {
        LOG.info("interface isLoggedIn() start in Dao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("accessToken", accessToken);
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryLogByToken", paramMap);
    }

    @Override
    public LogDo queryLogByUId(Long uId) {
        LOG.info("UserDao.queryLogByUId() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("uId", uId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryLogByUid", paramMap);
    }

    @Override
    public UserDo queryUserInfoByMobile(String mobile) {
        LOG.info("UserDao.queryUserInfoByMobile() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryUserByMobile", paramMap);
    }

    @Override
    public int createUserRelation(UserRelationDo userRelation) {
        LOG.info("UserDao.createUserRelation() Start");
        /**
         * 0
         * or
         * 1,
         * 1
         * means
         * success,
         * is
         * want
         * to
         * return
         * primary
         * key,
         * should
         * config
         * mapper_user
         * .
         * xml
         */
        return sqlSessionTemplate.insert(NAME_SPACE + ".addUserRelation", userRelation);
    }

    @Override
    public List<UserRelationDo> queryUserRelation(UserRelationDo userRelation) {
        LOG.info("UserDao.queryUserRelation() Start");
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryUserRelation", userRelation);
    }

    @Override
    public int updateUserRelation(UserRelationDo userRelation) {
        LOG.info("UserDao.updateUserRelation() Start");
//		 Map<String, String> map = new HashMap<String, String>();
//		 map.put("status", userRelation.getStatus().toString());
//		 map.put("invitedmobile", userRelation.getInvitedmobile());
        return sqlSessionTemplate.update(NAME_SPACE + ".updateUserRelation", userRelation);
    }

    @Override
    public List<WalletDo> queryMyWallet(Map<String, Object> map) {
        LOG.info("UserDao.queryMyWallet() Start");
//		 return sqlSessionTemplate.selectList(NAME_SPACE + ".queryMyWallet", map);
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryMyWalletFromOrder", map);
    }

    @Override
    public List<UserRelationInfoDo> queryMyInvitedUsers(Map<String, Object> map) {
        LOG.info("UserDao.queryMyInvitedUsers() Start");
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryMyInvitedUsers", map);
    }

    @Override
    public Double queryMyWalletSum(Map<String, Object> map) {
        LOG.info("UserDao.queryMyWalletSum() Start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryMyWalletSumFromOrder", map);
    }

    @Override
    public int queryMyInvitedUsersCount(Map<String, Object> map) {
        LOG.info("UserDao.queryMyInvitedUsersCount() Start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryMyInvitedUsersCount", map);
    }

    @Override
    public UserRelationDo queryInvitedUserByRegMobile(Map<String, Object> map) {
        LOG.info("UserDao.queryInvitedUserByRegMobile() Start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryInvitedUserByRegMobile", map);
    }

    /**
     * 往钱包中添加提成信息
     * 
     */
    @Override
    public Long saveWallet(WalletDo wallet) {
        LOG.info("UserDao.saveWallet() Start");
        sqlSessionTemplate.insert(NAME_SPACE + ".saveWallet", wallet);
        return wallet.getId();
    }

    @Override
    public Long querMyWalletCount(String mobile) {
        LOG.info("UserDao.querMyWalletCount() Start");
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".querMyWalletCount", map);
    }

    @Override
    public void unboundRelationShip() {
        LOG.info("UserDao.unboundRelationShip() Start");
        sqlSessionTemplate.update(NAME_SPACE + ".unboundRelationShip");
    }

    @Override
    public boolean deleteLogByAccessToken(String accessToken) {
        LOG.info("UserDao.deleteLogByAccessToken() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("accessToken", accessToken);
        return sqlSessionTemplate.delete(NAME_SPACE + ".deleteLogByAccessToken", paramMap) > 0;
    }

    @Override
    public void addAccessLog(Long userId, Integer osType) {
        LOG.info("UserDao.addAccessLog() Start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("osType", osType);
        sqlSessionTemplate.insert(NAME_SPACE + ".addAccessLog", paramMap);
    }

    @Override
    public void updateSmsCode(UserDo user) {
        LOG.info("UserDao.updateSmsCode() Start");
        sqlSessionTemplate.update(NAME_SPACE + ".updateSmsCode", user);
    }

	@Override
	public List<String> queryUserTag(Map<String, Object> param) {
		LOG.info("UserDao.queryUserTag() Start");
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryUserTag", param);
	}

	@Override
	public int updateUserTag(Map<String, Object> param) {
		LOG.info("UserDao.updateUserTag() Start");
        return sqlSessionTemplate.update(NAME_SPACE + ".updateUserTag", param);
	}
	
	@Override
	public int updateUserNickById(Long userId,String nickName) {
		LOG.info("UserDao.updateUserNickById() Start");
		 Map<String, Object> paramMap = new HashMap<String, Object>();
	     paramMap.put("userId", userId);
	     paramMap.put("nickName", nickName);
        return sqlSessionTemplate.update(NAME_SPACE + ".updateUserNickById", paramMap);
	}

	@Override
	public int addUserTag(Map<String, Object> param) {
		LOG.info("UserDao.addUserTag() Start");
        return sqlSessionTemplate.insert(NAME_SPACE + ".addUserTag", param);
	}

    @Override
    public Integer saveUserVisitLog(UserVisitLogDo userVisitLog) {
        LOG.info("UserDao.saveUserVisitLog() Start");
        return sqlSessionTemplate.insert(NAME_SPACE + ".addUserVisitLog", userVisitLog);
    }

	@Override
	public UserDo queryUserByNickName(String nickName) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nickName", nickName);
        return sqlSessionTemplate.selectOne(NAME_SPACE + ".queryUserByNickName", paramMap);
	}

	@Override
	public int getLoginLogCountByIp(String ip) {
		// TODO Auto-generated method stub
		 return sqlSessionTemplate.selectOne(NAME_SPACE + ".getLoginLogCountByIp", ip);
	}
}

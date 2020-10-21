package com.ihomefnt.o2o.service.dao.emchat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.emchat.EmchatIMUsersDao;
import com.ihomefnt.o2o.intf.domain.emchat.doo.EmchatIMUserInfoDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;

@Repository
public class EmchatIMUsersDaoImpl implements EmchatIMUsersDao {

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.emchat.EmchatIMUsersDao.";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    
    @Override
    public int addEmchatIMUser(EmchatIMUserInfoDo emchatIMUserInfo) {
        return sqlSessionTemplate.insert(NAME_SPACE + "saveEmchatIMUser", emchatIMUserInfo);
    }


    @Override
    public String getEmchatIMUserPassword(String userName) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryEmchatIMUserPassword", userName);
    }


    @Override
    public List<UserDo> getUserInfo() {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryUserInfo");
    }


    @Override
    public int modifyEmchatIMUserNickname(String userName, String nickName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("nickName", nickName);
        return sqlSessionTemplate.update(NAME_SPACE + "updateEmchatIMUserNickname", map);
    }

}

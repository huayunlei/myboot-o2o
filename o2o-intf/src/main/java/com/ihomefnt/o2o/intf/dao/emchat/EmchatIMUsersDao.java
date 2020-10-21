package com.ihomefnt.o2o.intf.dao.emchat;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.emchat.doo.EmchatIMUserInfoDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;

public interface EmchatIMUsersDao {
    
    int addEmchatIMUser(EmchatIMUserInfoDo emchatIMUserInfo);

    String getEmchatIMUserPassword(String userName);

    List<UserDo> getUserInfo();

    int modifyEmchatIMUserNickname(String userName, String nickName);
}

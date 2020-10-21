package com.ihomefnt.o2o.intf.service.emchat;

import com.ihomefnt.o2o.intf.domain.emchat.vo.response.EmchatIMUserResponseVo;

public interface EmchatIMUsersService {

    /**
     * 注册环信IM用户单个.
     * @param userName
     */
    int registerEmUser(String userName, String nickName);
    
    /**
     * 获取环信用户信息.
     * @param userId
     * @return
     */
    EmchatIMUserResponseVo getEmchatIMUser(String userName); 
    
    /**
     * 注册环信IM用户批量.
     * @return
     */
    int initEmchatIMUser();
    
    /**
     * 删除环信IM用户信息.
     * @return
     */
    int deleteEmchatIMUser();
    
    /**
     * 
     * @return
     */
    int modifyEmchatIMUserNickname(String userName, String nickName);
}

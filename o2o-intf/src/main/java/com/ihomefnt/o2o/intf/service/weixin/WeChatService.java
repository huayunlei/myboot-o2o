package com.ihomefnt.o2o.intf.service.weixin;

import com.ihomefnt.o2o.intf.domain.weixin.dto.WeChatUserInfo;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.UserInfoRequest;

/**
 * @author xiamingyu
 * @date 2018/10/12
 *
 * 与微信交互的Service
 */

public interface WeChatService {

    /**
     * 用code获取微信用户信息
     * @param request
     * @return
     */
    WeChatUserInfo getUserInfoByCode(UserInfoRequest request);

}

package com.ihomefnt.o2o.intf.proxy.weixin;

import com.ihomefnt.o2o.intf.domain.weixin.dto.WeChatUserInfo;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.UserInfoRequest;

/**
 * @author xiamingyu
 * @date 2018/10/13
 */

public interface WeChatProxy {

    /**
     * 获取微信用户信息
     * @param request
     * @return
     */
    WeChatUserInfo getUserInfo(UserInfoRequest request);

}

package com.ihomefnt.o2o.service.service.weixin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.proxy.weixin.WeChatProxy;
import com.ihomefnt.o2o.intf.service.weixin.WeChatService;
import com.ihomefnt.o2o.intf.domain.weixin.dto.WeChatUserInfo;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.UserInfoRequest;

/**
 * @author xiamingyu
 * @date 2018/10/13
 */

@Service
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private WeChatProxy weChatProxy;

    @Override
    public WeChatUserInfo getUserInfoByCode(UserInfoRequest request) {
        WeChatUserInfo userInfo = weChatProxy.getUserInfo(request);
        if(userInfo!=null){
            userInfo.setLocation(getLoaction(userInfo));
        }
        return userInfo;
    }

    private String getLoaction(WeChatUserInfo weChatUserInfo){
        String location = null;
        if(StringUtils.isNotBlank(weChatUserInfo.getProvince())){
            location = weChatUserInfo.getProvince();
        }
        if(StringUtils.isNotBlank(weChatUserInfo.getCity())){
            if(StringUtils.isNotBlank(weChatUserInfo.getProvince())){
                location = location+"-"+weChatUserInfo.getCity();
            }else{
                location = weChatUserInfo.getCity();
            }
        }

        return location;
    }
}

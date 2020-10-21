package com.ihomefnt.o2o.service.proxy.weixin;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.weixin.dto.WeChatUserInfo;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.UserInfoRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.weixin.WeChatProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author xiamingyu
 * @date 2018/10/13
 */

@Service
public class WeChatProxyImpl implements WeChatProxy {

    private static final Logger LOG = LoggerFactory.getLogger(WeChatProxyImpl.class);

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public WeChatUserInfo getUserInfo(UserInfoRequest request) {
        HttpBaseResponse<WeChatUserInfo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_WECHAT_USER_INFO, request,
                    new TypeReference<HttpBaseResponse<WeChatUserInfo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (null != responseVo && HttpResponseCode.SUCCESS == responseVo.getCode()) {
            return responseVo.getObj();
        }
        return null;
    }
}

package com.ihomefnt.o2o.service.service.push;


import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.push.dto.AppPushDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.push.AppPushProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author liyonggang
 * @create 2019-11-28 10:53 上午
 */
@Repository
public class AppPushProxyImpl implements AppPushProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public boolean appPush(AppPushDto appPushDto) {
        HttpBaseResponse result = strongSercviceCaller.post(WcmWebServiceNameConstants.APP_PUSH, appPushDto, HttpBaseResponse.class);
        return result.getCode().equals(1L);
    }
}

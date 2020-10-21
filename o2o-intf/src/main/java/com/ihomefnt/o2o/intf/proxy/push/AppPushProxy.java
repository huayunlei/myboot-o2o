package com.ihomefnt.o2o.intf.proxy.push;

import com.ihomefnt.o2o.intf.domain.push.dto.AppPushDto;

/**
 * @author liyonggang
 * @create 2019-11-28 10:51 上午
 */
public interface AppPushProxy {

    /**
     * app推送
     *
     * @param appPushDto
     * @return
     */
    boolean appPush(AppPushDto appPushDto);
}

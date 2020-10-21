package com.ihomefnt.o2o.intf.proxy.common;

import com.ihomefnt.o2o.intf.domain.user.dto.AiMonitorDto;

import java.util.List;

public interface AiMonitorConfigProxy {

    /**
     * 根据监控主键获取监控告警详情
     * @param monitorKey
     * @return
     */
    List<AiMonitorDto> getMonitorByKey(String monitorKey);



}

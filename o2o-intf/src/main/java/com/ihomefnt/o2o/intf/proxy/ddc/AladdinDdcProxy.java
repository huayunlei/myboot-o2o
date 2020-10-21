package com.ihomefnt.o2o.intf.proxy.ddc;

import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DirectCompleteSolutionTaskRequestVo;

public interface AladdinDdcProxy {

    /**
     * 一键完成设计任务
     *
     * @param request
     * @return
     */
    String directCompleteSolutionTask(DirectCompleteSolutionTaskRequestVo request);
}

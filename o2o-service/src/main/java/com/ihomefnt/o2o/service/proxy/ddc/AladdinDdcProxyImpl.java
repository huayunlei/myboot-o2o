package com.ihomefnt.o2o.service.proxy.ddc;

import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DirectCompleteSolutionTaskRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDdcServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.ddc.AladdinDdcProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AladdinDdcProxyImpl implements AladdinDdcProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public String directCompleteSolutionTask(DirectCompleteSolutionTaskRequestVo request) {
        ResponseVo<String> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.DIRECT_COMPLETE_SOLUTION_TASK, request, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }
        if (null != responseVo && responseVo.getMsg() != null) {
            return responseVo.getMsg();
        }
        return null;
    }
}

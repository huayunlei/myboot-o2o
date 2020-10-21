package com.ihomefnt.o2o.service.proxy.finalkeeper;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.finalkeeper.request.ConfirmFundRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.FinalkeeperWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.finalkeeper.FinalkeeperProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FinalkeeperProxyImpl implements FinalkeeperProxy {

    @Resource
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public String confirmFund(ConfirmFundRequest request) {
        try {
            ResponseVo<String> response = strongSercviceCaller.post(FinalkeeperWebServiceNameConstants.CONFIRM_FUND, request,
                    new TypeReference<ResponseVo<String>>() {
                    });
            return response.getMsg();
        } catch (Exception e) {
            return "确认收款单失败";
        }
    }
}

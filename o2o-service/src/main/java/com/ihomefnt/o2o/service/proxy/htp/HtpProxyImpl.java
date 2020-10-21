package com.ihomefnt.o2o.service.proxy.htp;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.htp.vo.request.GetExtByHouseIdRequestVo;
import com.ihomefnt.o2o.intf.domain.htp.vo.response.GetExtByHouseIdResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.HtpHouseServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.htp.HtpProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HtpProxyImpl implements HtpProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public GetExtByHouseIdResponseVo getExtByHouseId(GetExtByHouseIdRequestVo request) {
        ResponseVo<GetExtByHouseIdResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(HtpHouseServiceNameConstants.GET_EXT_BY_HOUSE_ID, request,
                    new TypeReference<ResponseVo<GetExtByHouseIdResponseVo>>() {});
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.FTP_FAILED, MessageConstant.FAILED);
        }
        if(responseVo == null || !responseVo.isSuccess()){
            throw new BusinessException(HttpReturnCode.FTP_FAILED, MessageConstant.FAILED);
        }
        return responseVo.getData();
    }
}

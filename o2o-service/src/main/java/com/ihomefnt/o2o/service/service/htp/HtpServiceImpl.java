package com.ihomefnt.o2o.service.service.htp;

import com.ihomefnt.o2o.intf.domain.htp.vo.request.GetExtByHouseIdRequestVo;
import com.ihomefnt.o2o.intf.domain.htp.vo.response.GetExtByHouseIdResponseVo;
import com.ihomefnt.o2o.intf.proxy.htp.HtpProxy;
import com.ihomefnt.o2o.intf.service.htp.HtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HtpServiceImpl implements HtpService {

    @Autowired
    private HtpProxy htpProxy;

    @Override
    public GetExtByHouseIdResponseVo getExtByHouseId(GetExtByHouseIdRequestVo request) {
        return htpProxy.getExtByHouseId(request);
    }
}

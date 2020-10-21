package com.ihomefnt.o2o.service.proxy.common;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.dingtalk.dto.AiDingTalkRecordDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.common.AiDingTalkRecordProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author hua
 * @Date 2019-07-11 14:47
 */
@Service
public class AiDingTalkRecordProxyImpl implements AiDingTalkRecordProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public void addDingTalkRecord(AiDingTalkRecordDto aiDingTalkRecordDto) {
        HttpBaseResponse<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.ADD_DING_TALK_RECORD, aiDingTalkRecordDto, HttpBaseResponse.class);
        } catch (Exception e) {
        }
    }
}

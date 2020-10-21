package com.ihomefnt.o2o.service.proxy.flowmsg;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.flowmsg.dto.DeleteOfflineDrawMessageDto;
import com.ihomefnt.o2o.intf.domain.flowmsg.vo.request.QueryMessageRecordFlowRequestVo;
import com.ihomefnt.o2o.intf.domain.flowmsg.vo.response.MessageRecordFlowResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AppPushServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.flowmsg.MessageFlowProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-21 10:59
 */
@Service
public class MessageFlowProxyImpl implements MessageFlowProxy {


    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public MessageRecordFlowResponseVo getMessageFlowList(QueryMessageRecordFlowRequestVo request) {
        try {
            HttpBaseResponse response = strongSercviceCaller.post(AppPushServiceNameConstants.QUERY_MESSAGE_RECORD_FLOW, request, HttpBaseResponse.class);

            if (null != response && response.getObj() != null) {
                return JsonUtils.json2obj(JsonUtils.obj2json(response.getObj()), MessageRecordFlowResponseVo.class);
            }
        } catch (Exception e) {
            throw new BusinessException(MessageConstant.QUERY_FAILED);
        }

        return null;
    }

    @Override
    public Boolean deleteOfflineDrawMessage(DeleteOfflineDrawMessageDto deleteOfflineDrawMessageDto) {
        try {
            HttpBaseResponse response = strongSercviceCaller.post(AppPushServiceNameConstants.DELETE_OFFLINE_DRAW_MESSAGE, deleteOfflineDrawMessageDto, HttpBaseResponse.class);

            if (null != response && response.isSuccess()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

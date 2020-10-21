package com.ihomefnt.o2o.intf.proxy.flowmsg;

import com.ihomefnt.o2o.intf.domain.flowmsg.dto.DeleteOfflineDrawMessageDto;
import com.ihomefnt.o2o.intf.domain.flowmsg.vo.request.QueryMessageRecordFlowRequestVo;
import com.ihomefnt.o2o.intf.domain.flowmsg.vo.response.MessageRecordFlowResponseVo;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-21 10:59
 */
public interface MessageFlowProxy {
    //查询信息流
    MessageRecordFlowResponseVo getMessageFlowList(QueryMessageRecordFlowRequestVo request);

    Boolean deleteOfflineDrawMessage(DeleteOfflineDrawMessageDto deleteOfflineDrawMessageDto);

}

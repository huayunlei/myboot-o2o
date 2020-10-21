package com.ihomefnt.o2o.intf.domain.emchat.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class ChatBaseRequest extends HttpBaseRequest {

    private Long suitId;//从套装进去传套装ID
    private Long roomId;//从空间进去传空间ID

}

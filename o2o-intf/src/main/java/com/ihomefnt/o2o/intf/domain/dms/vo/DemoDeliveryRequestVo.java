package com.ihomefnt.o2o.intf.domain.dms.vo;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class DemoDeliveryRequestVo extends HttpBaseRequest {

    private Integer orderId;
}

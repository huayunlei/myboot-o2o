package com.ihomefnt.o2o.intf.domain.homepage.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */
@Data
public class OrderBaseRequest extends HttpBaseRequest {

    private Integer nodeStatus;

    private Integer orderId;
}

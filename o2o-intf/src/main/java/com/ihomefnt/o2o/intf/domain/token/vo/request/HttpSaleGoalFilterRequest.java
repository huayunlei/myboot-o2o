package com.ihomefnt.o2o.intf.domain.token.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class HttpSaleGoalFilterRequest extends HttpBaseRequest{
    private Long saleId;//客户ID
}

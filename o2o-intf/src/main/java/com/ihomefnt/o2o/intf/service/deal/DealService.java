package com.ihomefnt.o2o.intf.service.deal;

import com.ihomefnt.o2o.intf.domain.deal.dto.DealPickHomeResponse;
import com.ihomefnt.o2o.intf.domain.deal.dto.OrderDetailResponse;

/**
 * Created by hvk687 on 9/29/15.
 */
public interface DealService {
    DealPickHomeResponse loadHomeResponse();

    OrderDetailResponse loadOrderDetailResponse(String mobile);

    boolean pickOrder(String orderNo);
    
    int getOrderCount(int userId);
}

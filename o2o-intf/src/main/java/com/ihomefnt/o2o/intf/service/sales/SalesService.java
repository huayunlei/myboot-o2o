package com.ihomefnt.o2o.intf.service.sales;


import com.ihomefnt.o2o.intf.proxy.sales.vo.response.CustomerListResponse;
import com.ihomefnt.o2o.intf.proxy.sales.vo.response.InviteResponse;

/**
 * Created by hvk687 on 10/20/15.
 */
public interface SalesService {
    CustomerListResponse loadSalesCustomer(Long salesId, Integer from, Integer size);

    InviteResponse bindUser(Long salesId, String mobile, Integer status);
    InviteResponse inviteUser(Long salesId, String mobile);
}

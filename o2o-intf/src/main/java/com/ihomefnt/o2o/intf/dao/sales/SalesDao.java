package com.ihomefnt.o2o.intf.dao.sales;

import java.util.List;

import com.ihomefnt.o2o.intf.proxy.sales.dto.CustomerInfo;

/**
 * Created by hvk687 on 10/20/15.
 */
public interface SalesDao {
    List<CustomerInfo> loadSalesCustomer(Long salesId, Integer from, Integer size);

    Integer queryCustomerCount(Long salesId);

    Integer queryCustomerCountByStatus(Long salesId, Integer status);

    List<Long> querySalesIdPerCustomer(String mobile);//query validate salesId bound with give customer mobile

    Integer bindSalesCustomer(Long salesId, String mobile,Integer status);

    Integer updateInviteStatus(String mobile);

    List<CustomerInfo> loadInvitedCustomers(String mobile);

    Integer updateStatus(Long id, Integer status);

    Integer updateValidationStatus();

    Integer getBindCountToday(Long salesId);
}

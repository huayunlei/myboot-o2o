package com.ihomefnt.o2o.service.proxy.sales;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.sales.SalesDao;
import com.ihomefnt.o2o.intf.proxy.sales.dto.CustomerInfo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hvk687 on 10/20/15.
 */
@Repository
public class SalesDaoImpl implements SalesDao {
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.sales.SalesDao.";
    @Autowired
    SqlSessionTemplate sessionTemplate;

    @Override
    public List<CustomerInfo> loadSalesCustomer(Long salesId, Integer from, Integer size) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sales_id", salesId);
        map.put("from", from);
        map.put("size", size);
        return sessionTemplate.selectList(NAME_SPACE + "queryCustomerList", map);
    }

    @Override
    public Integer queryCustomerCount(Long salesId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sales_id", salesId);
        return sessionTemplate.selectOne(NAME_SPACE + "queryCount", map);
    }

    @Override
    public Integer queryCustomerCountByStatus(Long salesId, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sales_id", salesId);
        map.put("status", status);
        return sessionTemplate.selectOne(NAME_SPACE + "queryCustomerCountByStatus", map);
    }

    @Override
    public List<Long> querySalesIdPerCustomer(String mobile) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        return sessionTemplate.selectList(NAME_SPACE + "querySalesIdPerCustomer", map);
    }

    @Override
    public Integer bindSalesCustomer(Long salesId, String mobile,Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("sales_id", salesId);
        map.put("status", status);
        return sessionTemplate.insert(NAME_SPACE + "bindSalesCustomer", map);
    }

    public List<CustomerInfo> loadInvitedCustomers(String mobile) {
        //only load valid user;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        return sessionTemplate.selectList(NAME_SPACE + "loadInvitedCustomers", map);
    }

    @Override
    public Integer updateInviteStatus(String mobile) {
        //1. check validation
        List<CustomerInfo> customerInfoList = loadInvitedCustomers(mobile);
        if (customerInfoList == null || customerInfoList.size() <= 0) {
            return 0;//not invited before;
        } else if (customerInfoList.size() > 1) {
            //TODO: this is invalid status. should delete latest one
        }

        CustomerInfo customerInfo = customerInfoList.get(0);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        if (current.after(customerInfo.getDeadline())) {
            //2. if invalid, update status
            updateStatus(customerInfo.getId(), 3);
        } else {
            //3. if valid, bind user
            updateStatus(customerInfo.getId(), 2);
        }
        return 1;
    }

    @Override
    public Integer updateStatus(Long id, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("status", status);
        return sessionTemplate.update(NAME_SPACE + "updatePerId", map);
    }

    @Override
    public Integer updateValidationStatus() {
        return sessionTemplate.update(NAME_SPACE + "updateValidationStatus");
    }

    @Override
    public Integer getBindCountToday(Long salesId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sales_id", salesId);
        return sessionTemplate.selectOne(NAME_SPACE + "getBindCountToday", map);
    }
}
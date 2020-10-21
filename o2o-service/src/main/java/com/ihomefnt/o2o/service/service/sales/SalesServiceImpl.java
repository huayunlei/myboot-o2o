package com.ihomefnt.o2o.service.service.sales;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.dao.sales.SalesDao;
import com.ihomefnt.o2o.intf.dao.user.UserDao;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.proxy.sales.dto.CustomerInfo;
import com.ihomefnt.o2o.intf.proxy.sales.vo.response.CustomerListResponse;
import com.ihomefnt.o2o.intf.proxy.sales.vo.response.InviteResponse;
import com.ihomefnt.o2o.intf.service.sales.SalesService;

import java.util.List;

/**
 * Created by hvk687 on 10/20/15.
 */
@Service
public class SalesServiceImpl implements SalesService {

    @Value("${host}")
    public String host;
    
    @Autowired
    SalesDao salesDao;

    @Autowired
    UserDao userDao;

    /**
     * @param salesId sales id
     * @param from    page number
     * @param size    page size
     * @return
     */
    @Override
    public CustomerListResponse loadSalesCustomer(Long salesId, Integer from, Integer size) {
        //update whole table, update user status;
        updateWholeTable();

        CustomerListResponse response = new CustomerListResponse();

        //load all customer count for give sales
        Integer count = salesDao.queryCustomerCount(salesId);
        response.setCustomerCount(count);
        Integer totalPages = (count / size) + (count % size == 0 ? 0 : 1);
        if (from > totalPages) {
            from = totalPages;
        }
        if (from == 0) {
            from = 1;
        }

        //load one page of customer
        List<CustomerInfo> list = salesDao.loadSalesCustomer(salesId, (from - 1) * size, size);
        response.setCustomerInfoList(list);

        /**
         * 获取绑定的客户数
         */
        Integer bound = salesDao.queryCustomerCountByStatus(salesId, 2);
        response.setBound(bound);
        response.setInvited(count);
        response.setPageNo(from);
        response.setInviteUrl(host + "/sales/invite/" + salesId);

        return response;
    }

    /**
     * bind user with salesId and customer mobile
     *
     * @param salesId
     * @param mobile
     * @return
     */
    @Override
    public InviteResponse bindUser(Long salesId, String mobile, Integer status) {
        InviteResponse response = new InviteResponse();
        UserDo tUser = userDao.queryUserInfoByMobile(mobile);
        if (tUser == null) {
            response.setStatus(InviteResponse.FAILED);
            response.setMsg(InviteResponse.ERR_UNREGISTER);
        } else {
            Integer todayCount = salesDao.getBindCountToday(salesId);
            if (todayCount.compareTo(100) >= 0) {
                response.setStatus(InviteResponse.FAILED);
                response.setMsg(InviteResponse.ERR_MORE);
            } else {
                //1. check whether salesId and mobile has already been bound;
                List<Long> salesList = salesDao.querySalesIdPerCustomer(mobile);
                if (salesList != null && salesList.size() > 0) {
                    response.setStatus(InviteResponse.BOUND);//用户易经被绑定过
                    response.setMsg(InviteResponse.ERR_BOUND);
                } else {
                    Integer ret = salesDao.bindSalesCustomer(salesId, mobile, status);//set to invite status 2
                    if (ret == 1) {
                        response.setStatus(InviteResponse.SUCCESS);
                    } else {
                        response.setStatus(InviteResponse.FAILED);
                    }
                }
            }
        }
        return response;
    }

    private void updateWholeTable() {
        salesDao.updateValidationStatus();
    }

    @Override
    public InviteResponse inviteUser(Long salesId, String mobile) {
        InviteResponse response = new InviteResponse();
        Integer todayCount = salesDao.getBindCountToday(salesId);
        if (todayCount.compareTo(100) >= 0) {
            response.setStatus(InviteResponse.FAILED);
            response.setMsg(InviteResponse.ERR_MORE);
        } else {
            //1. check whether salesId and mobile has already been bound;
            List<Long> salesList = salesDao.querySalesIdPerCustomer(mobile);
            if (salesList != null && salesList.size() > 0) {
                response.setStatus(InviteResponse.BOUND);//用户易经被绑定过
                response.setMsg(InviteResponse.ERR_BOUND);
            } else {
                UserDo tUser = userDao.queryUserInfoByMobile(mobile);
                Integer status = 1;
                if (tUser == null) {
                    status = 1;
                } else {
                    status = 2;
                }
                Integer ret = salesDao.bindSalesCustomer(salesId, mobile, status);//set to invite status 2
                if (ret == 1) {
                    response.setStatus(InviteResponse.SUCCESS);
                } else {
                    response.setStatus(InviteResponse.FAILED);
                }
            }
        }

        return response;
    }


}

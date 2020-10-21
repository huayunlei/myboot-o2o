package com.ihomefnt.o2o.intf.proxy.sales.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.proxy.sales.dto.CustomerInfo;

/**
 * Created by hvk687 on 10/20/15.
 */
public class CustomerListResponse {
    private String inviteUrl = "";
    private Integer invited = 0;
    private Integer bound = 0;
    private List<CustomerInfo> customerInfoList;
    private Integer pageNo = 1;
    private Integer customerCount = 0;

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public Integer getInvited() {
        return invited;
    }

    public void setInvited(Integer invited) {
        this.invited = invited;
    }

    public Integer getBound() {
        return bound;
    }

    public void setBound(Integer bound) {
        this.bound = bound;
    }

    public List<CustomerInfo> getCustomerInfoList() {
        return customerInfoList;
    }

    public void setCustomerInfoList(List<CustomerInfo> customerInfoList) {
        this.customerInfoList = customerInfoList;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }
}

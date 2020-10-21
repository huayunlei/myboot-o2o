/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月22日
 * Description:Kuaidi100Service.java
 */
package com.ihomefnt.o2o.intf.service.kuaidi100;

import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response.KuaidiProductInfoResponseVo;

/**
 * @author zhang
 */
public interface Kuaidi100Service {

    /**
     * 根据快递公司和快递单号获取快递的物流信息
     *
     * @param consigneePhoneNumber 收件人手机号，顺丰必填，其他公司选填
     * @param kuaidiCompany
     * @param kuaidiNo
     */
    public KuaidiProductInfoResponseVo getKuaidi100(String consigneePhoneNumber, String kuaidiCompany, String kuaidiNo);

    /**
     * 查询快递信息，根据订单号，快递信息查询，为了封装顺丰必须传入手机号的问题，订单号和订单id二选一，快递号和公司必填
     *
     * @param orderNum
     * @param orderId
     * @param kuaidiCompany
     * @param kuaidiNo
     * @return
     */
    public KuaidiProductInfoResponseVo getKuaidiInfo(String orderNum, Integer orderId, String kuaidiCompany, String kuaidiNo);


}

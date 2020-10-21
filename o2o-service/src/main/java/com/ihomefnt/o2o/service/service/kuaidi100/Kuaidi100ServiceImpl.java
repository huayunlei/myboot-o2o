/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月22日
 * Description:Kuaidi100ServiceImpl.java
 */
package com.ihomefnt.o2o.service.service.kuaidi100;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response.KuaidiProductInfoResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.kuaidi100.Kuaidi100Constant;
import com.ihomefnt.o2o.intf.manager.constant.kuaidi100.Kuaidi100StateEnum;
import com.ihomefnt.o2o.intf.manager.constant.kuaidi100.LogisticsCompanyCodeEnum;
import com.ihomefnt.o2o.intf.manager.util.kuaidi100.Kuaidi100HttpUtil;
import com.ihomefnt.o2o.intf.manager.util.kuaidi100.Kuaidi100MD5;
import com.ihomefnt.o2o.intf.service.kuaidi100.Kuaidi100Service;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.service.proxy.order.OrderProxyImpl;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDtoVo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author zhang
 */
@Service
public class Kuaidi100ServiceImpl implements Kuaidi100Service {

    @Autowired
    ApiConfig apiConfig;

    @Autowired
    private OrderProxyImpl orderProxy;
    @Autowired
    private OrderService orderService;

    public static final Logger LOGGER = LoggerFactory.getLogger(Kuaidi100ServiceImpl.class);

    @Override
    public KuaidiProductInfoResponseVo getKuaidi100(String consigneePhoneNumber, String kuaidiCompany, String kuaidiNo) {
        KuaidiProductInfoResponseVo info = null;
        // 只有满足生产环境才为true,只有生产环境才调用快递100
        String openTagShow = apiConfig.getOpenTagShow();
        if (openTagShow.equals("false")) {
            return null;
        }

        if (StringUtils.isBlank(kuaidiCompany) || StringUtils.isBlank(kuaidiNo)) {
            return null;
        }
        String param = JSON.toJSONString(Maps.newHashMap("com", kuaidiCompany, "num", kuaidiNo, "phone", consigneePhoneNumber));
        String customer = Kuaidi100Constant.CUSTOMER;
        String key = Kuaidi100Constant.key;
        String sign = Kuaidi100MD5.encode(param + key + customer);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("param", param);
        params.put("sign", sign);
        params.put("customer", customer);
        String resp = null;
        try {
            resp = Kuaidi100HttpUtil.postData(Kuaidi100Constant.QUERY_DO_URL, params, "utf-8").toString();
            if (StringUtils.isBlank(resp)) {
                return null;
            }
            info = JsonUtils.json2obj(resp, KuaidiProductInfoResponseVo.class);
            if (info != null) {
                Integer state = info.getState();
                if (state != null) {
                    info.setStateDesc(Kuaidi100StateEnum.getMsg(state));
                }
                info.setResult(Boolean.TRUE);
            }
        } catch (Exception e) {
            LOGGER.error("kuaidi100 api o2o-exception , more info :",e);
        }
        LOGGER.info("快递查询：params：{} , response{}", params, resp);

        if (info == null) {
            info = new KuaidiProductInfoResponseVo();
            info.setResult(Boolean.FALSE);
        }
        return info;
    }

    /**
     * 查询快递信息，根据订单号，快递信息查询，为了封装顺丰必须传入手机号的问题，订单号和订单id二选一，快递号和公司必填
     *
     * @param orderNum
     * @param orderId
     * @param kuaidiCompany
     * @param kuaidiNo
     * @return
     */
    @Override
    public KuaidiProductInfoResponseVo getKuaidiInfo(String orderNum, Integer orderId, String kuaidiCompany, String kuaidiNo) {
        //顺丰快递需要收件人手机号
        if (LogisticsCompanyCodeEnum.SHUNFENG.getCode().equalsIgnoreCase(kuaidiCompany)) {
            OrderDtoVo orderDtoVo = null;
            if (orderId != null) {
                orderDtoVo = orderService.queryOrderDetail(orderId);
            } else if (StringUtils.isNotBlank(orderNum)) {
                orderDtoVo = orderProxy.queryOrderDetailByOrderNum(orderNum);
            }
            if (orderDtoVo == null) {
                return null;
            }
            if (StringUtils.isBlank(orderDtoVo.getReceiverTel())) {
                LOGGER.warn("顺丰快递但无收货人手机号: 订单ID:{},订单号:{},快递公司:{},快递编号:{}", orderId, orderNum, kuaidiCompany, kuaidiNo);
                return null;
            }
            return getKuaidi100(orderDtoVo.getReceiverTel(), kuaidiCompany, kuaidiNo);
        }
        return getKuaidi100(null, kuaidiCompany, kuaidiNo);
    }

}

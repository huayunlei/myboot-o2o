/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月7日
 * Description:HbmsProxy.java
 */
package com.ihomefnt.o2o.service.proxy.lechange;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetBeginTimeByOrderIdResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.ProjectDetailProgressInfoVo;
import com.ihomefnt.o2o.intf.domain.lechange.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.lechange.HardOrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.proxy.HbmsServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.lechange.HbmsProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author zhang
 */
@Service
public class HbmsProxyImpl implements HbmsProxy {

    @Resource
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public boolean updateDeviceUrl(UpdateDeviceParamParamVo param) {
        if (param == null || StringUtils.isBlank(param.getDeviceId()) || StringUtils.isBlank(param.getUrl())) {
            return false;
        }

        ResponseVo<?> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.UPDATE_DEVICE_URL, param, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }
        if (responseVo == null) {
            return false;
        }
        return responseVo.isSuccess();

    }


    @Override
    public PagesVo<GetDeviceListResultVo> getSimpleDeviceList(GetDeviceListByValueParamVo param) {
        if (param == null) {
            return null;
        }
        ResponseVo<PagesVo<GetDeviceListResultVo>> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_SIMPLE_DEVICE_LIST_BY_VALUE, param,
                    new TypeReference<ResponseVo<PagesVo<GetDeviceListResultVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (!CollectionUtils.isEmpty(responseVo.getData().getList())) {
                responseVo.getData().setList(responseVo.getData().getList().parallelStream().peek(getDeviceListResultVo -> {
                    if (getDeviceListResultVo.getProjectStatus() != null) {
                        getDeviceListResultVo.setOrderStatus(HardOrderConstant.getMsg(getDeviceListResultVo.getProjectStatus()));
                    } else {
                        getDeviceListResultVo.setOrderStatus("");
                    }
                }).collect(toList()));
            }
            return responseVo.getData();
        }
        return null;

    }

    @Override
    public PagesVo<GetDeviceListResultVo> getSimpleDeviceList(GetDeviceListParamVo param) {
        if (param == null) {
            return null;
        }
        ResponseVo<PagesVo<GetDeviceListResultVo>> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_SIMPLE_DEVICE_LIST, param,
                    new TypeReference<ResponseVo<PagesVo<GetDeviceListResultVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (!CollectionUtils.isEmpty(responseVo.getData().getList())) {
                responseVo.getData().setList(responseVo.getData().getList().parallelStream().peek(getDeviceListResultVo -> {
                    if (getDeviceListResultVo.getProjectStatus() != null) {
                        getDeviceListResultVo.setOrderStatus(HardOrderConstant.getMsg(getDeviceListResultVo.getProjectStatus()));
                    } else {
                        getDeviceListResultVo.setOrderStatus("");
                    }
                }).collect(toList()));
            }
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public PagesVo<GetDeviceListResultVo> getDeviceList(GetDeviceListPageVo param) {
        if (param == null) {
            return null;
        }
        ResponseVo<PagesVo<GetDeviceListResultVo>> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_DEVICE_LIST, param,
                    new TypeReference<ResponseVo<PagesVo<GetDeviceListResultVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (!CollectionUtils.isEmpty(responseVo.getData().getList())) {
                responseVo.getData().setList(responseVo.getData().getList().parallelStream().peek(getDeviceListResultVo -> {
                    //魔看的设备施工状态是status
                    if (getDeviceListResultVo.getStatus() != null) {
                        getDeviceListResultVo.setOrderStatus(HardOrderConstant.getMsg(getDeviceListResultVo.getStatus()));
                    } else {
                        getDeviceListResultVo.setOrderStatus("");
                    }
                }).collect(toList()));
            }
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public GetDeviceListResultVo getDeviceBySn(String cameraSn) {
        if (StringUtils.isBlank(cameraSn)) {
            return null;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("cameraSn", cameraSn);
        ResponseVo<GetDeviceListResultVo> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_DEVICE_BY_SN, param,
                    new TypeReference<ResponseVo<GetDeviceListResultVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;

    }

    @Override
    public List<GetDeviceListResultVo> getDeviceByOrderId(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return null;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);

        ResponseVo<List<GetDeviceListResultVo>> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_DEVICE_BY_ORDER_ID, param,
                    new TypeReference<ResponseVo<List<GetDeviceListResultVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public ProjectDetailProgressInfoVo queryHbmsProgress(Integer orderNum) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderNum);
        ResponseVo<ProjectDetailProgressInfoVo> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_PROJECT_PROGRESS_BY_ORDER_ID, param,
                    new TypeReference<ResponseVo<ProjectDetailProgressInfoVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public GetBeginTimeByOrderIdResultDto getBeginTimeByOrderId(Integer orderNum) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderNum);
        ResponseVo<GetBeginTimeByOrderIdResultDto> responseVo;
        try {
            responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_BEGIN_TIME_BY_ORDER_ID, param,
                    new TypeReference<ResponseVo<GetBeginTimeByOrderIdResultDto>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;
    }

}

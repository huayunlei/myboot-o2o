/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月30日
 * Description:ArtOrder.java
 */
package com.ihomefnt.o2o.service.proxy.art;

import com.alibaba.fastjson.JSON;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderDto;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.art.ArtOrderProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.mail.MailProxy;
import com.ihomefnt.o2o.intf.domain.mail.dto.ErrorEntity;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhang
 */
@Service
public class ArtOrderProxyImpl implements ArtOrderProxy {

    @Autowired
    MailProxy mailProxy;

    @Autowired
    private StrongSercviceCaller  strongSercviceCaller;

    @Override
    public ResponseVo<?> createArtOrder(Object param) {
        ResponseVo<?> responseVo = null;
        ErrorEntity entity = new ErrorEntity();
        // 邮件标题
        entity.setTitle("艺术品订单出错");
        // 收件人,注意是配置在wcm.t_dic的key_desc这个字段,如:CREATE_ART_ERROR_MAIL
        entity.setWcmEmail("CREATE_ART_ERROR_MAIL");
        // 执行方法名
        entity.setZeusMethod(OmsWebServiceNameConstants.SERVER_CREATE_ART_ORDER);
        // 请求参数
        entity.setParam(param);
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SERVER_CREATE_ART_ORDER, param, ResponseVo.class);
        } catch (Exception e) {
            entity.setErrorMsg(JsonUtils.obj2json(e.getStackTrace()));
            mailProxy.sendErrorMail(entity);
            return null;
        }
        if(responseVo==null){
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, MessageConstant.PRODUCT_NOT_EXISTS);
        }
        if(responseVo!=null && responseVo.getCode()==110){
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END,MessageConstant.SKU_NOT_EXISTS);
        }
        if(responseVo!=null && responseVo.getCode()==111){
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END,MessageConstant.SKU_OFFLINE);
        }
        if(responseVo!=null && responseVo.getCode()==-1){
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END,responseVo.getMsg());
        }
        // 输出结果
        entity.setResponseVo(responseVo);
        mailProxy.sendErrorMail(entity);
        return responseVo;
    }


    @Override
    public boolean artCancel(Object param) {
        ResponseVo<?> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SERVER_CANCEL_ART_ORDER, param, ResponseVo.class);
        if (null == responseVo) {
        	return false;
        }
        return responseVo.isSuccess();
    }

    @Override
    public void deleteOrder(QueryCollageOrderDetailRequest request) {
        ResponseVo<Boolean> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.DEL_CANCEL_ART_ORDER, request.getOrderIds(), 
        		new TypeReference<ResponseVo<Boolean>>(){});
        if (!responseVo.isSuccess()||responseVo.getData()==null||!responseVo.getData()) {
            throw new BusinessException(HttpResponseCode.FAILED,  MessageConstant.FAILED);
        }
    }

    @Override
    public OrderDto queryArtOrderDetailById(Integer orderId) {
    	try {
    		ResponseVo<OrderDto> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SERVER_QUERY_ART_ORDER_DETAIL, orderId, 
    				new TypeReference<ResponseVo<OrderDto>>(){});
    		if (null != responseVo && responseVo.isSuccess()) {
    			return responseVo.getData();
    		}
		} catch (Exception e) {
		}
        return null;
    }

}

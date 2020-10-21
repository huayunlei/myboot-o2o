package com.ihomefnt.o2o.service.proxy.activity;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.common.ResponseCodeEnum;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.activity.BoeActivityProxy;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiamingyu
 * @date 2018/11/27
 */
@Service
public class BoeActivityProxyImpl implements BoeActivityProxy {

    private static final Logger LOG = LoggerFactory.getLogger(BoeActivityProxyImpl.class);

    private static final String WCM_BOE_URL = "wcm-web.boe.";

    @Autowired
    private ServiceCaller serviceCaller;

    @Override
    public CommonResponseVo getBoeVoucher(JSONObject params) {
        CommonResponseVo commonResponseVo;
        LOG.info("wcm-web.boe.createBoeVoucher param:{}", JsonUtils.obj2json(params));
        try {
            commonResponseVo = serviceCaller.post(WCM_BOE_URL+"createBoeVoucher",params,CommonResponseVo.class);
            LOG.info("wcm-web.boe.createBoeVoucher result:{}", JsonUtils.obj2json(commonResponseVo));
        }catch (Exception e){
            commonResponseVo = CommonResponseVo.buildFailedResponse(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode(),ResponseCodeEnum.SYSTEM_EXCEPTION.getMsg());
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo getBoeVoucherPrivate(JSONObject params){
        CommonResponseVo commonResponseVo;
        LOG.info("wcm-web.boe.createBoeVoucherPrivate param:{}", JsonUtils.obj2json(params));
        try {
            commonResponseVo = serviceCaller.post(WCM_BOE_URL+"createBoeVoucherPrivate",params,CommonResponseVo.class);
            LOG.info("wcm-web.boe.createBoeVoucherPrivate result:{}", JsonUtils.obj2json(commonResponseVo));
        }catch (Exception e){
            commonResponseVo = CommonResponseVo.buildFailedResponse(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode(),ResponseCodeEnum.SYSTEM_EXCEPTION.getMsg());
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo getInviteCode(JSONObject params) {
        CommonResponseVo commonResponseVo;
        LOG.info("wcm-web.boe.queryInviteCodeByMobile param:{}", JsonUtils.obj2json(params));
        try {
            commonResponseVo = serviceCaller.post(WCM_BOE_URL+"queryInviteCodeByMobile",params,CommonResponseVo.class);
            LOG.info("wcm-web.boe.queryInviteCodeByMobile result:{}", JsonUtils.obj2json(commonResponseVo));
        }catch (Exception e){
            commonResponseVo = CommonResponseVo.buildFailedResponse(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode(),ResponseCodeEnum.SYSTEM_EXCEPTION.getMsg());
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo getValidVoucher(JSONObject params) {
        CommonResponseVo commonResponseVo;
        LOG.info("wcm-web.boe.queryVoucherByProductId param:{}", JsonUtils.obj2json(params));
        try {
            commonResponseVo = serviceCaller.post(WCM_BOE_URL+"queryVoucherByProductId",params,CommonResponseVo.class);
            LOG.info("wcm-web.boe.queryVoucherByProductId result:{}", JsonUtils.obj2json(commonResponseVo));
        }catch (Exception e){
            commonResponseVo = CommonResponseVo.buildFailedResponse(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode(),ResponseCodeEnum.SYSTEM_EXCEPTION.getMsg());
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo validVoucher(JSONObject params) {
        CommonResponseVo commonResponseVo;
        LOG.info("wcm-web.boe.validVoucher param:{}", JsonUtils.obj2json(params));
        try {
            commonResponseVo = serviceCaller.post(WCM_BOE_URL+"validVoucher",params,CommonResponseVo.class);
            LOG.info("wcm-web.boe.validVoucher result:{}", JsonUtils.obj2json(commonResponseVo));
        }catch (Exception e){
            commonResponseVo = CommonResponseVo.buildFailedResponse(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode(),ResponseCodeEnum.SYSTEM_EXCEPTION.getMsg());
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo updateOrderId(JSONObject params) {
        CommonResponseVo commonResponseVo;
        LOG.info("wcm-web.boe.updateVoucherId param:{}", JsonUtils.obj2json(params));
        try {
            commonResponseVo = serviceCaller.post(WCM_BOE_URL+"updateVoucherId",params,CommonResponseVo.class);
            LOG.info("wcm-web.boe.updateVoucherId result:{}", JsonUtils.obj2json(commonResponseVo));
        }catch (Exception e){
            commonResponseVo = CommonResponseVo.buildFailedResponse(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode(),ResponseCodeEnum.SYSTEM_EXCEPTION.getMsg());
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo queryOrderVoucher(JSONObject params) {
        CommonResponseVo commonResponseVo;
        LOG.info("wcm-web.boe.queryVoucherInfoByOrderId param:{}", JsonUtils.obj2json(params));
        try {
            commonResponseVo = serviceCaller.post(WCM_BOE_URL+"queryVoucherInfoByOrderId",params,CommonResponseVo.class);
            LOG.info("wcm-web.boe.queryVoucherInfoByOrderId result:{}", JsonUtils.obj2json(commonResponseVo));
        }catch (Exception e){
            commonResponseVo = CommonResponseVo.buildFailedResponse(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode(),ResponseCodeEnum.SYSTEM_EXCEPTION.getMsg());
        }
        return commonResponseVo;
    }
}

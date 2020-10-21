package com.ihomefnt.o2o.service.proxy.delivery;

import com.alibaba.fastjson.JSON;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.ConfirmNodeRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.FinalCheckParamRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.GetNodeDetailRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.GetHardScheduleVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.HardDetailVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.NodeDetailVo;
import com.ihomefnt.o2o.intf.proxy.delivery.DeliveryServiceProxy;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
@Service
public class DeliveryServiceProxyImpl implements DeliveryServiceProxy {

    @Resource
    private ServiceCaller serviceCaller;

    private static final Logger LOG = LoggerFactory.getLogger(DeliveryServiceProxy.class);

    /**
     * 交付单信息接口
     * @param orderId
     * @return
     */
    @Override
    public HardScheduleVo getSchedule(String orderId) {
        if (orderId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("orderId",orderId);
        LOG.info("aladdin-dms.back.deliver.getSchedule params:{}",params);
        long t1 = System.currentTimeMillis();
        ResponseVo<HardScheduleVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getSchedule", params, new TypeReference<ResponseVo<HardScheduleVo>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.getSchedule ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-dms.back.deliver.getSchedule times :{}ms  responseVo :{}", (t2 - t1), JSON.toJSONString(responseVo));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return responseVo.getData();
    }

    /**
     * 硬装进度接口
     * @param orderId
     * @return
     */
    @Override
    public HardDetailVo getHardDetail(String orderId) {
        if (orderId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("orderId",orderId);
        LOG.info("aladdin-dms.back.deliver.getHardDetail params:{}",params);
        long t1 = System.currentTimeMillis();
        ResponseVo<HardDetailVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getHardDetail", params, new TypeReference<ResponseVo<HardDetailVo>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.getHardDetail ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-dms.back.deliver.getHardDetail times :{}ms  response  {}", (t2 - t1), JSON.toJSONString(responseVo));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return responseVo.getData();
    }


    /**
     * 节点详细信息
     * @param request
     * @return
     */
    @Override
    public NodeDetailVo getNodeDetail(GetNodeDetailRequest request) {
        if (request == null) {
            return null;
        }
        LOG.info("aladdin-dms.back.deliver.getNodeDetail params:{}",request);
        long t1 = System.currentTimeMillis();
        ResponseVo<NodeDetailVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getNodeDetail", request, new TypeReference<ResponseVo<NodeDetailVo>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.getNodeDetail ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-dms.back.deliver.getNodeDetail times :{}ms response {}", (t2 - t1), JSON.toJSONString(responseVo));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return responseVo.getData();
    }


    /**
     * 客户节点验收接口
     * @param request
     * @return
     */
    @Override
    public boolean confirmNode(ConfirmNodeRequest request) {
        if (request == null) {
            return false;
        }
        LOG.info("aladdin-dms.back.deliver.confirmNode params:{}",request);
        long t1 = System.currentTimeMillis();
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.confirmNode", request, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.confirmNode ERROR:{}", e.getMessage());
            return false;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-dms.back.deliver.confirmNode times :{}ms  response {}", (t2 - t1), JSON.toJSONString(responseVo));

        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }


    /**
     * 完整计划日历接口
     * @param orderId
     * @return
     */
    @Override
    public GetHardScheduleVo getHardSchedule(String orderId) {
        if (orderId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("orderId",orderId);
        LOG.info("aladdin-dms.back.deliver.getHardSchedule params:{}",params);
        long t1 = System.currentTimeMillis();
        ResponseVo<GetHardScheduleVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getHardSchedule", params, new TypeReference<ResponseVo<GetHardScheduleVo>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.getHardSchedule ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-dms.back.deliver.getHardSchedule times :{}ms  response {}", (t2 - t1), JSON.toJSONString(responseVo));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return responseVo.getData();
    }


    /**
     * 整体验收接口
     * @param request
     * @return
     */
    @Override
    public boolean finalCheck(FinalCheckParamRequest request) {
        if (request == null) {
            return false;
        }
        LOG.info("aladdin-dms.back.deliver.finalCheck params:{}",request);
        long t1 = System.currentTimeMillis();
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.finalCheck", request, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.finalCheck ERROR:{}", e.getMessage());
            return false;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-dms.back.deliver.finalCheck times :{}ms  response {}", (t2 - t1), JSON.toJSONString(responseVo));

        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }
}

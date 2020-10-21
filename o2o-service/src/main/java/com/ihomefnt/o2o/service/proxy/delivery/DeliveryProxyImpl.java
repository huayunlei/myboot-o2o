package com.ihomefnt.o2o.service.proxy.delivery;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.delivery.dto.DeliverOrderInfo;
import com.ihomefnt.o2o.intf.domain.delivery.dto.ProductStatusListVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.ConfirmNodeRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.FinalCheckParamRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.GetNodeDetailRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.*;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDmsServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.delivery.DeliveryProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
@Service
public class DeliveryProxyImpl implements DeliveryProxy {

    @Resource
    private ServiceCaller serviceCaller;

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    private static final Logger LOG = LoggerFactory.getLogger(DeliveryProxy.class);

    /**
     * 交付单信息接口
     *
     * @param orderId
     * @return
     */
    @Override
    public HardScheduleVo getSchedule(String orderId) {
        if (orderId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        LOG.info("aladdin-dms.back.deliver.getSchedule params:{}", params);
        long t1 = System.currentTimeMillis();
        ResponseVo<HardScheduleVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getSchedule", params, new TypeReference<ResponseVo<HardScheduleVo>>() {
                    });
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
     *
     * @param orderId
     * @return
     */
    @Override
    public HardDetailVo getHardDetail(String appVersion, String orderId) {
        if (orderId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        LOG.info("aladdin-dms.back.deliver.getHardDetail params:{}", params);
        long t1 = System.currentTimeMillis();
        ResponseVo<HardDetailVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getHardDetail", params, new TypeReference<ResponseVo<HardDetailVo>>() {
                    });
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.getHardDetail ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-dms.back.deliver.getHardDetail times :{}ms  response  {}", (t2 - t1), JSON.toJSONString(responseVo));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        HardDetailVo hardDetail = responseVo.getData();
        if (VersionUtil.mustUpdate(appVersion, "5.4.5")) {
            if (hardDetail != null && hardDetail.getHardOrderDetailVo() != null) {
                if (hardDetail.getHardOrderDetailVo().getHardStatus() != null) {
                    HardOrderDetailVo hardOrderDetailVo = hardDetail.getHardOrderDetailVo();
                    switch (hardOrderDetailVo.getHardStatus()) {
                        case 3:
                            hardOrderDetailVo.setHardStatus(7);
                            break;
                        case 4:
                            hardOrderDetailVo.setHardStatus(3);
                            break;
                        case 5:
                            hardOrderDetailVo.setHardStatus(4);
                            break;
                        case 6:
                            hardOrderDetailVo.setHardStatus(5);
                            break;
                    }
                }
            }
        }
        return hardDetail;
    }


    /**
     * 节点详细信息
     *
     * @param request
     * @return
     */
    @Override
    public NodeDetailVo getNodeDetail(GetNodeDetailRequest request) {
        if (request == null) {
            return null;
        }
        LOG.info("aladdin-dms.back.deliver.getNodeDetail params:{}", request);
        long t1 = System.currentTimeMillis();
        ResponseVo<NodeDetailVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getNodeDetail", request, new TypeReference<ResponseVo<NodeDetailVo>>() {
                    });
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
     *
     * @param request
     * @return
     */
    @Override
    public boolean confirmNode(ConfirmNodeRequest request) {
        if (request == null) {
            return false;
        }
        LOG.info("aladdin-dms.back.deliver.confirmNode params:{}", request);
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
     *
     * @param orderId
     * @return
     */
    @Override
    public GetHardScheduleVo getHardSchedule(String orderId) {
        if (orderId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        LOG.info("aladdin-dms.back.deliver.getHardSchedule params:{}", params);
        long t1 = System.currentTimeMillis();
        ResponseVo<GetHardScheduleVo> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-dms.back.deliver.getHardSchedule", params, new TypeReference<ResponseVo<GetHardScheduleVo>>() {
                    });
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
     *
     * @param request
     * @return
     */
    @Override
    public boolean finalCheck(FinalCheckParamRequest request) {
        ResponseVo responseVo = strongSercviceCaller
                .post(AladdinDmsServiceNameConstants.API_DELIVER_FINALCHECK, request, ResponseVo.class);
        if (responseVo.getCode().equals(30000)) {
            return true;
        } else if (responseVo.getCode().equals(100) || responseVo.getCode().equals(101) || responseVo.getCode().equals(102)) {
            throw new BusinessException(HttpReturnCode.DMS_FAIL_FINAL_CHECK_ERROR, MessageConstant.FINAL_CHECK_ERROR_MESSAGE_FOR_SALES_RETURN);
        } else if (responseVo.getCode().equals(103)) {
            throw new BusinessException(HttpReturnCode.DMS_FAIL_FINAL_CHECK_ERROR, MessageConstant.FINAL_CHECK_ERROR_MESSAGE_FOR_UNINSTALLED);
        } else if (responseVo.getCode().equals(104)) {
            throw new BusinessException(HttpReturnCode.DMS_FAIL_FINAL_CHECK_ERROR, MessageConstant.FINAL_CHECK_ERROR_MESSAGE_RESET_PRICE_CHECK);
        }
        return false;
    }

    public List<DeliverInfoVo> queryByOrderIdList(List<Integer> orderIdList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderIdList", orderIdList);
        LOG.info("serviceCaller post aladdin-dms.back.deliver.queryByOrderIdList params:{}", params);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = serviceCaller.post("aladdin-dms.back.deliver.queryByOrderIdList", params,
                    ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.queryByOrderIdList ERROR:{}", e.getMessage());
            return null;
        }
        if (responseVo == null) {
            return null;
        }
        List<DeliverInfoVo> vo = null;
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                vo = JsonUtils.json2list(JsonUtils.obj2json(responseVo.getData()), DeliverInfoVo.class);
            }
        }
        LOG.info("serviceCaller aladdin-dms.back.deliver.queryByOrderIdList :{}", JsonUtils.obj2json(responseVo));
        return vo;
    }

    public DmsRequiredVo queryOrderDeliverInfo(Integer orderNum) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNum", orderNum);
        LOG.info("serviceCaller post aladdin-dms.back.deliver.queryOrderDeliverInfo params:{}", params);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = serviceCaller.post("aladdin-dms.back.deliver.queryOrderDeliverInfo", params,
                    ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-dms.back.deliver.queryOrderDeliverInfo ERROR:{}", e.getMessage());
            return null;
        }
        if (responseVo == null) {
            return null;
        }
        DmsRequiredVo vo = null;
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), DmsRequiredVo.class);
            }
        }
        LOG.info("serviceCaller aladdin-dms.back.deliver.queryOrderDeliverInfo :{}", JsonUtils.obj2json(responseVo));
        return vo;
    }

    @Override
    public List<ProductStatusListVo> queryProductStatus(Integer orderNum) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNum", orderNum);
        LOG.info("serviceCaller post aladdin-dms.deliver.product.queryProductStatus params:{}", params);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = serviceCaller.post("aladdin-dms.deliver.product.queryProductStatus", params,
                    ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-dms.deliver.product.queryProductStatus ERROR:{}", e.getMessage());
            return null;
        }
        if (responseVo == null) {
            return null;
        }
        List<ProductStatusListVo> vo = null;
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                vo = JsonUtils.json2list(JsonUtils.obj2json(responseVo.getData()), ProductStatusListVo.class);
            }
        }
        LOG.info("serviceCaller aladdin-dms.deliver.product.queryProductStatus :{}", JsonUtils.obj2json(responseVo));
        return vo;
    }

    @Override
    public NodeDetailVo getNodeDetailV2(String nodeId, Integer orderId) {
        return ((ResponseVo<NodeDetailVo>) strongSercviceCaller.post(AladdinDmsServiceNameConstants.DELIVER_GET_NODE_DETAIL_V2, Maps.newHashMap("nodeId", nodeId, "orderId", orderId), new TypeReference<ResponseVo<NodeDetailVo>>() {
        })).getData();
    }

    /**
     * 根据订单号批量查询订单交付信息
     *
     * @param orderIdList
     * @return
     */
    @Override
    public List<DeliverOrderInfo> queryDeliverOrderInfoListByOrderIdList(List<Integer> orderIdList) {
        return ((ResponseVo<List<DeliverOrderInfo>>) strongSercviceCaller.post(AladdinDmsServiceNameConstants.QUERY_DELIVER_ORDERINFO_LIST_BY_ORDER_ID_LIST, Maps.newHashMap("orderIdList", orderIdList), new TypeReference<ResponseVo<List<DeliverOrderInfo>>>() {
        })).getData();
    }
}

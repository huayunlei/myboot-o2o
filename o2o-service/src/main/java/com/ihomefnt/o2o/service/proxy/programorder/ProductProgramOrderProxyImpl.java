/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年3月15日
 * Description:ProductProgramOrderProxy.java
 */
package com.ihomefnt.o2o.service.proxy.programorder;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingSchemeRecord;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.response.HousePatternConfigResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomIdRelationDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CreateFamilyOrderRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.QueryFamilyOrderPriceRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.AddCustomerProjectResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.home.AddCustomerResponseEnum;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.DollyWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.service.common.AiDingTalk;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.testng.collections.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhang
 */
@Service
public class ProductProgramOrderProxyImpl {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Autowired
    private AiDingTalk aiDingTalk;

    private static final Logger LOG = LoggerFactory.getLogger(ProductProgramOrderProxyImpl.class);

    /**
     * 创建订单
     *
     * @param param
     * @return
     */
    public AladdinCreateOrderResponseVo createOrder(Object param) {
        //全品家大订单
        ResponseVo<AladdinCreateOrderResponseVo> responseVo = null;
        try {

            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.CREATE_FAMILY_ORDER, param,
                    new TypeReference<ResponseVo<AladdinCreateOrderResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 5.0创建订单
     *
     * @param param
     * @return
     */
    public AladdinCreateOrderResponseVo createFamilyOrder(Object param) {
        ResponseVo<AladdinCreateOrderResponseVo> responseVo = null;
        try {

            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.CREATE_FAMILY_ORDER, param,
                    new TypeReference<ResponseVo<AladdinCreateOrderResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            createOrUpdateOrderError("ProductProgramOrderProxy.createFamilyOrder", AladdinOrderServiceNameConstants.CREATE_FAMILY_ORDER
                    , JsonUtils.obj2json(param), null, null);
            return null;
        }
        if (responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }

        createOrUpdateOrderError("ProductProgramOrderProxy.createFamilyOrder", AladdinOrderServiceNameConstants.CREATE_FAMILY_ORDER
                , JsonUtils.obj2json(param), responseVo.getCode().longValue(), responseVo.getMsg());
        AladdinCreateOrderResponseVo vo = new AladdinCreateOrderResponseVo();
        vo.setCode(responseVo.getCode());
        return vo;
    }

    /**
     * 5.0查询订单价格
     *
     * @param param
     * @return AladdinQueryFamilyOrderPriceResponseVo
     */
    public AladdinQueryFamilyOrderPriceResponseVo queryFamilyOrderPrice(QueryFamilyOrderPriceRequest param) {
        //全品家大订单
        ResponseVo<AladdinQueryFamilyOrderPriceResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_FAMILY_ORDER_PRICE, param,
                    new TypeReference<ResponseVo<AladdinQueryFamilyOrderPriceResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 订单支付明细
     *
     * @param transactionType
     * @param orderNum
     * @return
     */
    public PageModel<TransactionListVo> queryPayDetailInfoListWithParam(String orderNum, Integer transactionType) {
        if (StringUtils.isEmpty(orderNum)) {
            return null;
        }
        ResponseVo<PageModel<TransactionListVo>> responseVo = null;
//                CONFIRMING(0, "待确认收款"),
//                CONFIRMED(1, "已确认收款"),
//                REJECTED(2, "已驳回收款"),
//                PENDING_REFUND(10, "待审批退款"),
//                TOBE_REFUND(11, "待退款"),
//                REFUNDED(12, "已退款"),
//                REJECTED_REFUND(13, "已驳回退款"),
//                REFUSE_REFUND(14, "拒绝退款"),
//                CANCLE_REFUND(15, "已撤销"),这边只要成功的
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_TRANSACTION_LIST, Maps.newHashMap("orderNum", orderNum, "amountStatusList", Lists.newArrayList(1, 12), "pageNo", 1, "pageSize", Integer.MAX_VALUE, "transactionType", transactionType),
                    new TypeReference<ResponseVo<PageModel<TransactionListVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    public HomeCarnivalInfoResponseVo queryOrderActRecordByUser(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        ResponseVo<HomeCarnivalInfoResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_ACT_RECORD_BY_USER, paramMap,
                    new TypeReference<ResponseVo<HomeCarnivalInfoResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    public AladdinOrderDetailResponseVo queryOrderListByParam(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        ResponseVo<AladdinOrderDetailResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_LIST_BY_PARAM, paramMap,
                    new TypeReference<ResponseVo<AladdinOrderDetailResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }


    public ResponseVo<Boolean> confirmRequriement(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        ResponseVo<Boolean> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.CONFIRM_REQURIEMENT, paramMap,
                    new TypeReference<ResponseVo<Boolean>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null) {
            return responseVo;
        }
        return null;
    }

    public Integer cancelScheme(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.CANCEL_SCHEME, paramMap,
                    ResponseVo.class);
        } catch (Exception e) {
            return 0;
        }
        if (responseVo == null || responseVo.getCode() == null) {
            return 0;
        }
        return responseVo.getCode();
    }

    public List<QueryContractListResponseVo> queryContractList(Integer orderId) {
        if (orderId == null || orderId < 1) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderId);
        ResponseVo<List<QueryContractListResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_CONTRACT_LIST, paramMap,
                    new TypeReference<ResponseVo<List<QueryContractListResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    public Integer addCustomerHouseInfo(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        ResponseVo<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.ADD_CUSTOMER_HOUSE_INFO, paramMap,
                    new TypeReference<ResponseVo<Integer>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && !responseVo.isSuccess()) {
            if (responseVo.getCode().equals(AddCustomerResponseEnum.HOUSE_REPEAT.getStatus())) {
                throw new BusinessException(HttpResponseCode.ADD_HOUSE_REPEAT, AddCustomerResponseEnum.getDescription(responseVo.getCode()));
            }
            throw new BusinessException(AddCustomerResponseEnum.getDescription(responseVo.getCode()));
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    public AppSystemInfoResponseVo queryAppSystemInfo() {
        ResponseVo<AppSystemInfoResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_APP_SYSTEM_INFO, null,
                    new TypeReference<ResponseVo<AppSystemInfoResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 夏铭宇2018/3/21新增
     * 查询订单选方案信息
     *
     * @return
     */
    public SolutionInfo querySolutionInfo(Integer orderId) {
        ResponseVo<SolutionInfo> responseVo = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderId);
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_SOLUTION_INFO, paramMap,
                    new TypeReference<ResponseVo<SolutionInfo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    public AppOrderBaseInfoResponseVo queryAppOrderBaseInfo(Integer orderId) {
        ResponseVo<AppOrderBaseInfoResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_APP_ORDER_BASE_INFO, orderId,
                    new TypeReference<ResponseVo<AppOrderBaseInfoResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 根据订单id查询订单明细
     *
     * @param orderId
     * @return
     */
    public OrderDetailResultDto queryOrderDetailById(Integer orderId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        CommonResponseVo<OrderDetailResultDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_DETAIL, paramMap,
                    new TypeReference<CommonResponseVo<OrderDetailResultDto>>() {
                    });
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.ALADDIN_ORDER_FAILED, MessageConstant.FAILED);
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    /**
     * 根据订单id查询代客下单信息
     *
     * @param orderId
     * @return
     */
    public ValetOrderResultDto queryValetOrderDetail(Integer orderId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("masterOrderNum", orderId);
        paramMap.put("orderType", 11);
        CommonResponseVo<ValetOrderResultDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_VALET_ORDER_DETAIL, paramMap,
                    new TypeReference<CommonResponseVo<ValetOrderResultDto>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    /**
     * 更新订单
     *
     * @param request
     * @return
     */
    public AladdinCreateOrderResponseVo updateFamilyOrder(CreateFamilyOrderRequest request) {
        //全品家大订单
        ResponseVo<AladdinCreateOrderResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.UPDATE_FAMILY_ORDER, request,
                    new TypeReference<ResponseVo<AladdinCreateOrderResponseVo>>() {
                    });
        } catch (Exception e) {
            createOrUpdateOrderError("ProductProgramOrderProxy.updateFamilyOrder", AladdinOrderServiceNameConstants.UPDATE_FAMILY_ORDER
                    , JsonUtils.obj2json(request), null, e.getMessage());
            return null;
        }
        if (responseVo == null) {
            createOrUpdateOrderError("ProductProgramOrderProxy.updateFamilyOrder", AladdinOrderServiceNameConstants.UPDATE_FAMILY_ORDER
                    , JsonUtils.obj2json(request), null, null);
            return null;
        }
        if (!responseVo.isSuccess()) {
            if (responseVo.getCode() == null) {
                createOrUpdateOrderError("ProductProgramOrderProxy.updateFamilyOrder", AladdinOrderServiceNameConstants.UPDATE_FAMILY_ORDER
                        , JsonUtils.obj2json(request), null, null);
                return null;
            }
            createOrUpdateOrderError("ProductProgramOrderProxy.updateFamilyOrder", AladdinOrderServiceNameConstants.UPDATE_FAMILY_ORDER
                    , JsonUtils.obj2json(request), responseVo.getCode().longValue(), responseVo.getMsg());
            AladdinCreateOrderResponseVo vo = new AladdinCreateOrderResponseVo();
            vo.setCode(responseVo.getCode());
            return vo;
        }
        if (responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 调用下单服务失败
     *
     * @param classMethodName
     * @param url
     * @param params
     * @param code
     * @param msg
     */
    public void createOrUpdateOrderError(String classMethodName, String url, String params, Long code, String msg) {
        aiDingTalk.asynSendOrderDingTalkWarn(classMethodName, url, params, code, msg);
    }

    public BuildingSchemeRecord buildingSchemeRecord(Integer buildingId) {
        ResponseVo<BuildingSchemeRecord> responseVo = null;
        try {

            Integer param = buildingId == null ? 0 : buildingId;
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_BUILDING_SCHEME_RECORD, param,
                    new TypeReference<ResponseVo<BuildingSchemeRecord>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || responseVo.getData() == null) {
            return null;
        }

        return responseVo.getData();
    }

    public List<SnapshotOrderRoomInfoDto> batchQueryRoomSkuInfo(Integer orderId) {
        ResponseVo<List<SnapshotOrderRoomInfoDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ROOM_SKU_INFO, orderId,
                    new TypeReference<ResponseVo<List<SnapshotOrderRoomInfoDto>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || responseVo.getData() == null) {
            return null;
        }

        return responseVo.getData();
    }

    public List<QueryMasterOrderIdByHouseIdResultDto> queryMasterOrderIdsByHouseIds(List<Integer> customerHouseId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("customerHouseIds", customerHouseId);

        ResponseVo<List<QueryMasterOrderIdByHouseIdResultDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_MASTER_ORDER_IDS_BY_HOUSE_IDS, param,
                    new TypeReference<ResponseVo<List<QueryMasterOrderIdByHouseIdResultDto>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || responseVo.getData() == null) {
            return null;
        }

        return responseVo.getData();
    }

    public RoomIdRelationDto batchQueryRoomIdRelation(Set<Integer> roomIdList) {
        return ((ResponseVo<RoomIdRelationDto>) strongSercviceCaller.post(DollyWebServiceNameConstants.BATCH_QUERY_ROOMID_RELATION, Maps.newHashMap("roomIdList", roomIdList),
                new TypeReference<ResponseVo<RoomIdRelationDto>>() {
                })).getData();
    }

    public AddCustomerProjectResponseVo addCustomerProject(AddCustomerProjectDto addCustomerProjectDto) {
        return ((ResponseVo<AddCustomerProjectResponseVo>) strongSercviceCaller.post(DollyWebServiceNameConstants.PROJECT_ADD_CUSTOME_RPROJECT, addCustomerProjectDto,
                new TypeReference<ResponseVo<AddCustomerProjectResponseVo>>() {
                })).getData();
    }

    public HousePatternConfigResponse queryHousePatternConfig() {
        return ((ResponseVo<HousePatternConfigResponse>) strongSercviceCaller.post(DollyWebServiceNameConstants.CUSTOPER_PROJECR_APARTMENT_QUERY_PATTERN_CONFIG, null,
                new TypeReference<ResponseVo<HousePatternConfigResponse>>() {
                })).getData();
    }
}

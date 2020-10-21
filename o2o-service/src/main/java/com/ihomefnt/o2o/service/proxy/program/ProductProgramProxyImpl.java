package com.ihomefnt.o2o.service.proxy.program;

import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingNoResponseVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.QueryPriceDiffDTO;
import com.ihomefnt.o2o.intf.domain.program.dto.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.BatchQuerySolutionByHouseIdRequestVo;
import com.ihomefnt.o2o.intf.domain.program.vo.request.HardMoreRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.HouseProjectRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.MoreInfoRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesignVo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CreateFamilyOrderRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.*;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品方案BOSS\WCM端代理
 *
 * @author ZHAO
 * @see 192.168.1.12:10025/dolly-web/swagger/
 * @see 192.168.1.12:10025/dolly-web/swagger/
 */
@Service
public class ProductProgramProxyImpl implements ProductProgramProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;


    @Override
    public List<SolutionSketchInfoResponseVo> getUserSpecificProgram(Integer userId, Integer houseProjectId,
                                                                     Integer zoneId, Integer houseTypeId) {
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("houseProjectId", houseProjectId);
        if (zoneId != null) {
            param.put("zoneId", zoneId);
        }
        param.put("houseTypeId", houseTypeId);
        ResponseVo<List<SolutionSketchInfoResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.querySolutionSketchWithUserInfo", param,
                    new TypeReference<ResponseVo<List<SolutionSketchInfoResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "o2o-api", keyGenerator = "springCacheKeyGenerator")
    public SolutionDetailResponseVo getProgramDetailById(Integer solutionId) {
        ResponseVo<SolutionDetailResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.querySolutionDetailWithId", solutionId,
                    new TypeReference<ResponseVo<SolutionDetailResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }


    @Override
    @Deprecated
    public List<SolutionRoomBaseInfoResponseVo> getRoomListByCondition(Integer userId, Integer houseProjectId,
                                                                       Integer zoneId, Integer houseTypeId) {
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("houseProjectId", houseProjectId);
        if (zoneId != null) {
            param.put("zoneId", zoneId);
        }
        param.put("houseTypeId", houseTypeId);
        ResponseVo<List<SolutionRoomBaseInfoResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.querySolutionRoomNamesWithHouseTypeId", param,
                    new TypeReference<ResponseVo<List<SolutionRoomBaseInfoResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public SolutionRoomDetailResponseVo getRoomDetailByCondition(Integer solutionId, Integer solutionRoomId) {
        JSONObject param = new JSONObject();
        param.put("solutionId", solutionId);
        if (solutionRoomId != null) {
            param.put("solutionRoomId", solutionRoomId);
        }
        ResponseVo<SolutionRoomDetailResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.queryCertainSolutionRoomDetailWithParams", param,
                    new TypeReference<ResponseVo<SolutionRoomDetailResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    @Deprecated
    public List<AvailableSolutionRoomResponseVo> getRoomProgramListByCondition(Integer userId, Integer houseProjectId,
                                                                               Integer houseTypeId, Integer roomTypeId) {
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("houseProjectId", houseProjectId);
        param.put("houseTypeId", houseTypeId);
        param.put("roomTypeId", roomTypeId);
        ResponseVo<List<AvailableSolutionRoomResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(
                    "dolly-web.solution-app.queryAvailableSolutionRoomWithHouseTypeAndRoomType", param,
                    new TypeReference<ResponseVo<List<AvailableSolutionRoomResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public CustomerDetailInfo getUserInfo(String mobileNum) {
        JSONObject param = new JSONObject();
        param.put("mobile", mobileNum);
        ResponseVo<CustomerDetailInfo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.api-customer.getCustomerInfo", param,
                    new TypeReference<ResponseVo<CustomerDetailInfo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<SolutionAddBagInfoVo> queryExtraItemWithSolutionIds(List<Integer> solutionIds) {
        ResponseVo<List<SolutionAddBagInfoVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.queryExtraItemWithSolutionIds", solutionIds,
                    new TypeReference<ResponseVo<List<SolutionAddBagInfoVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<SolutionAddBagInfoVo> queryExtraItemWithSkuIds(List<Integer> skuIdList) {
        ResponseVo<List<SolutionAddBagInfoVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.queryExtraItemWithSkuIds", skuIdList,
                    new TypeReference<ResponseVo<List<SolutionAddBagInfoVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    //@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
    public SolutionBuildingProgramResponseVo querySolutionSketchWithBuildingId(Integer buildingId, Integer zoneId,String solutionName,Integer houseTypeId) {
        JSONObject param = new JSONObject();
        param.put("buildingId", buildingId);
        if (zoneId != null) {
            param.put("zoneId", zoneId);
        }
        if (solutionName != null) {
            param.put("solutionName", solutionName);
        }
        if(houseTypeId!=null){
            param.put("houseId", houseTypeId);
        }
        ResponseVo<SolutionBuildingProgramResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.querySolutionSketchWithBuildingId", param,
                    new TypeReference<ResponseVo<SolutionBuildingProgramResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }


    @Override
    public SolutionStandardUpgradesResponseVo querySolutionStandardUpgrades(List<Integer> roomIdList) {
        ResponseVo<SolutionStandardUpgradesResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.querySolutionStandardUpgrades", roomIdList,
                    new TypeReference<ResponseVo<SolutionStandardUpgradesResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<OptionalSkusResponseVo> queryOptionalSkus(Integer roomId, Integer parentSkuId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roomId", roomId);
        if (parentSkuId != null && parentSkuId > 0) {
            params.put("parentSkuId", parentSkuId);
        }
        ResponseVo<List<OptionalSkusResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.queryOptionalSkus", params,
                    new TypeReference<ResponseVo<List<OptionalSkusResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    //@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
    public List<OptionalSkusResponseVo> queryOptionalSkusByRoomIds(List<Integer> roomIds) {
        ResponseVo<List<OptionalSkusResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.queryOptionalSkusByRoomIds", roomIds,
                    new TypeReference<ResponseVo<List<OptionalSkusResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<ProvinceLocationResponseVo> queryLocationInfo() {
        Map<String, Object> params = new HashMap<String, Object>();
        ResponseVo<List<ProvinceLocationResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.api-project.queryLocationInfo", params,
                    new TypeReference<ResponseVo<List<ProvinceLocationResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<UsableCompanyResponseVo> querySolutionBuildingInfo(ProjectSearchVo params) {
        ResponseVo<List<UsableCompanyResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.querySolutionBuildingInfo", params,
                    new TypeReference<ResponseVo<List<UsableCompanyResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<OptionalRoomResponseVo> queryRoomDesignWithSolutionId(Integer solutionId) {
        ResponseVo<List<OptionalRoomResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.queryRoomDesignWithSolutionId", solutionId,
                    new TypeReference<ResponseVo<List<OptionalRoomResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "o2o-api", keyGenerator = "springCacheKeyGenerator")
    public List<BuildingNoResponseVo> queryBuildingUnitNoByZoneId(Integer zoneId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("zoneId", zoneId);
        params.put("filterFlag", 0);//测试要求过滤其他
        ResponseVo<List<BuildingNoResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.api-project.queryBuildingUnitNoByZoneId", params,
                    new TypeReference<ResponseVo<List<BuildingNoResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<CompareSolutionResponseVo> compareSolution(Integer userId, Integer houseProjectId, Integer zoneId,
                                                           Integer houseTypeId) {
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("houseProjectId", houseProjectId);
        if (zoneId != null) {
            param.put("zoneId", zoneId);
        }
        param.put("houseTypeId", houseTypeId);
        ResponseVo<List<CompareSolutionResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.compareSolution", param,
                    new TypeReference<ResponseVo<List<CompareSolutionResponseVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public SolutionEffectResponse querySolutionListByHouseId(Integer houseTypeId, Integer orderId, Long solutionId) {
        JSONObject param = new JSONObject();
        param.put("apartmentId", houseTypeId);
        param.put("orderNum", orderId);
        param.put("solutionId", solutionId);
        ResponseVo<SolutionEffectResponse> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.querySolutionListByHouseId", param,
                    new TypeReference<ResponseVo<SolutionEffectResponse>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public SolutionEffectResponse querySolutionList(Map<String, Object> params) {
        ResponseVo<SolutionEffectResponse> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_SOLUTION_LIST, params,
                    new TypeReference<ResponseVo<SolutionEffectResponse>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public SolutionEffectResponse queryRoomListBySolutionId(Map<String, Object> params) {
        ResponseVo<SolutionEffectResponse> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_ROOM_LIST, params,
                    new TypeReference<ResponseVo<SolutionEffectResponse>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public RoomAndHardItemInfoVo queryHardSelectionList(List<Integer> spaceIds) {
        ResponseVo<RoomAndHardItemInfoVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.batchQueryRoomHardItemInfo", spaceIds,
                    new TypeReference<ResponseVo<RoomAndHardItemInfoVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public Integer queryAvailableSolutionCount(Integer houseProjectId, Integer houseTypeId, Long orderNum) {
        JSONObject param = new JSONObject();
        param.put("houseProjectId", houseProjectId);
        param.put("houseTypeId", houseTypeId);
        param.put("orderNum", orderNum);
        ResponseVo<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.solution-app.queryAvailableSolutionCount", param,
                    new TypeReference<ResponseVo<Integer>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<THouseResponse> batchQueryHouse(List<Integer> houseIds) {
        ResponseVo<List<THouseResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("dolly-web.api-house.batchQueryHouse", houseIds,
                    new TypeReference<ResponseVo<List<THouseResponse>>>() {
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
     * 查询空间软装列表
     *
     * @param spaceIdList
     * @return
     */
    @Override
    public List<OptionalSkusResponseVo> batchQuerySoftItemByRoom(List<Integer> spaceIdList) {
        return ((ResponseVo<List<OptionalSkusResponseVo>>) strongSercviceCaller.post("dolly-web.solution-app.batchQuerySoftItemByRoom", spaceIdList.stream().map(spaceId -> Maps.newHashMap("roomId", spaceId)).collect(Collectors.toList()),
                new TypeReference<ResponseVo<List<OptionalSkusResponseVo>>>() {
                })).getData();
    }

    /**
     * 查询空间硬装列表
     *
     * @param spaceIdList
     * @return
     */
    @Override
    public List<SpaceDesignVo> batchQueryHardItemByRoom(List<Integer> spaceIdList) {
        List<SpaceDesignVo> roomList = ((ResponseVo<List<SpaceDesignVo>>) strongSercviceCaller.post("dolly-web.solution-app.batchQueryHardItemByRoom", spaceIdList.stream().map(spaceId -> Maps.newHashMap("roomId", spaceId)).collect(Collectors.toList()),
                new TypeReference<ResponseVo<List<SpaceDesignVo>>>() {
                })).getData();
        if (roomList == null) {
            throw new BusinessException(HttpReturnCode.DOLLY_FAILED, MessageConstant.FAILED);
        }
        return roomList;
    }

    /**
     * 查询软装可替换列表
     *
     * @param request
     * @return
     */
    @Override
    public OptionalSkusResponseVo queryReplaceSoftItemByRoom(MoreInfoRequest request) {
        return ((ResponseVo<OptionalSkusResponseVo>) strongSercviceCaller.post(DollyWebServiceNameConstants.SOLUTION_APP_QUERYREPLACESOFTITEMBYROOM, Maps.newHashMap("furnitureType", request.getFurnitureType(), "roomId", request.getSpaceId(), "skuId", request.getSkuId(), "selectedSkuId", request.getSelectedSkuId()), new TypeReference<ResponseVo<OptionalSkusResponseVo>>() {
        })).getData();
    }

    /**
     * 查询硬装可替换列表
     *
     * @param request
     * @return
     */
    @Override
    public SpaceDesignVo queryReplaceHardItemByRoom(HardMoreRequest request) {
        return ((ResponseVo<SpaceDesignVo>) strongSercviceCaller.post(DollyWebServiceNameConstants.SOLUTION_APP_QUERYREPLACEHARDITEMBYROOM, Maps.newHashMap("categoryIdList", request.getCategoryIdList(), "craftId", request.getCraftId(), "roomId", request.getSpaceId(), "skuId", request.getHardItemId(), "classifyId", request.getClassifyId(), "selectedCraftId", request.getSelectedCraftId(), "selectedSkuId", request.getSelectedHardItemId(), "bomParam", request.getBomParam(), "pageNo", request.getPageNo(), "pageSize", request.getPageSize()), new TypeReference<ResponseVo<SpaceDesignVo>>() {
        })).getData();

    }

    /**
     * dolly校验下单方案、空间、sku是否下架
     *
     * @param request
     * @return
     */
    @Override
    public ValidateParamResultDto dollyValidateOrderParam(CreateFamilyOrderRequest request) {
        return ((ResponseVo<ValidateParamResultDto>) strongSercviceCaller.post(DollyWebServiceNameConstants.DOLLY_ORDER_VALIDATE_PARAM, request,
                new TypeReference<ResponseVo<ValidateParamResultDto>>() {
                })).getData();
    }

    /**
     * 查询方案及自由搭配空间详细信息
     *
     * @param queryPriceDiffDTO
     * @return
     */
    @Override
    public QueryPriceDiffDTO queryPriceDiff(QueryPriceDiffDTO queryPriceDiffDTO) {
        return ((ResponseVo<QueryPriceDiffDTO>) strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_QUERY_SOLUTION_AND_ROOM_INFO, queryPriceDiffDTO,
                new TypeReference<ResponseVo<QueryPriceDiffDTO>>() {
                })).getData();
    }

    /**
     * 查询aladdin用户房产
     *
     * @param userId
     * @return
     */
    @Override
    public List<AladdinUserHouseInfo> queryAladdinUserHouseInfo(Integer userId) {
        return ((ResponseVo<List<AladdinUserHouseInfo>>) strongSercviceCaller.post(AladdinOrderServiceNameConstants.HOUSEPROPERTY_APP_QUERYHOUSEINFOLISTBYUSERID, Maps.newHashMap("userId", userId),
                new TypeReference<ResponseVo<List<AladdinUserHouseInfo>>>() {
                })).getData();
    }

    @Override
    public List<MasterOrderSimpleInfo> queryMasterOrderIdsByHouseIds(List<Integer> houseIdList) {
        return ((ResponseVo<List<MasterOrderSimpleInfo>>) strongSercviceCaller.post(AladdinOrderServiceNameConstants.ALADDIN_ORDER_MASTERORDER_APP_QUERYMASTERORDERIDSBYHOUSEIDS, Maps.newHashMap("customerHouseIds", houseIdList),
                new TypeReference<ResponseVo<List<MasterOrderSimpleInfo>>>() {
                })).getData();
    }

    /**
     * 批量查询方案信息
     *
     * @param solutionIdList
     * @return
     */
    @Override
    public BatchSolutionBaseInfoVo batchQuerySolutionBaseInfo(List<Integer> solutionIdList) {
        return ((ResponseVo<BatchSolutionBaseInfoVo>) strongSercviceCaller.post(DollyWebServiceNameConstants.BATCH_QUERY_SOLUTION_BASEINFO, Maps.newHashMap("solutionIdList", solutionIdList),
                new TypeReference<ResponseVo<BatchSolutionBaseInfoVo>>() {
                })).getData();
    }

    /**
     * 批量查询方案信息
     *
     * @param request
     * @return
     */
    @Override
    public BatchSolutionBaseInfoVo batchQuerySolutionBaseInfo(BatchQuerySolutionByHouseIdRequestVo request) {
        return ((ResponseVo<BatchSolutionBaseInfoVo>) strongSercviceCaller.post(DollyWebServiceNameConstants.BATCH_QUERY_SOLUTION_BASEINFO, request,
                new TypeReference<ResponseVo<BatchSolutionBaseInfoVo>>() {
                })).getData();
    }

    /**
     * roomID 对应关系
     *
     * @return
     */
    @Override
    public List<RelRoomIdVo> listAllRoomClassRel() {
        return ((ResponseVo<List<RelRoomIdVo>>) strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_ALL_ROOM_CLASS_REL, null,
                new TypeReference<ResponseVo<List<RelRoomIdVo>>>() {
                })).getData();
    }

    /**
     * 查询替换信息接口
     *
     * @param groupIdList
     * @return
     */
    @Override
    public QueryCabinetPropertyListResponseDolly queryCabinetPropertyList(List<Integer> groupIdList) {
        return ((ResponseVo<QueryCabinetPropertyListResponseDolly>) strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_BOM_GROUP_COMPONENT_DETAIL, Maps.newHashMap("groupIdList", groupIdList),
                new TypeReference<ResponseVo<QueryCabinetPropertyListResponseDolly>>() {
                })).getData();
    }

    /**
     * 根据方案id查服务费详情
     *
     * @param solutionId
     * @return
     */
    @Override
    public ServiceItemResponse querySolutionService(Long solutionId) {
        return ((ResponseVo<ServiceItemResponse>) strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_SOLUTION_SERVICE, Maps.newHashMap("solutionId", solutionId),
                new TypeReference<ResponseVo<ServiceItemResponse>>() {
                })).getData();
    }

    @Override
    public void markUserSolutionReadRecord(Integer programId, Integer userId) {
        strongSercviceCaller.post(WcmWebServiceNameConstants.MARK_USER_SOLUTION_READ_RECORD, Maps.newHashMap("solutionId", programId, "userId", userId),
                ResponseVo.class);
    }

    @Override
    public List<Integer> queryUserSolutionReadListByUserId(Integer userId) {
        if (userId==null){
            return Lists.newArrayList();
        }
        return ((HttpBaseResponse<List<Integer>>) strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_USER_SOLUTION_READ_LIST_BY_USERID, Maps.newHashMap("userId", userId),
                new TypeReference<HttpBaseResponse<List<Integer>>>() {
                })).getObj();
    }

    @Override
    public AvailableChildCategoryResponse queryRelationCategory(Integer categoryId) {
        return ((ResponseVo<AvailableChildCategoryResponse>) strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_RELATION_CATEGORY, Maps.newHashMap("categoryId", categoryId),
                new TypeReference<ResponseVo<AvailableChildCategoryResponse>>() {
                })).getData();
    }

    @Override
    public List<BetaUserDto> batchQueryBetaUserByMobileList(List<String> mobiles) {
        return ((ResponseVo<List<BetaUserDto>>) strongSercviceCaller.post(HeimdallrWebServiceNameConstants.BATCH_QUERY_BETA_USER, Maps.newHashMap("mobiles", mobiles, "includeDeleted", false),
                new TypeReference<ResponseVo<List<BetaUserDto>>>() {
                })).getData();
    }

    @Override
    public List<HtpHouseTypeResponse> queryHouseListByProjectId(HouseProjectRequest request) {
        JSONObject object = new JSONObject();
        object.put("isOriginal",1);//查原户型
        object.put("projectId",request.getBuildingId());

        ResponseVo<List<HtpHouseTypeResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(HtpHouseServiceNameConstants.LIST_HOUSE_BY_PROJECT_ID, object,
                    new TypeReference<ResponseVo<List<HtpHouseTypeResponse>>>() {});
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.FTP_FAILED, MessageConstant.FAILED);
        }
        if(responseVo == null || !responseVo.isSuccess()){
            throw new BusinessException(HttpReturnCode.FTP_FAILED, MessageConstant.FAILED);
        }
        return responseVo.getData();
    }

}

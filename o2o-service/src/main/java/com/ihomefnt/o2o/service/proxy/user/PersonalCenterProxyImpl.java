package com.ihomefnt.o2o.service.proxy.user;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.user.dto.*;
import com.ihomefnt.o2o.intf.domain.user.vo.ResourceVo;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liyonggang
 * @create 2019-02-21 09:27
 */
@Repository
public class PersonalCenterProxyImpl implements PersonalCenterProxy {

    @Autowired
    private StrongSercviceCaller serviceCaller;

    /**
     * 从wcm查询个人中心资源
     *
     * @return
     */
    @Override
    public List<ResourceVo> queryPersonalCenterResoureData() {
        HttpBaseResponse<List<ResourceVo>> responseVo = serviceCaller.post("wcm-web.personalCenter.queryAllPersonalCenterResourse", null, new TypeReference<HttpBaseResponse<List<ResourceVo>>>() {
        });
        if (responseVo.getCode().equals(HttpResponseCode.SUCCESS)) {
            return responseVo.getObj();
        }
        return null;
    }

    /**
     * 根据用户id查询订单列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<AppMasterOrderResultDto> queryMasterOrderListByUserId(Integer userId) {
        ResponseVo<List<AppMasterOrderResultDto>> responseVo = serviceCaller.post("aladdin-order.masterOrder-app.queryMasterOrderListByUserId", Maps.newHashMap("userId", userId), new TypeReference<ResponseVo<List<AppMasterOrderResultDto>>>() {
        });
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 根据大订单号批量查询大订单详情
     *
     * @param orderIds
     */
    @Override
    public List<HbmsOrderDetailDto> batchQueryMasterOrderDetail(List<Integer> orderIds) {
        ResponseVo<List<HbmsOrderDetailDto>> responseVo = serviceCaller.post("aladdin-order.masterOrder-app.batchQueryMasterOrderDetail", Maps.newHashMap("orderNums", orderIds), new TypeReference<ResponseVo<List<HbmsOrderDetailDto>>>() {
        });
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 查询大订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public AppMasterOrderDetailDto queryMasterOrderDetail(Integer orderId) {

        ResponseVo<AppMasterOrderDetailDto> responseVo = serviceCaller.post("aladdin-order.masterOrder-app.queryMasterOrderDetail", Maps.newHashMap("masterOrderNum", orderId), new TypeReference<ResponseVo<AppMasterOrderDetailDto>>() {
        });
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;
    }

    /**
     * 根据订单号批量查询方案草稿
     *
     * @param orderIds
     * @return
     */
    @Override
    public Map<Integer, OrderDraftDto> queryOrderDraftDtoByOrderIds(List<Integer> orderIds) {

        HttpBaseResponse<Map<Integer, OrderDraftDto>> responseVo = serviceCaller.post("wcm-web.orderdraft.queryOrderDraftByOrderIdList", Maps.newHashMap("orderIds", orderIds), new TypeReference<HttpBaseResponse<Map<Integer, OrderDraftDto>>>() {
        });
        return responseVo.getObj();
    }

    /**
     * 查询可升级权益
     * @param orderIds
     * @return
     */
    @Override
    public List<UpgradeInfoDto> batchUpgradeInfo(List<Integer> orderIds) {
        List<Map> orderIdMap= new ArrayList<>();
        if(CollectionUtils.isNotEmpty(orderIds)){
            for(Integer orderId:orderIds){
                orderIdMap.add(Maps.newHashMap("orderNum", orderId));
            }
        }
        ResponseVo<List<UpgradeInfoDto>> responseVo = serviceCaller.post("aladdin-order.order-rights.batchUpgradeInfo", orderIdMap, new TypeReference<ResponseVo<List<UpgradeInfoDto>>>() {
        });
        if (responseVo.isSuccess()) {
            return responseVo.getData();
        }
        return null;
    }
}
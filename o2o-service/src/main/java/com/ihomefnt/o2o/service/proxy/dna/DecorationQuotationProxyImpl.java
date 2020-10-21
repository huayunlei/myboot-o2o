package com.ihomefnt.o2o.service.proxy.dna;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.dna.dto.*;
import com.ihomefnt.o2o.intf.domain.dna.vo.request.QuotePriceRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.*;
import com.ihomefnt.o2o.intf.proxy.dna.DecorationQuotationProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author wanyunxin
 * @create 2019-11-18 11:18
 */
@Service
@Slf4j
public class DecorationQuotationProxyImpl implements DecorationQuotationProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    /**
     * 查询获取装修报价人数
     *
     * @return
     */
    @Override
    public Integer queryQuotePriceCount() {
        return ((HttpBaseResponse<Integer>) strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_QUOTE_PRICE_COUNT, new Object(), new TypeReference<HttpBaseResponse<Integer>>() {
        })).getObj();
    }


    /**
     * 增加获取装修报价人数
     *
     * @return
     */
    @Override
    public void addQuotePriceCount() {
        strongSercviceCaller.post(WcmWebServiceNameConstants.ADD_QUOTE_PRICE_COUNT, new Object(), new TypeReference<HttpBaseResponse<Integer>>() {
        });
    }

    /**
     * 获取DNA推荐列表
     *
     * @param request
     * @return
     */
    @Override
    public List<DnaPriceInfoDto> queryRecommendDnaList(QuotePriceRequest request) {
        return ((ResponseVo<List<DnaPriceInfoDto>>) strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_DNA_PRICE_INFO, new Object(),
                new TypeReference<ResponseVo<List<DnaPriceInfoDto>>>() {
                })).getData();
    }

    /**
     * 增加DNA浏览记录
     *
     * @param dnaBrowseRecordDto
     */
    @Override
    public void addDnaBrowseRecord(DnaBrowseRecordDto dnaBrowseRecordDto) {
        strongSercviceCaller.post(WcmWebServiceNameConstants.ADD_DNA_BROWSE_RECORD, dnaBrowseRecordDto, new TypeReference<HttpBaseResponse<Integer>>() {
        });
    }

    /**
     * 增加装修报价记录
     *
     * @param quotePriceRecord
     */
    @Override
    public Integer addQuotePriceRecord(QuotePriceRecordDto quotePriceRecord) {
        return ((HttpBaseResponse<Integer>) strongSercviceCaller.post(WcmWebServiceNameConstants.ADD_QUOTE_PRICE_RECORD, quotePriceRecord, new TypeReference<HttpBaseResponse<Integer>>() {
        })).getObj();
    }


    /**
     * 预约全品家-发送给小艾
     *
     * @param request
     * @return
     */
    @Override
    public void addOrderFamilyRecord(QuotePriceRequest request) {
        request.setUserId(request.getUserInfo().getId());
        try {
            strongSercviceCaller.post(AladdinCscServiceNameConstants.ADD_RESERVATION, request, ResponseVo.class);
        } catch (Exception e) {
            log.error("addOrderFamilyRecord error", e);
        }
    }

    /**
     * 获取装修历程
     *
     * @param request
     * @return
     */
    @Override
    public List<DecorationProcessDto> queryDecorationProcess(QuotePriceRequest request) {
        return ((HttpBaseResponse<List<DecorationProcessDto>>) strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_DECORATION_PROCESS, request,
                new TypeReference<HttpBaseResponse<List<DecorationProcessDto>>>() {
                })).getObj();
    }


    /**
     * 根据订单号查询订单各阶段时间
     *
     * @param request
     * @return
     */
    @Override
    public OrderTimeDto queryOrderTime(QuotePriceRequest request) {
        return ((ResponseVo<OrderTimeDto>) strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_MANY_TIME, request.getOrderNum(),
                new TypeReference<ResponseVo<OrderTimeDto>>() {
                })).getData();
    }

    /**
     * 查询订单时间
     *
     * @param request
     * @return
     */
    @Override
    public DeliveryTimeDto queryDeliveryTime(QuotePriceRequest request) {
        return ((ResponseVo<DeliveryTimeDto>) strongSercviceCaller.post(AladdinDmsServiceNameConstants.QUERY_ORDER_NODE_TIME, request.getOrderNum(),
                new TypeReference<ResponseVo<DeliveryTimeDto>>() {
                })).getData();
    }

}

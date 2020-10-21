package com.ihomefnt.o2o.intf.proxy.dna;

import com.ihomefnt.o2o.intf.domain.dna.dto.*;
import com.ihomefnt.o2o.intf.domain.dna.vo.request.QuotePriceRequest;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.QuotePriceResponse;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-11-18 11:38
 */
public interface DecorationQuotationProxy {

    /**
     * 查询报价记录总数
     * @return
     */
    Integer queryQuotePriceCount();

    /**
     * 增加报价记录
     * @return
     */
    void addQuotePriceCount();

    /**
     * 增加DNA浏览记录
     * @param dnaBrowseRecordDto
     */
    void addDnaBrowseRecord(DnaBrowseRecordDto dnaBrowseRecordDto);

    /**
     * 增加装修报价记录
     * @param quotePriceRecord
     */
    Integer addQuotePriceRecord(QuotePriceRecordDto quotePriceRecord);


    /**
     * 获取DNA推荐列表
     * @param request
     * @return
     */
    List<DnaPriceInfoDto> queryRecommendDnaList(QuotePriceRequest request);

    /**
     * 预约全品家-发送给小艾
     * @param request
     */
    void addOrderFamilyRecord(QuotePriceRequest request);

    /**
     * 获取装修历程
     * @param request
     * @return
     */
    List<DecorationProcessDto> queryDecorationProcess(QuotePriceRequest request);


    /**
     * 订单各阶段时间查询
     * @param request
     * @return
     */
     OrderTimeDto queryOrderTime(QuotePriceRequest request);

    /**
     * 交付时间查询
     * @param request
     * @return
     */
     DeliveryTimeDto queryDeliveryTime(QuotePriceRequest request);
}

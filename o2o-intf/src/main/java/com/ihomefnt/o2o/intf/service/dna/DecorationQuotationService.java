package com.ihomefnt.o2o.intf.service.dna;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.dna.vo.request.QuotePriceRequest;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.DecorationProcessResponse;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.QuotePriceInfoResponse;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.QuotePriceResponse;

/**
 * @author wanyunxin
 * @create 2019-11-18 11:09
 */
public interface DecorationQuotationService {


    /**
     * 装修报价信息查询
     * @param request
     * @return
     */
    QuotePriceInfoResponse queryQuotePriceInfo(HttpBaseRequest request);

    /**
     * 获取装修报价
     * @param request
     * @return
     */
    QuotePriceResponse queryQuotePrice(QuotePriceRequest request);

    /**
     * 获取装修历程
     * @param request
     * @return
     */
    DecorationProcessResponse queryDecorationProcess(QuotePriceRequest request);
}

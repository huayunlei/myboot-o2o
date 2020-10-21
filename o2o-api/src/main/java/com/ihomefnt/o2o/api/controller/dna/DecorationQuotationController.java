package com.ihomefnt.o2o.api.controller.dna;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.dna.vo.request.QuotePriceRequest;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.DecorationProcessResponse;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.QuotePriceInfoResponse;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.QuotePriceResponse;
import com.ihomefnt.o2o.intf.service.dna.DecorationQuotationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanyunxin
 * @create 2019-11-18 09:43
 */
@RestController
@RequestMapping("/decorationQuotation")
@Api(tags = "【装修报价API】")
public class DecorationQuotationController {

    @Autowired
    private DecorationQuotationService decorationQuotationService;

    @ApiOperation(value = "装修报价信息查询接口", notes = "装修报价信息查询接口")
    @PostMapping(value = "/queryQuotePriceInfo")
    public HttpBaseResponse<QuotePriceInfoResponse> queryQuotePriceInfo(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(decorationQuotationService.queryQuotePriceInfo(request));
    }

    @ApiOperation(value = "获取装修报价", notes = "获取装修报价接口")
    @PostMapping(value = "/queryQuotePrice")
    public HttpBaseResponse<QuotePriceResponse> queryQuotePrice(@RequestBody QuotePriceRequest request) {
        return HttpBaseResponse.success(decorationQuotationService.queryQuotePrice(request));
    }

    @ApiOperation(value = "获取装修历程", notes = "获取装修历程")
    @PostMapping(value = "/queryDecorationProcess")
    public HttpBaseResponse<DecorationProcessResponse> queryDecorationProcess(@RequestBody QuotePriceRequest request) {
        return HttpBaseResponse.success(decorationQuotationService.queryDecorationProcess(request));
    }
}

package com.ihomefnt.o2o.api.controller.optional;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.optional.vo.request.CreateCustomSkuRequestVo;
import com.ihomefnt.o2o.intf.domain.optional.vo.request.QuerySkuRequestVo;
import com.ihomefnt.o2o.intf.domain.optional.vo.response.CreateCustomSkuResponseVo;
import com.ihomefnt.o2o.intf.domain.optional.vo.response.CustoOptionalResponseVo;
import com.ihomefnt.o2o.intf.service.optional.CustomOptionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订制品支持选配
 *
 * @author liyonggang
 * @create 2018-11-22 19:34
 */
@Api(tags = "【新订制品选配API】")
@RestController
@RequestMapping("/customOptional")
public class CustomOptionalController {

    @Autowired
    private CustomOptionalService customOptionalService;

    /**
     * 根据skuId查询新订制品属性信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据skuId查询新订制品属性信息")
    @RequestMapping(value = "/queryCustomAttrs", method = RequestMethod.POST)
    public HttpBaseResponse<CustoOptionalResponseVo> queryCustomAttrs(@RequestBody QuerySkuRequestVo request) {
        if (request.getWidth() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误");
        }
        return HttpBaseResponse.success(customOptionalService.queryCustomAttrs(request.getWidth(), request.getSkuId()));
    }

    /**
     * 新建sku
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "创建新的sku")
    @RequestMapping(value = "/createCustomSku", method = RequestMethod.POST)
    public HttpBaseResponse<CreateCustomSkuResponseVo> createCustomSku(@RequestBody CreateCustomSkuRequestVo request) {
        return HttpBaseResponse.success(customOptionalService.createCustomSku(request));
    }
}

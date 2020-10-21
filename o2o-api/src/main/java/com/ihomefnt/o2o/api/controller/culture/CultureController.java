package com.ihomefnt.o2o.api.controller.culture;

import com.ihomefnt.common.util.validation.ValidationUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.CultureConsumeCodeRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.CultureDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.OrderCreateRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CreateOrderResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureCommodityResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureConsumeCodeResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.OrderConfirmResponseVo;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.culture.CultureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 文旅商品Controller
 *
 * @author Charl
 */
@ApiIgnore
@Api(tags = "文旅商品api", hidden = true)
@RestController
public class CultureController {
    @Autowired
    CultureService cultureService;

    /**
     * 获取文旅商品详情
     *
     * @return
     */
    @ApiOperation(value = "culture/detail", notes = "文旅商品详情")
    @RequestMapping(value = "/culture/detail", method = RequestMethod.POST)
    public HttpBaseResponse<CultureCommodityResponseVo> getCultureDetail(@Json CultureDetailRequestVo requestVo) {
        if (null == requestVo || null == requestVo.getItemId()) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CultureCommodityResponseVo obj = cultureService.getCultureDetail(requestVo);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 文旅商品确定订单页面接口
     *
     * @param requestVo
     * @return
     */
    @ApiOperation(value = "cculture/onfirmOrder", notes = "文旅商品确认订单页面接口")
    @RequestMapping(value = "/culture/confirmOrder", method = RequestMethod.POST)
    public HttpBaseResponse<OrderConfirmResponseVo> confirmCultureOrder(@Json CultureDetailRequestVo requestVo) {
        if (null == requestVo || null == requestVo.getItemId() || null == requestVo.getAccessToken()) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        OrderConfirmResponseVo obj = cultureService.confirmCultureOrder(requestVo);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 文旅商品下单接口
     *
     * @param requestVo
     * @return
     */
    @ApiOperation(value = "culture/createOrder", notes = "文旅商品创建订单接口")
    @RequestMapping(value = "/culture/createOrder", method = RequestMethod.POST)
    public HttpBaseResponse<CreateOrderResponseVo> createCultureOrder(@Json OrderCreateRequestVo requestVo) {
        if (null == requestVo || ValidationUtils.validateEntity(requestVo).isHasErrors() || null == requestVo.getProductList()
                || requestVo.getProductList().size() <= 0 || null == requestVo.getAccessToken()) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CreateOrderResponseVo obj = cultureService.createCultureOrder(requestVo);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 获取唯一码
     *
     * @param requestVo
     * @return
     */
    @ApiModelProperty(value = "cultureOrder/getGenerateCode", notes = "根据订单id获取唯一码")
    @RequestMapping(value = "/cultureOrder/getGenerateCode", method = RequestMethod.POST)
    public HttpBaseResponse<CultureConsumeCodeResponseVo> getGenerateCode(@Json CultureConsumeCodeRequestVo requestVo) {
        if (null == requestVo || null == requestVo.getAccessToken() || null == requestVo.getOrderId()) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CultureConsumeCodeResponseVo obj = cultureService.getGenerateCode(requestVo);
        return HttpBaseResponse.success(obj);
    }
}

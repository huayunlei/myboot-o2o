package com.ihomefnt.o2o.api.controller.cart;

import com.ihomefnt.o2o.intf.domain.cart.vo.request.AddShoppingCartRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.BatchGoodsRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.GoodsAmountRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.GoodsCountResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.GoodsListResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.HttpAjbInfoResponseVo;
import com.ihomefnt.o2o.intf.manager.util.common.secure.StringUtils;
import com.ihomefnt.o2o.intf.service.cart.ShoppingCartService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车Controller
 *
 * @author Charl
 */
@RestController
@Api(tags = "【购物车API】")
@RequestMapping("/userCart")
public class ShoppingCartController {
	
    @Autowired
    private ShoppingCartService userCartService;

    /**
     * 添加商品到购物车
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "添加商品到购物车(艺术品详情用户点击加入购物车)")
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    public HttpBaseResponse<Void> addToShoppingCart(@RequestBody AddShoppingCartRequestVo request) {
        if (null == request || null == request.getAccessToken() || request.getGoodsType() <= 0 || StringUtils.isEmpty(request.getGoodsId())) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        userCartService.addShoppingCart(request);
        return HttpBaseResponse.success();
    }

    /**
     * 获取购物车列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取购物车列表")
    @RequestMapping(value = "/getShoppingCartList", method = RequestMethod.POST)
    public HttpBaseResponse<GoodsListResponseVo> getShoppingCartList(@RequestBody HttpBaseRequest request) {
    	if (null == request || null == request.getAccessToken()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
    	GoodsListResponseVo obj = userCartService.goodsList(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 购物车中增加商品数量
     *
     * @param
     * @return HttpBaseResponse
     */
    @ApiOperation(value = "购物车中增加商品数量(+1)")
    @RequestMapping(value = "/incGoodsAmount", method = RequestMethod.POST)
    public HttpBaseResponse<Void> incGoodsAmount(@RequestBody GoodsAmountRequestVo request) {
    	if (null == request || null == request.getAccessToken() || null == request.getRecordId()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
        userCartService.incGoodsAmount(request);
        return HttpBaseResponse.success();
    }

    /**
     * 购物车中减少商品数量
     *
     * @param
     * @return HttpBaseResponse
     */
    @ApiOperation(value = "购物车中减少商品数量(-1)")
    @RequestMapping(value = "/decGoodsAmount", method = RequestMethod.POST)
    public HttpBaseResponse<Void> decGoodsAmount(@RequestBody GoodsAmountRequestVo request) {
    	if (null == request || null == request.getAccessToken() || null == request.getRecordId()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
        userCartService.decGoodsAmount(request);
        return HttpBaseResponse.success();
    }

    /**
     * 删除购物车中一条数据
     *
     * @param
     * @return HttpBaseResponse
     */
    @ApiOperation(value = "删除购物车中一条数据")
    @RequestMapping(value = "/removeGoods", method = RequestMethod.POST)
    public HttpBaseResponse<Void> removeGoods(@RequestBody GoodsAmountRequestVo request) {
    	if (null == request || null == request.getAccessToken() || null == request.getRecordId()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
        userCartService.removeGoods(request);
        return HttpBaseResponse.success();
    }

    /**
     * 批量删除购物车中数据
     *
     * @param
     * @return HttpBaseResponse
     */
    @ApiOperation(value = "批量删除购物车中数据")
    @RequestMapping(value = "/batchRemoveGoods", method = RequestMethod.POST)
    public HttpBaseResponse<Void> batchRemoveGoods(@RequestBody BatchGoodsRequestVo request) {
    	if (null == request || null == request.getAccessToken() || CollectionUtils.isEmpty(request.getRecordIds())) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
        userCartService.batchRemoveGoods(request);
        return HttpBaseResponse.success();
    }

    /**
     * 购物车中商品数量
     *
     * @param
     * @return HttpHttpBaseResponse
     */
    @ApiOperation(value = "购物车中商品数量")
    @RequestMapping(value = "/goodsAmount", method = RequestMethod.POST)
    public HttpBaseResponse<GoodsCountResponseVo> goodsAmount(@RequestBody HttpBaseRequest request) {
    	if (null == request || null == request.getAccessToken()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
    	GoodsCountResponseVo obj = userCartService.goodsAmount(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 获取用户艾积分信息
     *
     * @param
     * @return HttpBaseResponse<HttpAjbInfoResponse>
     */
    @ApiOperation(value = "当前用户艾积分信息")
    @RequestMapping(value = "/ajbInfo", method = RequestMethod.POST)
    public HttpBaseResponse<HttpAjbInfoResponseVo> ajbInfo(@RequestBody HttpBaseRequest request) {
    	if (null == request || null == request.getAccessToken()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
    	HttpAjbInfoResponseVo obj = userCartService.ajbInfo(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 购物车结算
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "购物车结算")
    @RequestMapping(value = "/settleAccounts", method = RequestMethod.POST)
    public HttpBaseResponse<Void> settleAccounts(@RequestBody BatchGoodsRequestVo request) {
    	if (null == request || null == request.getAccessToken() || CollectionUtils.isEmpty(request.getRecordIds())) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
    	
        userCartService.settleAccount(request);
        return HttpBaseResponse.success();
    }
}

package com.ihomefnt.o2o.api.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ihomefnt.o2o.intf.domain.user.vo.request.AjbInfoRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.MyAccountRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserAddWishListRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserFavoritesRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserOrderRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserWishListRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AccountInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.MyConfigResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserAddWishListResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserFavoritesAllResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserFavoritesResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserOrderListResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserWishListResponseVo;
import com.ihomefnt.o2o.intf.service.user.MyService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author huayunlei
 * @ClassName: MySetController
 * @Description: 我的设置管理controller
 * @date Feb 20, 2019 5:25:24 PM
 */
@RestController
@RequestMapping("/my")
@Api(tags = "【我的设置管理API】")
public class MyController {

    @Autowired
    private MyService myService;

    @ApiOperation(value = "favorites", notes = "favorites")
    @RequestMapping(value = "/favorites", method = RequestMethod.POST)
    public HttpBaseResponse<UserFavoritesResponseVo> getAllFavorites(@Json UserFavoritesRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserFavoritesResponseVo obj = myService.getAllFavorites(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "favorites200", notes = "favorites200")
    @RequestMapping(value = "/favorites200", method = RequestMethod.POST)
    public HttpBaseResponse<UserFavoritesAllResponseVo> getAllFavorites200(@Json UserFavoritesRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserFavoritesAllResponseVo obj = myService.getAllFavorites200(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "wishList", notes = "wishList")
    @RequestMapping(value = "/wishList", method = RequestMethod.POST)
    public HttpBaseResponse<UserWishListResponseVo> getAllWishList(@Json UserWishListRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserWishListResponseVo obj = myService.getAllWishList(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "wishList/add", notes = "addWishList")
    @RequestMapping(value = "/wishList/add", method = RequestMethod.POST)
    public HttpBaseResponse<UserAddWishListResponseVo> addWishList(@Json UserAddWishListRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserAddWishListResponseVo obj = myService.addWishList(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "order", notes = "order")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public HttpBaseResponse<UserOrderListResponseVo> getAllOrder(@Json UserOrderRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserOrderListResponseVo obj = myService.getAllOrder(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "configByOrderId", notes = "configByOrderId")
    @RequestMapping(value = "/configByOrderId", method = RequestMethod.POST)
    public HttpBaseResponse<MyConfigResponseVo> configByOrderId(@RequestBody UserOrderRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        MyConfigResponseVo obj = myService.configByOrderId(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "config", notes = "config")
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public HttpBaseResponse<MyConfigResponseVo> config(@Json HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        MyConfigResponseVo obj = myService.config(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "ajb", notes = "ajb")
    @RequestMapping(value = "/ajb", method = RequestMethod.POST)
    public HttpBaseResponse<AjbInfoResponseVo> getAjbInfo(@Json AjbInfoRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        AjbInfoResponseVo obj = myService.getAjbInfo(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "accountinfo", notes = "accountinfo")
    @RequestMapping(value = "/accountinfo", method = RequestMethod.POST)
    public HttpBaseResponse<AccountInfoResponseVo> getAccountInfo(@Json MyAccountRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        AccountInfoResponseVo obj = myService.getAccountInfo(request);
        return HttpBaseResponse.success(obj);
    }

}

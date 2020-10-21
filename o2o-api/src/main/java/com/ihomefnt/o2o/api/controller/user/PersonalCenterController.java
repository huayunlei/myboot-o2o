package com.ihomefnt.o2o.api.controller.user;

import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.right.vo.response.RightsAndOrderResponse;
import com.ihomefnt.o2o.intf.domain.user.vo.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.OrderDeliverAndOrderSaleType;
import com.ihomefnt.o2o.intf.manager.constant.RedisKey;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.user.PersonalCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 个人中心api
 *
 * @author liyonggang
 * @create 2019-02-20 10:29
 */
@Api(tags = "【个人中心API】")
@RestController
@RequestMapping("/personalCenter")
public class PersonalCenterController {
    @Autowired
    private PersonalCenterService personalCenterService;

    @Autowired
    private UserProxy userProxy;

    /**
     * 获取个人中心主体数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取个人中心主体数据", notes = "获取个人中心主体数据")
    @RequestMapping(value = "/queryPersonalCenter", method = RequestMethod.POST)
    public HttpBaseResponse<PersonalCenterVo> queryPersonalCenter(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(personalCenterService.queryPersonalCenter(request));
    }

    /**
     * 获取个人中心角标数据和用户信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取个人中心角标数据和用户信息", notes = "获取个人中心角标数据和用户信息")
    @RequestMapping(value = "/queryPersonalCenterCornerMark", method = RequestMethod.POST)
    public HttpBaseResponse<PersonalCenterCornerMarkVo> queryPersonalCenterCornerMark(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(personalCenterService.queryPersonalCenterCornerMark(request));
    }

    /**
     * 查询个人中心款项记录页面数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询个人中心款项记录页面数据", notes = "查询个人中心款项记录页面数据")
    @RequestMapping(value = "/queryFundRecordForPersonalCenter", method = RequestMethod.POST)
    public HttpBaseResponse<List<PersonalCenterMiddleInfoVo>> queryFundRecordForPersonalCenter(@RequestBody HttpBaseRequest request) {
        HttpUserInfoRequest userVo = this.checkParamsAndUser(request);
        return HttpBaseResponse.success(personalCenterService.queryFundRecordForPersonalCenter(userVo.getId()));
    }

    /**
     * 查询个人中心我的合同页面数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询个人中心我的合同页面数据", notes = "查询个人中心我的合同页面数据")
    @RequestMapping(value = "/queryUserContractForPersonalCenter", method = RequestMethod.POST)
    public HttpBaseResponse<List<PersonalCenterMiddleInfoVo>> queryUserContractForPersonalCenter(@RequestBody HttpBaseRequest request) {
        HttpUserInfoRequest userVo = this.checkParamsAndUser(request);
        return HttpBaseResponse.success(personalCenterService.queryUserContractForPersonalCenter(userVo.getId()));
    }

    /**
     * 查询个人中心我的方案列表页数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询个人中心我的方案列表页数据", notes = "查询个人中心我的方案列表页数据")
    @RequestMapping(value = "/queryUserProgramListForPersonalCenter", method = RequestMethod.POST)
    public HttpBaseResponse<List<PersonalCenterMiddleInfoVo>> queryUserProgramListForPersonalCenter(@RequestBody HttpBaseRequest request) {
        HttpUserInfoRequest userVo = this.checkParamsAndUser(request);
        return HttpBaseResponse.success(personalCenterService.queryUserProgramListForPersonalCenter(userVo.getId()));
    }

    /**
     * 查询个人中心我的方案详情
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询个人中心我的方案详情", notes = "查询个人中心我的方案详情")
    @RequestMapping(value = "/queryUserProgramForPersonalCenter", method = RequestMethod.POST)
    public HttpBaseResponse<UserProgramForPersonalCenterVo> queryUserProgramForPersonalCenter(@RequestBody HttpOrderRequest request) {
        if (request == null || request.getOrderId() == null || request.getWidth() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(personalCenterService.queryUserProgramForPersonalCenter(request.getOrderId().intValue(), user.getId(), request.getOsType(), request.getWidth()));
    }


    /**
     * 查询个人中心我的权益页面数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询个人中心我的权益页面数据", notes = "查询个人中心我的权益页面数据")
    @RequestMapping(value = "/queryUserRightsForPersonalCenter", method = RequestMethod.POST)
    public HttpBaseResponse<List<PersonalCenterMiddleInfoVo>> queryUserRightsForPersonalCenter(@RequestBody HttpBaseRequest request) {
        HttpUserInfoRequest userVo = this.checkParamsAndUser(request);
        return HttpBaseResponse.success(personalCenterService.queryUserRightsForPersonalCenter(userVo.getId(), request.getWidth(),request.getAppVersion()));
    }

    /**
     * 查询用户订单号集合
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询用户订单房产的订单号", notes = "查询用户订单房产订单号")
    @RequestMapping(value = "/queryUserOrderIdList", method = RequestMethod.POST)
    public HttpBaseResponse<List<OrderIdResponse>> queryUserOrderIdList(@RequestBody HttpBaseRequest request) {
        HttpUserInfoRequest userVo = checkParamsAndUser(request);
        return HttpBaseResponse.success(personalCenterService.queryUserOrderIdList(userVo.getId()));
    }

    private HttpUserInfoRequest checkParamsAndUser(HttpBaseRequest request) {
        if (request == null) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest userByToken = request.getUserInfo();
        if (userByToken == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return userByToken;
    }

    @RequestMapping(value = "/rmRedisByKey", method = RequestMethod.POST)
    public HttpBaseResponse<Long> rmRedisByKey(@RequestHeader("key") String redisKey) {
        return HttpBaseResponse.success(AppRedisUtil.del(redisKey));
    }

    @RequestMapping(value = "/cleanPersonalCenterData", method = RequestMethod.POST)
    public HttpBaseResponse<Long> cleanPersonalCenterData() {
        Long num = 0L;
        num += AppRedisUtil.del(RedisKey.PersonalCenter.PERSONAL_CENTER_RESOURCE_GROUPBY_KEY);
        num += AppRedisUtil.del(RedisKey.PersonalCenter.PERSONAL_CENTER_RESOURCE_GROUPBY_CATEGORY);
        num += AppRedisUtil.del(RedisKey.PersonalCenter.DEFAULT_AVATAR_IMAGE);
        num += AppRedisUtil.del(RedisKey.PersonalCenter.ADVISER_MOBILES);
        Set<String> keys = AppRedisUtil.keys(RedisKey.PersonalCenter.PERSONAL_CENTER_DATA_NO_LOGIN + "*");
        if (CollectionUtils.isNotEmpty(keys)) {
            for (String key : keys) {
                num += AppRedisUtil.del(key);
            }
        }
        return HttpBaseResponse.success(num);
    }

    /**
     * 根据订单号查权益等级
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据订单号查权益等级", notes = "根据订单号查权益等级")
    @RequestMapping(value = "/queryRightsByOrderId", method = RequestMethod.POST)
    @Deprecated
    public HttpBaseResponse<Integer> queryRightsByOrderId(@RequestBody QueryDraftRequest request) {
        return HttpBaseResponse.success(personalCenterService.queryRightsByOrderId(request.getOrderId()));
    }

    /**
     * 根据订单号查权益等级和金额
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据订单号查权益等级和金额", notes = "根据订单号查权益等级和金额")
    @RequestMapping(value = "/queryRightsInfoByOrderId", method = RequestMethod.POST)
    public HttpBaseResponse<RightsAndOrderResponse> queryRightsInfoByOrderId(@RequestBody QueryDraftRequest request) {
        if (request == null || request.getOrderId() == null) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(personalCenterService.queryRightsInfoByOrderId(request.getOrderId()));
    }

    /**
     * 查询已签约过的订单信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询已签约过的订单信息", notes = "查询已签约过的订单信息")
    @RequestMapping(value = "/queryOrderInfoByOrderId", method = RequestMethod.POST)
    public HttpBaseResponse<AppOrderBaseInfoResponseVo> queryOrderInfoByOrderId(@RequestBody QueryCollageOrderDetailRequest request) {
        return HttpBaseResponse.success(personalCenterService.queryOrderInfoByOrderId(request.getOrderId()));
    }


    /**
     * 查询订单信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询订单信息", notes = "查询订单信息")
    @RequestMapping(value = "/queryAppOrderBaseInfo", method = RequestMethod.POST)
    public HttpBaseResponse<AppOrderBaseInfoResponseVo> queryAppOrderBaseInfo(@RequestBody QueryCollageOrderDetailRequest request) {
        return HttpBaseResponse.success(personalCenterService.queryAppOrderBaseInfo(request.getOrderId()));
    }

    /**
     * 查询个人中心我的工地数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询个人中心我的工地数据", notes = "查询个人中心我的工地数据")
    @RequestMapping(value = "/queryMyWorkSiteForPersonalCenter", method = RequestMethod.POST)
    public HttpBaseResponse<List<PersonalCenterMiddleInfoVo>> queryMyWorkSiteForPersonalCenter(@RequestBody HttpBaseRequest request) {
        HttpUserInfoRequest userVo = this.checkParamsAndUser(request);
        return HttpBaseResponse.success(personalCenterService.queryMyWorkSiteForPersonalCenter(userVo.getId()));
    }

    /**
     * 查询个人中心我的工地数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询订单售卖类型和交付信息", notes = "查询个人中心我的工地数据")
    @RequestMapping(value = "/queryOrderDeliverAndOrderSaleType", method = RequestMethod.POST)
    public HttpBaseResponse<OrderDeliverAndOrderSaleType> queryOrderDeliverAndOrderSaleType(@RequestBody QueryCollageOrderDetailRequest request) {
        if (StringUtils.isBlank(request.getAccessToken())||request.getOrderId()==null){
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN,MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(personalCenterService.queryOrderDeliverAndOrderSaleType(request.getOrderId()));
    }
}

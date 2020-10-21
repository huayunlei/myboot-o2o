package com.ihomefnt.o2o.api.controller.right;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.right.dto.OrderSingleClassifyDto;
import com.ihomefnt.o2o.intf.domain.right.vo.request.*;
import com.ihomefnt.o2o.intf.domain.right.vo.response.*;
import com.ihomefnt.o2o.intf.proxy.right.RightProxy;
import com.ihomefnt.o2o.intf.service.right.QueryMyOrderRightService;
import com.ihomefnt.o2o.intf.service.right.RightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author jerfan cang
 * @date 2018/9/6 0:06
 */
@RestController
@RequestMapping(value = "/right")
@Api(tags="【订单权益API】")
public class RightController {

    @Autowired
    QueryMyOrderRightService queryMyOrderRightService;

    @Autowired
    RightProxy proxy;

    @Autowired
    RightService rightService;


    @ApiOperation(value = "根据订单查询权益资质", notes = "根据订单查询权益资质")
    @RequestMapping(value = "/license",method = RequestMethod.POST)
    public HttpBaseResponse<CheckOrderRightsResponseVo> queryOrderRightsLicense(@RequestBody OrderRightsLicenseRequestVo req){
        if(null == req || null ==req.getOrderNum()){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CheckOrderRightsResponseVo obj = rightService.queryOrderRightsLicense(req.getOrderNum());
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "查询订单权益(活动)详情", notes = "查询订单权益(活动)详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    public HttpBaseResponse<RightResponse> queryOrderRightsDetail(@RequestBody OrderRightsDetailRequestVo req, HttpServletRequest request){
        if(null == req){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        RightResponse rightResponse = rightService.queryOrderRightsDetail(req, request.getServerName());
        return HttpBaseResponse.success(rightResponse);
    }

    @ApiOperation(value = "查询特定等级权益详情", notes = "查询特定等级权益详情")
    @RequestMapping(value = "/queryGradeClassifyInfo",method = RequestMethod.POST)
    public HttpBaseResponse<RightDetailResponse> queryGradeClassifyInfo(@RequestBody ClassificationReq req , HttpServletRequest request){
        if(null == req || null==req.getGradeId()){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        RightDetailResponse rightDetailResponse = rightService.queryGradeClassifyInfo(req, request.getServerName());
        return HttpBaseResponse.success(rightDetailResponse);
    }

    @ApiOperation(value = "查询我的订单权益", notes = "查询我的订单权益")
    @RequestMapping(value = "/mine",method = RequestMethod.POST)
    public HttpBaseResponse<OrderRightsResultVo> queryMyOrderRights(@RequestBody QueryMyOrderRightItemListRequest req){
        if(null == req || null ==req.getOrderNum()  || req.getOrderNum() <= 0){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        OrderRightsResultVo resultVo = queryMyOrderRightService.queryMyOrderRightItemList(req);
        return HttpBaseResponse.success(resultVo);
    }


    /**
     *  20180928 权益迭代三需求
     * @param req 请求参数
     * @return ResponseEntity
     */
    @ApiOperation(value = "查询我的订单权益中单个分类的权益详情", notes = "查询我的订单权益中单个分类的权益详情")
    @RequestMapping(value = "/mine/single/classify",method = RequestMethod.POST)
    public HttpBaseResponse<OrderSingleClassifyDto> queryOrderRightsMineSingleClassify(@RequestBody MyOrderRightByClassifyRequest req){
        if(null == req || null ==req.getOrderNum() || null == req.getClassifyNo()){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        OrderSingleClassifyDto obj = rightService.queryOrderRightSingleClassify(req);
        return HttpBaseResponse.success(obj);
    }


    @ApiOperation(value = "用户订单确权", notes = "用户订单确权")
    @RequestMapping(value = "/classify/confirm",method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> classifyRightConfirm(@RequestBody ConfirmRightRequest req){
        if(null == req || null ==req.getOrderNum() || null == req.getClassifyNo()
            || null == req.getItemIds() || req.getItemIds().size() <=0){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        Boolean obj = rightService.classifyRightConfirm(req);
        if (!obj){
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(obj);
    }
    
    @ApiOperation(value = "判断订单权益弹窗", notes = "判断订单权益弹窗")
    @RequestMapping(value = "/judgeOrderRightPopup",method = RequestMethod.POST)
    public HttpBaseResponse<OrderRightPopupResponse> judgeOrderRightPopup(@RequestBody OrderRightPopupRequest req){
        if(null == req){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

    	OrderRightPopupResponse obj = rightService.judgeOrderRightPopup(req);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "权益宣传页", notes = "权益宣传页")
    @RequestMapping(value = "/queryPublicityInfo",method = RequestMethod.POST)
    public HttpBaseResponse<RightInfoResponse> queryPublicityInfo(@RequestBody QueryMyOrderRightItemListRequest req){
        if(null == req){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        return HttpBaseResponse.success(rightService.queryPublicityInfo(req));
    }


    @ApiOperation(value = "我的权益页面（新）", notes = "我的权益页面（新）")
    @RequestMapping(value = "/queryMineRights",method = RequestMethod.POST)
    public HttpBaseResponse<OrderRightsResponse> queryMineRights(@RequestBody QueryMyOrderRightItemListRequest req){
        if(null == req || null ==req.getOrderNum()  || req.getOrderNum() <= 0){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        req.setVersion(2);
        return HttpBaseResponse.success(rightService.queryMineRights(req));
    }


    @ApiOperation(value = "更新金额显示状态", notes = "更新金额显示状态")
    @RequestMapping(value = "/updateMoneyHideStatus",method = RequestMethod.POST)
    public HttpBaseResponse<String> updateMoneyHideStatus(@RequestBody QueryMyOrderRightItemListRequest req){
        if(null == req || null ==req.getOrderNum()  || req.getOrderNum() <= 0){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        rightService.updateMoneyHideStatus(req);
        return HttpBaseResponse.success(MessageConstant.SUCCESS);
    }


}

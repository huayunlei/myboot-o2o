package com.ihomefnt.o2o.api.controller.delivery;

import com.ihomefnt.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.ConfirmNodeRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.FinalCheckParamRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.GetNodeDetailRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.GetHardScheduleVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.HardDetailVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.NodeDetailVo;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.delivery.DeliveryService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.comment.vo.request.DelivertCommentRequest;
import com.ihomefnt.o2o.intf.manager.util.common.http.HttpRequestParamHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 硬装进度改版
 */
@Api(tags = "【交付相关】")
@Slf4j
@Controller
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    UserProxy userProxy;

    @Autowired
    private HttpRequestParamHandler requestParamHandler;

    @ApiOperation(value = "硬装进度接口", notes = "硬装进度接口")
    @PostMapping(value = "/getHardDetail")
    public HttpBaseResponse<HardDetailVo> getHardDetail(@RequestBody @Valid DelivertCommentRequest request) {
        if (request == null || request.getUserInfo() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(deliveryService.getHardDetail(request.getOrderId(), request.getAppVersion()));
    }

    /**
     * @deprecated 已废弃
     * @param request
     * @return
     */
    @ApiOperation(value = "节点详细信息(已废弃)", notes = "节点详细信息(已废弃)")
    @PostMapping(value = "/getNodeDetail")
    @Deprecated
    public HttpBaseResponse<NodeDetailVo> getNodeDetail(@RequestBody @Valid GetNodeDetailRequest request) {
        requestParamHandler.verificationLogin(request.getAccessToken(), Boolean.TRUE);
        return HttpBaseResponse.success(deliveryService.getNodeDetail(request));
    }

    @ApiOperation(value = "客户节点验收接口", notes = "客户节点验收接口")
    @PostMapping(value = "/confirmNode")
    public HttpBaseResponse confirmNode(@RequestBody ConfirmNodeRequest request) {
        if (VersionUtil.mustUpdate(request.getAppVersion(), "5.4.5")) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, "请更新APP后再继续操作！");
        }
        requestParamHandler.verificationLogin(request.getAccessToken(), Boolean.TRUE);
        deliveryService.confirmNode(request);
        return HttpBaseResponse.success("成功");
    }

    @ApiOperation(value = "完整计划日历接口", notes = "完整计划日历接口")
    @PostMapping(value = "/getHardSchedule")
    public HttpBaseResponse<GetHardScheduleVo> getHardSchedule(@RequestBody DelivertCommentRequest request) {
        requestParamHandler.verificationLogin(request.getAccessToken(), Boolean.TRUE);
        return HttpBaseResponse.success(deliveryService.getHardSchedule(request.getOrderId()));
    }

    @ApiOperation(value = "整体验收接口", notes = "最终验收接口")
    @PostMapping(value = "/finalCheck")
    public HttpBaseResponse finalCheck(@RequestBody FinalCheckParamRequest request) {
        requestParamHandler.verificationLogin(request.getAccessToken(), Boolean.TRUE);
        deliveryService.finalCheck(request);
        if(0 == request.getConfirm()){
            return HttpBaseResponse.success("已收到你的反馈问题，会尽快进行核实处理。");
        }else{
            return HttpBaseResponse.success("验收成功");
        }
    }

    @ApiOperation(value = "节点详细信息V2", notes = "节点详细信息V2")
    @PostMapping("/getNodeDetailV2")
    public HttpBaseResponse<NodeDetailVo> getNodeDetailV2(@RequestBody @Valid GetNodeDetailRequest request) {
        return HttpBaseResponse.success(deliveryService.getNodeDetailV2(request));
    }
}

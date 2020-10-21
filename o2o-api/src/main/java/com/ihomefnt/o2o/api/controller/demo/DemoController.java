package com.ihomefnt.o2o.api.controller.demo;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.demo.vo.request.CompleteDesignDemandRequest;
import com.ihomefnt.o2o.intf.domain.demo.vo.request.DemoCommonRequest;
import com.ihomefnt.o2o.intf.domain.demo.vo.response.DemoCommonResponse;
import com.ihomefnt.o2o.intf.domain.dms.vo.DemoDeliveryRequestVo;
import com.ihomefnt.o2o.intf.service.demo.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "演示系统")
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoService demoService;

    @ApiOperation(value = "查询演示按钮", notes = "查询演示按钮")
    @PostMapping(value = "/queryButtonList")
    public HttpBaseResponse<DemoCommonResponse> queryButtonList(@RequestBody DemoCommonRequest request) {
        if(request == null || request.getAccessToken() == null || request.getOrderId() == null || request.getMobileNum() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }

        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        return HttpBaseResponse.success(demoService.queryDemoButtonInfo(request));
    }

    @ApiOperation(value = "演示按钮-交定金", notes = "演示按钮-交定金(orderId、mobileNum)")
    @PostMapping(value = "/payEarnest")
    public HttpBaseResponse<String> payEarnest(@RequestBody DemoCommonRequest request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }

        return HttpBaseResponse.success(demoService.payEarnest(request));
    }

    @ApiOperation(value = "演示按钮-完成设计任务", notes = "演示按钮-完成设计任务(taskId，orderId、mobileNum)")
    @PostMapping(value = "/completeDesignDemand")
    public HttpBaseResponse<String> completeDesignDemand(@RequestBody DemoCommonRequest request) {
        if(request == null || request.getTaskId() == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        return HttpBaseResponse.success(demoService.completeDesignDemand(request));
    }

    @ApiOperation(value = "演示按钮-指定方案编号完成设计任务", notes = "演示按钮-指定方案编号完成设计任务(taskId、solutionId，orderId、mobileNum)")
    @PostMapping(value = "/completeDesignDemandBySolutionId")
    public HttpBaseResponse<String> completeDesignDemandBySolutionId(@RequestBody CompleteDesignDemandRequest request) {
        if(request == null || request.getTaskId() == null || request.getOrderId() == null || request.getSolutionId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        return HttpBaseResponse.success(demoService.completeDesignDemandBySolutionId(request));
    }

    @ApiOperation(value = "演示按钮-交合同款", notes = "演示按钮-交合同款(orderId、mobileNum)")
    @PostMapping(value = "/payContract")
    public HttpBaseResponse<String> payContract(@RequestBody DemoCommonRequest request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        return HttpBaseResponse.success(demoService.payContract(request));
    }

    @ApiOperation(value = "演示按钮-修改交房日期", notes = "演示按钮-修改交房日期(orderId、mobileNum)")
    @PostMapping(value = "/updateDeliverTime")
    public HttpBaseResponse<String> updateDeliverTime(@RequestBody DemoCommonRequest request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        return HttpBaseResponse.success(demoService.updateDeliverTime(request));
    }

    @ApiOperation(value = "演示按钮-默认", notes = "演示按钮-默认")
    @PostMapping(value = "/demoDefault")
    public HttpBaseResponse<String> demoDefault(@RequestBody DemoCommonRequest request) {
        return HttpBaseResponse.success("正在加紧建设中");
    }

    @ApiOperation(value = "演示按钮-一键排期（全品家）", notes = "演示按钮-一键排期（全品家）(orderId、mobileNum)")
    @PostMapping(value = "/scheduleDate")
    public HttpBaseResponse<String> scheduleDate(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.scheduleDate(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键排期（全品家软）", notes = "演示按钮-一键排期（全品家软）(orderId、mobileNum)")
    @PostMapping(value = "/softScheduleDate")
    public HttpBaseResponse<String> softScheduleDate(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.softScheduleDate(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键完成开工交底", notes = "演示按钮-一键完成开工交底(orderId、mobileNum)")
    @PostMapping(value = "/startSchedule")
    public HttpBaseResponse<String> startSchedule(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.startSchedule(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键完成水电", notes = "演示按钮-一键完成水电(orderId、mobileNum)")
    @PostMapping(value = "/finishHydropower")
    public HttpBaseResponse<String> finishHydropower(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishHydropower(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键完成瓦木", notes = "演示按钮-一键完成瓦木(orderId、mobileNum)")
    @PostMapping(value = "/finishWooden")
    public HttpBaseResponse<String> finishWooden(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishWooden(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键完成硬装竣工", notes = "演示按钮-一键完成硬装竣工(orderId、mobileNum)")
    @PostMapping(value = "/finishHard")
    public HttpBaseResponse<String> finishHard(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishHard(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键采购完成", notes = "演示按钮-一键采购完成(orderId、mobileNum)")
    @PostMapping(value = "/finishPurchase")
    public HttpBaseResponse<String> finishPurchase(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishPurchase(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键配送完成", notes = "演示按钮-一键配送完成(orderId、mobileNum)")
    @PostMapping(value = "/finishLogistic")
    public HttpBaseResponse<String> finishLogistic(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishLogistic(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键安装完成", notes = "演示按钮-一键安装完成(orderId、mobileNum)")
    @PostMapping(value = "/finishInstall")
    public HttpBaseResponse<String> finishInstall(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishInstall(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键软装验收", notes = "演示按钮-一键软装验收(orderId、mobileNum)")
    @PostMapping(value = "/finishSoftCheck")
    public HttpBaseResponse<String> finishSoftCheck(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishSoftCheck(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键艾师傅验收", notes = "演示按钮-一键艾师傅验收(orderId、mobileNum)")
    @PostMapping(value = "/finishCheck")
    public HttpBaseResponse<String> finishCheck(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishCheck(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-一键快速验收", notes = "演示按钮-一键快速验收(orderId、mobileNum)")
    @PostMapping(value = "/finishFastCheck")
    public HttpBaseResponse<String> finishFastCheck(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "success".equals(demoService.finishFastCheck(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }

    @ApiOperation(value = "演示按钮-取消订单", notes = "演示按钮-取消订单(orderId、mobileNum)")
    @PostMapping(value = "/cancelOrder")
    public HttpBaseResponse<String> cancelOrder(@RequestBody DemoDeliveryRequestVo request) {
        if(request == null || request.getOrderId() == null){
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if(Boolean.FALSE.equals(demoService.judgeCanDemo(request.getMobileNum()))){
            return HttpBaseResponse.fail(HttpReturnCode.NO_PERMISSION, MessageConstant.NO_PERMISSION);
        }
        if(Boolean.FALSE.equals(demoService.judgeBuildingIsWhite(request.getOrderId()))){
            return HttpBaseResponse.fail(HttpReturnCode.BUILDING_NO_PERMISSION, MessageConstant.BUILDING_NO_PERMISSION);
        }
        String returnMsg = "成功".equals(demoService.cancelOrder(request)) ?
                MessageConstant.OP_SUCCESS : MessageConstant.OP_FAILED;
        return HttpBaseResponse.success(returnMsg);
    }
}

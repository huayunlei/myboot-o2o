package com.ihomefnt.o2o.api.controller.designdemand;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.designdemand.request.DesignDemandToolQueryRequest;
import com.ihomefnt.o2o.intf.domain.designdemand.request.QueryDesignDemandInfoRequest;
import com.ihomefnt.o2o.intf.domain.designdemand.response.DesignDemandToolOrderListResponse;
import com.ihomefnt.o2o.intf.domain.designdemand.response.QueryDesignDemandInfoResponse;
import com.ihomefnt.o2o.intf.domain.designdemand.response.SimpleDataForBetaAppResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.CommitDesignRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.PersonalDesignResponse;
import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.service.designDemand.DesignDemandToolService;
import com.ihomefnt.o2o.intf.service.sms.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Api(tags = "【小艾提交设计任务API】")
@RestController
@RequestMapping("/designDemand")
public class DesignDemandToolController {

    @Autowired
    DesignDemandToolService designDemandToolService;

    @Autowired
    private SmsService smsService;

    @ApiOperation("订单房产列表查询")
    @PostMapping("/queryUserHouseListForDesignDemandTool")
    public HttpBaseResponse<DesignDemandToolOrderListResponse> queryUserHouseListForDesignDemandTool(@RequestBody DesignDemandToolQueryRequest request) {
        if (request.getUserId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(designDemandToolService.queryUserHouseListForDesignDemandTool(request.getUserId()));
    }

    @ApiOperation("暂存设计任务")
    @PostMapping("/addOrUpdateDesignDraft")
    public HttpBaseResponse<Map<String, String>> addOrUpdateDesignDraft(@RequestBody CommitDesignRequest commitDesignRequest) {
        return HttpBaseResponse.success(Maps.newHashMap("designDemandId", designDemandToolService.addOrUpdateDesignDraft(commitDesignRequest) + ""));
    }

    @ApiOperation("查询用户提交记录")
    @PostMapping("/queryCommentRecord")
    public HttpBaseResponse<List<PersonalDesignResponse>> queryCommentRecord(@RequestBody DesignDemandToolQueryRequest request) {
        if (request.getUserId() == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(designDemandToolService.queryCommentRecord(request));
    }

    @ApiOperation("查询设计任务详情")
    @PostMapping("/queryDesignDemandInfo")
    public HttpBaseResponse<QueryDesignDemandInfoResponse> queryDesignDemandInfo(@RequestBody QueryDesignDemandInfoRequest request) {
        if (request == null || ((request.getCommitRecordId() == null || request.getOrderId() == null)
                && request.getDesignDemandId() == null)) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(designDemandToolService.queryDesignDemandInfo(request));
    }

    @ApiOperation("发送给用户确认，变更状态")
    @PostMapping("/sendToUserAffirm")
    public HttpBaseResponse<DesignDemandToolQueryRequest> sendToUserAffirm(@RequestBody CommitDesignRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getDnaRoomList())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        return HttpBaseResponse.success(designDemandToolService.sendToUserAffirm(request));
    }

    @ApiOperation("用户确认设计任务")
    @PostMapping("/affirmDesignDemand")
    public HttpBaseResponse<DesignDemandToolQueryRequest> affirmDesignDemand(@RequestBody DesignDemandToolQueryRequest request) {
        if (request.getDesignDemandId() == null || StringUtils.isBlank(request.getMobile()) || StringUtils.isBlank(request.getAuthCode())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if (!smsService.checkSmsCode(new CheckSmsCodeParamVo().setMobile(request.getMobile()).setSmsCode(request.getAuthCode()).setType(3))) {
            return HttpBaseResponse.fail(HttpReturnCode.FAILED_VERIFY_FOR_MSG_CODE, MessageConstant.CODE_ERROR);
        }
        return HttpBaseResponse.success(designDemandToolService.affirmDesignDemand(request));
    }

    @ApiOperation("小艾确认设计任务")
    @PostMapping("/affirmDesignDemandByXa")
    public HttpBaseResponse<DesignDemandToolQueryRequest> affirmDesignDemandByXa(@RequestBody DesignDemandToolQueryRequest request) {
        if (request.getDesignDemandId() == null || StringUtils.isBlank(request.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(designDemandToolService.affirmDesignDemandByXa(request));
    }

    @ApiOperation("查询订单最新的方案意见和设计任务")
    @PostMapping("/queryProgramOpAndDesignDemandByOrderId")
    public HttpBaseResponse<SimpleDataForBetaAppResponse> queryProgramOpAndDesignDemandByOrderId(@RequestBody DesignDemandToolQueryRequest request) {
        if (request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(designDemandToolService.queryProgramOpAndDesignDemandByOrderId(request));
    }
}

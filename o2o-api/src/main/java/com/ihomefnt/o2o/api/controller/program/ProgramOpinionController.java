package com.ihomefnt.o2o.api.controller.program;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.program.dto.ProgramOpinionDetailDto;
import com.ihomefnt.o2o.intf.domain.program.vo.request.ProgramOpinionPageQueryReq;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionDetailResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserOrderProgramListResponse;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionAffirmByOperationListReq;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionRequest;
import com.ihomefnt.o2o.intf.service.program.ProgramOpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 方案修改意见controller
 *
 * @author liyonggang
 * @create 2019-08-08 15:38
 */
@Api(tags = "【方案修改意见API】")
@RestController
@RequestMapping("/programOpinion")
public class ProgramOpinionController {

    @Autowired
    private ProgramOpinionService programOpinionService;

    @ApiOperation(value = "订单方案列表", notes = "订单方案列表")
    @PostMapping("/queryUserOrderProgramList")
    public HttpBaseResponse<UserOrderProgramListResponse> queryUserProgramOpinionOrderList(@RequestBody @Valid ProgramOpinionRequest request) {
        return HttpBaseResponse.success(programOpinionService.queryUserProgramOpinionOrderList(request.getUserId()));
    }

    @ApiOperation(value = "方案意见列表", notes = "方案意见列表")
    @PostMapping("/queryProgramOpinionList")
    public HttpBaseResponse<ProgramOpinionResponse> queryProgramOpinionList(@RequestBody ProgramOpinionRequest request) {
        if (request.getSolutionId() == null || request.getUserId() == null || request.getOrderNum() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(programOpinionService.queryProgramOpinionList(request));
    }

    @ApiOperation(value = "新增或修改意见", notes = "新增或修改意见")
    @PostMapping("/addOrUpdateProgramOpinionDraft")
    public HttpBaseResponse<ProgramOpinionRequest> addOrUpdateProgramOpinionDraft(@RequestBody ProgramOpinionDetailDto request) {
        return HttpBaseResponse.success(new ProgramOpinionRequest().setProgramOpinionId(programOpinionService.addOrUpdateProgramOpinionDraft(request)));
    }

    @ApiOperation(value = "方案修改意见详情", notes = "方案修改意见详情")
    @PostMapping("/queryProgramOpinionDetail")
    public HttpBaseResponse<ProgramOpinionDetailDto> queryProgramOpinionDetail(@RequestBody ProgramOpinionRequest request) {
        return HttpBaseResponse.success(programOpinionService.queryProgramOpinionDetail(request));
    }

    @ApiOperation(value = "提交方案意见", notes = "提交方案意见")
    @PostMapping("/submitProgramOpinion")
    public HttpBaseResponse submitProgramOpinion(@RequestBody ProgramOpinionDetailDto request) {
        programOpinionService.submitProgramOpinion(request);
        return HttpBaseResponse.success();
    }

    @ApiOperation(value = "用户确认", notes = "用户确认")
    @PostMapping("/affirmProgramOpinion")
    public HttpBaseResponse<ProgramOpinionRequest> affirmProgramOpinion(@RequestBody ProgramOpinionRequest request) {
        if (request.getProgramOpinionId() == null|| StringUtils.isBlank(request.getMobile())||StringUtils.isBlank(request.getAuthCode())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        programOpinionService.affirmProgramOpinion(request);
        return HttpBaseResponse.success();
    }

    @ApiOperation(value = "运营确认方案意见", notes = "运营人员批量确认方案")
    @PostMapping("/affirmProgramOpinionByOperation")
    public HttpBaseResponse<ProgramOpinionRequest> affirmProgramOpinionByOperation(@RequestBody ProgramOpinionAffirmByOperationListReq request) {
        if (null == request || CollectionUtils.isEmpty(request.getItems())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if (request.getItems().size() > 100) {
            return HttpBaseResponse.fail(HttpReturnCode.RECORD_LIMIT, MessageConstant.RECORD_LIMIT_100);
        }

        programOpinionService.affirmProgramOpinionByOperation(request);
        return HttpBaseResponse.success();
    }

    @ApiOperation(value = "方案意见分页查询", notes = "方案意见分页查询")
    @PostMapping("/queryProgramOpinionListPage")
    public HttpBasePageResponse<ProgramOpinionDetailResponse> queryProgramOpinionListPage(@RequestBody ProgramOpinionPageQueryReq request) {
        return programOpinionService.queryProgramOpinionListPage(request);
    }


}

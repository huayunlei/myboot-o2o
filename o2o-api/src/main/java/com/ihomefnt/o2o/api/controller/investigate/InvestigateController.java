package com.ihomefnt.o2o.api.controller.investigate;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.investigate.vo.request.InvestigateCommitRequest;
import com.ihomefnt.o2o.intf.domain.investigate.vo.request.InvestigateQueryRequest;
import com.ihomefnt.o2o.intf.domain.investigate.vo.response.InvestigateQueryResponse;
import com.ihomefnt.o2o.intf.service.investigate.InvestigateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/4 9:52 上午
 */
@RestController
@RequestMapping("/investigate")
@Api(value = "问卷调研API")
public class InvestigateController {

    @Autowired
    private InvestigateService investigateService;

    @ApiOperation(value = "查询问卷信息接口", notes = "查询问卷信息接口")
    @PostMapping("/queryInvestigateInfo")
    public HttpBaseResponse<InvestigateQueryResponse> queryInvestigateInfo(@RequestBody InvestigateQueryRequest request) {
        if (null == request.getInvestigateId()) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        return HttpBaseResponse.success(investigateService.queryInvestigateInfo(request));
    }

    @ApiOperation(value = "提交问卷接口", notes = "提交问卷接口")
    @PostMapping("/commitInvestigate")
    public HttpBaseResponse commitInvestigate(@RequestBody InvestigateCommitRequest request) {
        if (null == request.getInvestigateId()) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        investigateService.commitInvestigate(request);
        return HttpBaseResponse.success();
    }
}

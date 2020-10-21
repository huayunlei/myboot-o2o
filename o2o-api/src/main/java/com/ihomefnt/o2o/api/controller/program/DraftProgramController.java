package com.ihomefnt.o2o.api.controller.program;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.program.vo.request.CompareDraftRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.CompareDraftResponse;
import com.ihomefnt.o2o.intf.service.program.DraftProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 草稿方案
 */
@RestController
@Api(tags = "【草稿方案数据查询API】")
@RequestMapping("/draftProgram")
public class DraftProgramController {

    @Autowired
    DraftProgramService draftProgramService;

    @ApiOperation(value = "已选方案对比", notes = "已选方案对比（草稿比对）")
    @RequestMapping(value = "/compareDraftList", method = RequestMethod.POST)
    public HttpBaseResponse<CompareDraftResponse> compareDraftList(@RequestBody CompareDraftRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getDraftSimpleList()) || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        CompareDraftResponse response = draftProgramService.compareDraftList(request);
        return HttpBaseResponse.success(response);

    }

}
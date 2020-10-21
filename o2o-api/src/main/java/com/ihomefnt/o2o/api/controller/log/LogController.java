package com.ihomefnt.o2o.api.controller.log;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.log.vo.request.LogRequestVo;
import com.ihomefnt.o2o.intf.service.log.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Api(tags = "【操作日志API】")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 功能描述：记录APP操作日志
     */
    @ApiOperation(value = "记录APP操作日志", notes = "记录APP操作日志")
    @RequestMapping(value = "/addOperationLog", method = RequestMethod.POST)
    public HttpBaseResponse<Void> addOperationLog(@RequestBody LogRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        logService.addOperationLog(request);
        return HttpBaseResponse.success();
    }

    /**
     * 功能描述：记录日志
     */
    @ApiOperation(value = "记录日志", notes = "记录日志（无需登陆）")
    @RequestMapping(value = "/addLog", method = RequestMethod.POST)
    public HttpBaseResponse<Void> addLog(@RequestBody LogRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        logService.addLog(request);
        return HttpBaseResponse.success();
    }

}

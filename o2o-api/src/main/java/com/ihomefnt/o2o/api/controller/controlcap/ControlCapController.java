package com.ihomefnt.o2o.api.controller.controlcap;

import com.ihomefnt.o2o.intf.domain.controlcap.vo.request.AccessOptionsByBuildingRequestVo;
import com.ihomefnt.o2o.intf.domain.controlcap.vo.response.ProgramListForHouseTypeResponseVo;
import com.ihomefnt.o2o.intf.proxy.controlcap.ControlCapService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingInfo;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liyonggang
 * @create 2018-11-28 10:44
 */
@Api(tags = "【地推api】")
@RestController
@RequestMapping("/controlCap")
public class ControlCapController {

    @Autowired
    private ControlCapService controlCapService;

    @Autowired
    private SmsProxy smsProxy;

    /**
     * 根据楼盘id获取分区,楼栋,单元,房号
     *
     * @return
     */
    @ApiOperation(value = "根据楼盘id获取分区,楼栋,单元,房号")
    @RequestMapping(value = "/getAccessOptionsByBuildingId", method = RequestMethod.POST)
    public HttpBaseResponse<BuildingInfo> getAccessOptionsByBuildingId(@RequestBody AccessOptionsByBuildingRequestVo request) {
        if (request == null || request.getBuildingId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        return HttpBaseResponse.success(controlCapService.getAccessOptionsByBuildingId(request.getBuildingId()));
    }

    /**
     * 根据户型id获取当前户型方案列表
     *
     * @return
     */
    @ApiOperation(value = "根据楼盘id,空间id,户型id获取当前户型方案列表附加验证码", notes = "需要验证码")
    @RequestMapping(value = "/getProgramListByHouseTypeAndBuildingIdAndZoneId", method = RequestMethod.POST)
    public HttpBaseResponse<ProgramListForHouseTypeResponseVo> getProgramListByHouseTypeAndBuildingIdAndZoneId(@RequestBody AccessOptionsByBuildingRequestVo request) {
        if (request == null || request.getBuildingId() == null || request.getHouseTypeId() == null || request.getZoneId() == null || request.getWidth() == null ||
                StringUtils.isBlank(request.getMobile()) || StringUtils.isBlank(request.getSmsCode()) || request.getCustomerInfoDto() == null || request.getCustomerInfoDto().getBuildingId() == null ||
                request.getCustomerInfoDto().getBuildingName() == null || request.getCustomerInfoDto().getHouseTypeId() == null ||
                request.getCustomerInfoDto().getHouseTypeName() == null || request.getCustomerInfoDto().getHousingNum() == null || request.getCustomerInfoDto().getPartitionName() == null ||
                request.getCustomerInfoDto().getPhoneNumber() == null || request.getCustomerInfoDto().getRoomNum() == null || request.getCustomerInfoDto().getSize() == null || request.getCustomerInfoDto().getUnitNum() == null || request.getCustomerInfoDto().getZoneId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        if (!smsProxy.checkSmsCode(new CheckSmsCodeParamVo(request.getMobile(), 2, request.getSmsCode()))) {//校验验证码
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, "验证码错误!");
        }
        return HttpBaseResponse.success(controlCapService.getProgramListByHouseIdAndBuildingIdAndZoneId(request));
    }

    /**
     * 根据户型id获取其他户型方案列表
     *
     * @return
     */
    @ApiOperation(value = "根据楼盘id,空间id,户型id获取其他户型方案列表", notes = "无需验证码")
    @RequestMapping(value = "/getProgramListByNotHouseTypeAndBuildingIdAndZoneId", method = RequestMethod.POST)
    public HttpBaseResponse<List<ProgramListForHouseTypeResponseVo>> getProgramListByNotHouseTypeAndBuildingIdAndZoneId(@RequestBody AccessOptionsByBuildingRequestVo request) {
        if (request == null || request.getBuildingId() == null || request.getHouseTypeId() == null || request.getZoneId() == null || request.getWidth() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        return HttpBaseResponse.success(controlCapService.getProgramListByNotHouseTypeAndBuildingIdAndZoneId(request));
    }
}

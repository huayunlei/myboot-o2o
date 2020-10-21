package com.ihomefnt.o2o.api.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.api.controller.BaseController;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingCityInfo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingNoInfo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingRoomInfo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.UserIsHasCodeNewAndAgentHouseInfoResponse;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.request.BuildingAddUpdateRequest;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.request.BuildingSearchRequest;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.request.HouseInfoSearchRequest;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.request.ZoneSearchRequest;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.response.*;
import com.ihomefnt.o2o.intf.domain.program.dto.THouseResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.BuildingHouseInfoResponse;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APP4.0新版楼盘信息
 *
 * @author ZHAO
 */
@Api(tags = "【楼盘信息api】")
@RequestMapping("/homeBuilding")
@RestController
public class HomeBuildingController extends BaseController {
    @Autowired
    private HomeBuildingService homeBuildingService;

    @ApiOperation(value = "查询风格信息", notes = "查询风格信息")
    @RequestMapping(value = "/getStyleInfo", method = RequestMethod.POST)
    public HttpBaseResponse<BasePropertyListResponseVo> getStyleInfo() {
        BasePropertyListResponseVo responseVo = homeBuildingService.getStyleInfo();

        return HttpBaseResponse.success(responseVo);
    }

    @ApiOperation(value = "查询省份、城市、楼盘、分区", notes = "查询省份、城市、楼盘、分区")
    @RequestMapping(value = "/getBuildingInfo", method = RequestMethod.POST)
    public HttpBaseResponse<BuildingInfoListResponseVo> getBuildingInfo(@RequestBody HttpBaseRequest request) {
        List<BuildingProvinceResponse> buildingProvinceResponses = homeBuildingService.getBuildingInfo();

        BuildingInfoListResponseVo result = new BuildingInfoListResponseVo();
        result.setBuildingProvinceList(buildingProvinceResponses);
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "新增、编辑房产户型信息", notes = "新增、编辑房产户型信息")
    @RequestMapping(value = "/addOrUpdateHouseInfo", method = RequestMethod.POST)
    public HttpBaseResponse<BuildingHouseInfoResponse> addOrUpdateHouseInfo(@RequestBody BuildingAddUpdateRequest request) {
        if (request.getUserInfo() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        if (StringUtils.isNotBlank(request.getBuildingInfo())) {//完善房产信息手动输入信息
            try {
                JSONObject buildingInfo = JSON.parseObject(request.getBuildingInfo());
                if (buildingInfo.get("type") == null) {
                    return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误");
                }
                String type = String.valueOf(buildingInfo.get("type"));
                if (type.equals("2") || type.equals("3")) {//手动输入
                    request.setBuildingId(-1);
                    request.setHouseTypeId(-1);
                }
            } catch (Exception e) {
                return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误");
            }
        }
        if (StringUtils.isEmpty(request.getAccessToken()) || StringUtil.isBlank(request.getCustomerName())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        BuildingHouseInfoResponse response = homeBuildingService.addOrUpdateHouseInfo(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "查询房产户型信息", notes = "查询房产户型信息")
    @RequestMapping(value = "/queryHouseInfoById", method = RequestMethod.POST)
    public HttpBaseResponse<BuildingHouseInfoResponse> queryHouseInfoById(@RequestBody BuildingSearchRequest request) {
        if (request == null || (request.getHouseId() == null && request.getCustomerHouseId() == null)) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        BuildingHouseInfoResponse houseInfoResponse = homeBuildingService.queryHouseInfoById(request.getCustomerHouseId() == null ? request.getHouseId() : request.getCustomerHouseId());
        if (request.getUserInfo() == null || houseInfoResponse == null || houseInfoResponse.getUserId() == null || !request.getUserInfo().getId().equals(houseInfoResponse.getUserId())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.NOT_YOUR_HOUSE);
        }

        return HttpBaseResponse.success(houseInfoResponse);
    }

    /**
     * 查询用户是否是有邀请码的用户并返回经纪人房产信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询用户是否是有邀请码的用户并返回经纪人房产信息", notes = "查询用户是否是有邀请码的用户并返回经纪人房产信息")
    @RequestMapping(value = "/queryUserIsHasCodeNewAndAgentHouseInfo", method = RequestMethod.POST)
    public HttpBaseResponse<UserIsHasCodeNewAndAgentHouseInfoResponse> queryUserIsHasCodeNewAndAgentHouseInfo(@RequestBody HttpBaseRequest request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        UserIsHasCodeNewAndAgentHouseInfoResponse response = homeBuildingService.queryUserIsHasCodeNewAndAgentHouseInfo(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "根据户型id户型信息", notes = "根据户型id户型信息")
    @RequestMapping(value = "/queryBuildingByLayoutId", method = RequestMethod.POST)
    public HttpBaseResponse<THouseResponse> queryBuildingByLayoutId(@RequestBody HouseInfoSearchRequest request) {
        if (request == null || request.getLayoutId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        THouseResponse houseInfoResponse = homeBuildingService.queryBuildingByLayoutId(request.getLayoutId());
        return HttpBaseResponse.success(houseInfoResponse);
    }

    @ApiOperation(value = "查询房产可新增编辑次数", notes = "查询房产可新增编辑次数")
    @RequestMapping(value = "/queryHouseAddEditCount", method = RequestMethod.POST)
    public HttpBaseResponse<HouseAddEditCountResponse> queryHouseAddEditCount(@RequestBody BuildingSearchRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        HouseAddEditCountResponse addEditCountResponse = homeBuildingService.queryHouseAddEditCount(request.getCustomerHouseId() == null ? request.getHouseId() : request.getCustomerHouseId());
        return HttpBaseResponse.success(addEditCountResponse);
    }

    @CrossOrigin(origins = "*")
    @ApiOperation(value = "根据分区查询楼栋号、单元号、房号、户型", notes = "根据分区查询楼栋号、单元号、房号、户型")
    @RequestMapping(value = "/queryBuildingUnitNoByZoneId", method = RequestMethod.POST)
    public HttpBaseResponse<BuildingNoListResponse> queryBuildingUnitNoByZoneId(@RequestBody ZoneSearchRequest request) {
        if (request == null || request.getZoneId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        Integer width = 0;
        if (request != null && request.getWidth() != null) {
            width = request.getWidth();
        }

        BuildingNoListResponse buildingNoListResponse = homeBuildingService.queryBuildingUnitNoByZoneId(request.getZoneId(), width);
        return HttpBaseResponse.success(buildingNoListResponse);
    }

    @ApiOperation(value = "查询所有的城市、楼盘、分区", notes = "查询省份、城市、楼盘、分区")
    @RequestMapping(value = "/V6/getBuildingInfoStartForCity", method = RequestMethod.POST)
    public HttpBaseResponse<BuildingInfoListResponseVo> getBuildingInfoStartForCity(@RequestBody HttpBaseRequest request) {
        List<BuildingCityInfo> buildingProvinceResponses = homeBuildingService.getBuildingInfoStartForCity();
        BuildingInfoListResponseVo result = new BuildingInfoListResponseVo();
        result.setBuildingCityInfoList(buildingProvinceResponses);
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "查询户型格局配置项", notes = "查询省份、城市、楼盘、分区")
    @RequestMapping(value = "/queryHousePatternConfig", method = RequestMethod.POST)
    public HttpBaseResponse<HousePatternConfigResponse> queryHousePatternConfig(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(homeBuildingService.queryHousePatternConfig());
    }

    @ApiOperation(value = "根据分区查询楼栋单元", notes = "根据分区查询楼栋单元")
    @RequestMapping(value = "/queryBuildingUnitListByZoneId", method = RequestMethod.POST)
    public HttpBaseResponse<List<BuildingNoInfo>> queryBuildingUnitListByZoneId(@RequestBody ZoneSearchRequest request) {
        if (request.getZoneId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(homeBuildingService.queryBuildingUnitListByZoneId(request.getZoneId()));
    }

    @ApiOperation(value = "根据分区，楼栋，单元查房间", notes = "根据分区，楼栋，单元查房间")
    @RequestMapping(value = "/queryRoomListByZoneIdAndBuildingIdAndUnitId", method = RequestMethod.POST)
    public HttpBaseResponse<List<BuildingRoomInfo>> queryRoomListByZoneIdAndBuildingIdAndUnitId(@RequestBody ZoneSearchRequest request) {
        if (request.getZoneId() == null || request.getBuildingId() == null || StringUtils.isBlank(request.getUnitNo())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(homeBuildingService.queryRoomListByZoneIdAndBuildingIdAndUnitId(request.getZoneId(), request.getBuildingId(), request.getUnitNo()));
    }
}

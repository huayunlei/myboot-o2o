package com.ihomefnt.o2o.api.controller.lechange;


import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.o2o.common.config.ApiConfig;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.lechange.dto.GetDeviceListPageVo;
import com.ihomefnt.o2o.intf.domain.lechange.vo.request.*;
import com.ihomefnt.o2o.intf.domain.lechange.vo.response.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.lechange.LechangeCodeEnum;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.lechange.LechangeService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "【乐橙API】")
@RequestMapping("/lechange")
public class LechangeController {

    @Resource
    private ApiConfig apiconfig;

    @Resource
    private UserService userService;

    @Resource
    private LechangeService lechangeService;

    @NacosValue(value = "${leCheng.group.list}", autoRefreshed = true)
    private String LECHANG_GROUP_LIST;

    @ApiOperation(value = "获取用户的设备信息")
    @RequestMapping(value = "/deviceInfo", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceInfoResponseVo> getDeviceInfo(@Json HttpBaseRequest request) {
        if (null == request || null == request.getAccessToken()) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        DeviceInfoResponseVo obj = lechangeService.getDeviceInfo(request, apiconfig.getLechangeBaseUrl());
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "获取设备token(用户需要登录)")
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeTokenResponseVo> getLechangeToken(@Json HttpBaseRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String token = request.getAccessToken();
        UserDto userByToken = userService.getUserByToken(token);
        if (null == userByToken) {
            return HttpBaseResponse.fail(HttpResponseCode.ADMIN_ILLEGAL, MessageConstant.USER_NOT_LOGIN);
        }

        String queryAccessToken = lechangeService.queryAccessToken();
        return HttpBaseResponse.success(new LechangeTokenResponseVo(queryAccessToken));
    }

    @ApiOperation(value = "获取设备token(用户不登录即可获取)")
    @RequestMapping(value = "/getTokenWithoutLogin", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeTokenResponseVo> getLechangeTokenWithOutLogin() {
        String queryAccessToken = lechangeService.queryAccessToken();
        return HttpBaseResponse.success(new LechangeTokenResponseVo(queryAccessToken));
    }

    @ApiOperation(value = "获取全部设备列表,效率很低,不推荐使用")
    @RequestMapping(value = "/getAllDeviceList", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceInfoMapResponseVo> getAllDeviceList() {
        List<Map<String, Object>> list = lechangeService.getAllDeviceList(null, 100);
        if (CollectionUtils.isEmpty(list)) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        DeviceInfoDetailMapResponseVo detail = new DeviceInfoDetailMapResponseVo();
        detail.setDevices(list);
        detail.setCount(list.size());
        return HttpBaseResponse.success(new DeviceInfoMapResponseVo(detail));
    }

    @ApiOperation(value = "获取乐橙分组下的设备")
    @RequestMapping(value = "/groupDeviceList", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceInfoMapResponseVo> groupDeviceList(@RequestBody HttpGroupRequest request) {
        Long groupId = null;
        if (request != null && request.getGroupId() != null) {
            groupId = request.getGroupId();
        }
        List<Map<String, Object>> list;
        // 这里所有的设备只查询在线,业务规范
        if (groupId == null || groupId == 0L) {
            list = lechangeService.getDeviceListForBind(null, 1);
        } else {
            if (groupId.intValue() == 11) {
                list = lechangeService.getDeviceListForYBJ(1);
            } else {
                list = lechangeService.getDeviceListForBind(groupId.intValue(), 1);
            }
        }

        DeviceInfoDetailMapResponseVo detail = new DeviceInfoDetailMapResponseVo();
        detail.setDevices(list);
        detail.setCount(list.size());
        return HttpBaseResponse.success(new DeviceInfoMapResponseVo(detail));
    }

    @ApiOperation(value = "获取乐橙的设备分组")
    @RequestMapping(value = "/getGroupList", method = RequestMethod.POST)
    public HttpBaseResponse<GroupListResponseVo> getGroupList() {
        return HttpBaseResponse.success(new GroupListResponseVo(JSON.parseArray(LECHANG_GROUP_LIST, GroupVo.class)));
    }

    @ApiOperation(value = "绑定设备")
    @RequestMapping(value = "/bindDevice", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeCommonResultResponseVo> bindDevice(@RequestBody HttpDeviceRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        String code = request.getCode();
        String result = lechangeService.bindDevice(deviceId, code);
        if (!result.equals(LechangeCodeEnum.CODE_0.getCode())) {
            String message;
            if (StringUtils.isNotBlank(LechangeCodeEnum.getMsg(result))) {
                message = LechangeCodeEnum.getMsg(result);
            } else {
                message = LechangeCodeEnum.CODE_1.getMsg();
            }
            return HttpBaseResponse.fail(message);
        }
        return HttpBaseResponse.success(new LechangeCommonResultResponseVo(result), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "根据条件获取乐橙设备列表")
    @RequestMapping(value = "/groupDeviceListByStatus", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceInfoMapResponseVo> groupDeviceListByStatus(@RequestBody HttpGroupStatusRequest request) {
        Long groupId = null;
        if (request != null && request.getGroupId() != null) {
            groupId = request.getGroupId();
        }
        Integer status = null;
        if (request != null && request.getStatus() != null) {
            status = request.getStatus();
        }
        // 0离线，1在线,-1:全部
        if (status != null && status == -1) {
            status = null;
        }
        assert request != null;
        Integer pageNo = request.getPageNo();
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        Integer pageSize = request.getPageSize();
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        //先查摩看，再查乐橙
        DeviceInfoDetailMapResponseVo detail = new DeviceInfoDetailMapResponseVo();

        // groupId 为0 查询全部
        if (groupId != null && 0 == groupId) {
            groupId = null;
        }
        GetDeviceListPageVo getDeviceListPageVo = new GetDeviceListPageVo()
                .setStatus(0)
                .setCameraStatus(status)
                .setBrand(2)
                .setPageNo(pageNo)
                .setPageSize(pageSize);
        if (groupId != null) {
            getDeviceListPageVo.setCompanyId(groupId.intValue());
        }
        List<Map<String, Object>> mokanData = lechangeService.getDeviceList(getDeviceListPageVo);
        if (CollectionUtils.isNotEmpty(mokanData)) {
            list.addAll(mokanData);
            detail.setMokanPageNo(request.getMokanPageNo() + 1);
        }
        if (list.size() < pageSize) {
            List<Map<String, Object>> lechengList = new ArrayList<>();
            //这里分页与在线,离线两个条件冲突,只能二选一
            if (groupId == null || groupId == 0L) {
                // 根据条件查询所有的乐橙设备
                lechengList = lechangeService.getAllDeviceList(null, pageSize);
            } else {
                // 根据对应状态来查询乐橙设备
                if (groupId.intValue() == 11) {
                    lechengList = lechangeService.getDeviceListForYBJ(status);
                } else {
                    lechengList = lechangeService.getDeviceListForBind(groupId.intValue(), status);
                }
            }
            if (CollectionUtils.isNotEmpty(lechengList)) {
                list.addAll(lechengList);
                //增加乐橙数据标记位，乐橙数据不再分页查
                detail.setLechengPageNo(1);
            }
        }

        detail.setDevices(list);
        detail.setCount(list.size());
        return HttpBaseResponse.success(new DeviceInfoMapResponseVo(detail));
    }

    @ApiOperation(value = "设置设备翻转状态")
    @RequestMapping(value = "/modifyFrameReverseStatus", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeCommonResultResponseVo> modifyFrameReverseStatus(
            @RequestBody HttpModifyFrameReverseStatusRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())
                || StringUtils.isBlank(request.getChannelId()) || StringUtils.isBlank(request.getDirection())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }

        String deviceId = request.getDeviceId();
        String channelId = request.getChannelId();
        String direction = request.getDirection();
        String result = lechangeService.modifyFrameReverseStatus(deviceId, channelId, direction);

        if (!result.equals(LechangeCodeEnum.CODE_0.getCode())) {
            String message;
            if (StringUtils.isNotBlank(LechangeCodeEnum.getMsg(result))) {
                message = LechangeCodeEnum.getMsg(result);
            } else {
                message = LechangeCodeEnum.CODE_2.getMsg();
            }
            return HttpBaseResponse.fail(message);
        }
        return HttpBaseResponse.success(new LechangeCommonResultResponseVo(result), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "获取设备翻转状态")
    @RequestMapping(value = "/frameReverseStatus", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeCommonResultResponseVo> frameReverseStatus(@RequestBody HttpframeReverseStatusRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())
                || StringUtils.isBlank(request.getChannelId())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        String channelId = request.getChannelId();
        String result = lechangeService.frameReverseStatus(deviceId, channelId);

        if (StringUtils.isBlank(result)) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_2.getMsg());
        }
        return HttpBaseResponse.success(new LechangeCommonResultResponseVo(result), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "修改设备名称")
    @RequestMapping(value = "/modifyDeviceName", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeCommonResultResponseVo> modifyDeviceName(@RequestBody HttpModifyDeviceNameRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId()) || StringUtils.isBlank(request.getName())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        String name = request.getName();
        String result = lechangeService.modifyDeviceName(deviceId, name);

        if (!result.equals(LechangeCodeEnum.CODE_0.getCode())) {
            String message;
            if (StringUtils.isNotBlank(LechangeCodeEnum.getMsg(result))) {
                message = LechangeCodeEnum.getMsg(result);
            } else {
                message = LechangeCodeEnum.CODE_2.getMsg();
            }
            return HttpBaseResponse.fail(message);
        }
        return HttpBaseResponse.success(new LechangeCommonResultResponseVo(result), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "查询BOSS设备信息")
    @RequestMapping(value = "/queryDeviceInfo", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceDetailResponse> queryDeviceInfo(@RequestBody HttpDeviceRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        DeviceDetailResponse result = lechangeService.queryDeviceInfo(deviceId);
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "查询周边wifi信息")
    @RequestMapping(value = "/queryAroundWifiList", method = RequestMethod.POST)
    public HttpBaseResponse<AroundWifiListResponseVo> queryAroundWifiList(@RequestBody HttpframeReverseStatusRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        List<WifiInfoResponse> result = lechangeService.queryAroundWifiList(deviceId);
        return HttpBaseResponse.success(new AroundWifiListResponseVo(result), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "修改设备连接热点")
    @RequestMapping(value = "/modifyDeviceWifi", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeCommonResultResponseVo> modifyDeviceWifi(@RequestBody HttpModifyDeviceWifiRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId()) || StringUtils.isBlank(request.getSsid())
                || StringUtils.isBlank(request.getBssid()) || StringUtils.isBlank(request.getPassword())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        String ssid = request.getSsid();
        String bssid = request.getBssid();
        String password = request.getPassword();
        String result = lechangeService.modifyDeviceWifi(deviceId, ssid, bssid, password);

        if (!result.equals(LechangeCodeEnum.CODE_0.getCode())) {
            String message;
            if (StringUtils.isNotBlank(LechangeCodeEnum.getMsg(result))) {
                message = LechangeCodeEnum.getMsg(result);
            } else {
                message = LechangeCodeEnum.CODE_2.getMsg();
            }
            return HttpBaseResponse.fail(message);
        }
        return HttpBaseResponse.success(new LechangeCommonResultResponseVo(result), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "解绑设备")
    @RequestMapping(value = "/unBindDevice", method = RequestMethod.POST)
    public HttpBaseResponse<LechangeCommonResultResponseVo> unBindDevice(@RequestBody HttpDeviceRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        String code = request.getCode();
        String result = lechangeService.unBindDevice(deviceId, code);

        if (!result.equals(LechangeCodeEnum.CODE_0.getCode())) {
            String message;
            if (StringUtils.isNotBlank(LechangeCodeEnum.getMsg(result))) {
                message = LechangeCodeEnum.getMsg(result);
            } else {
                message = LechangeCodeEnum.CODE_1.getMsg();
            }
            return HttpBaseResponse.fail(message);
        }
        return HttpBaseResponse.success(new LechangeCommonResultResponseVo(result), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "查询乐橙设备信息")
    @RequestMapping(value = "/bindDeviceInfo", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceInfoLechangeResponse> bindDeviceInfo(@RequestBody HttpDeviceRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        DeviceInfoLechangeResponse result = lechangeService.bindDeviceInfo(deviceId);

        return HttpBaseResponse.success(result, LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "搜索设备列表")
    @RequestMapping(value = "/searchDeviceList", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceInfoMapResponseVo> searchDeviceList(@RequestBody HttpSearchDeviceListRequest request) {
        if (request == null || StringUtils.isBlank(request.getDeviceId())) {
            return HttpBaseResponse.fail(LechangeCodeEnum.CODE_NULL.getMsg());
        }
        String deviceId = request.getDeviceId();
        Integer status = request.getStatus();
        List<Map<String, Object>> list = lechangeService.searchDeviceList(deviceId, status);
        DeviceInfoDetailMapResponseVo detail = new DeviceInfoDetailMapResponseVo();
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        detail.setDevices(list);
        detail.setCount(list.size());
        return HttpBaseResponse.success(new DeviceInfoMapResponseVo(detail), LechangeCodeEnum.CODE_0.getMsg());
    }

    @ApiOperation(value = "查询大屏展示的9个设备详情")
    @RequestMapping(value = "/queryDisplayDevices", method = RequestMethod.POST)
    public HttpBaseResponse<DeviceInfoMapResponseVo> queryDisplayDevices() {
        List<Map<String, Object>> list = lechangeService.queryDisplayDevices();
        DeviceInfoDetailMapResponseVo detail = new DeviceInfoDetailMapResponseVo();
        detail.setDevices(list);
        detail.setCount(CollectionUtils.isEmpty(list) ? 0 : list.size());
        return HttpBaseResponse.success(new DeviceInfoMapResponseVo(detail));
    }
}

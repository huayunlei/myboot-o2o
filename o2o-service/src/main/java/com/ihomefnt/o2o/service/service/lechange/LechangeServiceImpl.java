package com.ihomefnt.o2o.service.service.lechange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.concurrent.TaskProcessManager;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.lechange.dto.*;
import com.ihomefnt.o2o.intf.domain.lechange.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.lechange.DeviceConstants;
import com.ihomefnt.o2o.intf.manager.constant.lechange.HardOrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.lechange.LechangeCodeEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.http.HttpUtil;
import com.ihomefnt.o2o.intf.manager.util.common.secure.SignHelper;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.lechange.HbmsProxy;
import com.ihomefnt.o2o.intf.proxy.lechange.LechangeProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.lechange.LechangeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.*;

@Service
public class LechangeServiceImpl implements LechangeService {

    private static final Logger LOG = LoggerFactory.getLogger(LechangeServiceImpl.class);

    @Autowired
    private LechangeProxy lechengDao;

    @Autowired
    private HbmsProxy hbmsProxy;

    @Autowired
    private DicProxy dicProxy;

    @Autowired
    UserProxy userProxy;


    @Autowired
    LechangeAsync lechangeAsync;

    /**
     * 通过boss端接口来查询满足条件设备列表,并封装成list格式
     *
     * @param status    boss侧的设备状态 0-现有 3-空闲设备
     * @param companyId :boss侧的分公司id
     * @return
     */
    private List<GetDeviceListResultVo> getDeviceListByBossStatusAndCompanyId(Integer status, Integer companyId) {
        GetDeviceListParamVo param = new GetDeviceListParamVo();

        List<GetDeviceListResultVo> resultList = new ArrayList<GetDeviceListResultVo>();

        if (status != null) {
            param.setStatus(status + "");
        }
        if (companyId != null) {
            param.setCompanyId(companyId);
        }
        //不提供分页
        PagesVo<GetDeviceListResultVo> page = hbmsProxy.getSimpleDeviceList(param);
        if (page == null) {
            return resultList;
        }
        // 设备列表
        List<GetDeviceListResultVo> list = page.getList();
        // 列表总数
        Integer totalRecords = page.getTotalRecords();
        if (CollectionUtils.isEmpty(list) || totalRecords == null) {
            return resultList;
        }
        resultList.addAll(list);

        return resultList;
    }

    @Override
    public String queryAccessToken() {
        return lechengDao.queryAccessToken();
    }

    @Override
    public Map<String, GetDeviceListResultVo> getAllDeviceMapByBoss() {
        return getDeviceMapByBossStatusAndCompanyId(null, null);
    }

    /**
     * 通过boss端接口的查询满足条件设备列表,并封装成map格式
     *
     * @param bossstatus boss侧 的设备状态 0-现有 3-空闲设备
     * @param companyId  :boss侧 的分公司id
     * @return
     */
    private Map<String, GetDeviceListResultVo> getDeviceMapByBossStatusAndCompanyId(Integer bossstatus,
                                                                                    Integer companyId) {
        Map<String, GetDeviceListResultVo> map = new HashMap<String, GetDeviceListResultVo>();
        List<GetDeviceListResultVo> resultList = this.getDeviceListByBossStatusAndCompanyId(bossstatus, companyId);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (GetDeviceListResultVo vo : resultList) {
                if (StringUtils.isNotBlank(vo.getCameraSn())) {
                    map.put(vo.getCameraSn(), vo);
                }
            }
        }
        return map;
    }

    /**
     * 合并数据
     *
     * @param deviceList 乐橙侧数据
     * @param map        boss侧数据
     * @return
     */
    private List<Map<String, Object>> buildList(List<JSONObject> deviceList, Map<String, GetDeviceListResultVo> map) {
        long t2 = System.currentTimeMillis();
        List<Map<String, Object>> list = new ArrayList<>();
        for (JSONObject object : deviceList) {
            String deviceId = object.getString("deviceId");
            JSONArray jsonArray2 = object.getJSONArray("channels");
            JSONObject object2 = (JSONObject) jsonArray2.get(0);
            // 赋值
            if (map != null && StringUtils.isNotBlank(deviceId)) {
                GetDeviceListResultVo vo = map.get(deviceId);
                if (vo != null) {
                    String liveImage = vo.getUrl();
                    if (StringUtil.isNotBlank(liveImage)) {
                        object2.put("channelPicUrl", liveImage);
                    }
                    String orderStatus = vo.getProjectStatus();
                    if (StringUtil.isNotBlank(orderStatus)) {
                        String msg = HardOrderConstant.getMsg(orderStatus);
                        if (StringUtil.isNotBlank(orderStatus)) {
                            object.put("orderStatus", msg);
                        } else {
                            object.put("orderStatus", "");
                        }
                    } else {
                        object.put("orderStatus", "");
                    }
                }
            }
            jsonArray2.set(0, object2);
            object.put("channels", jsonArray2);
            Map<String, Object> json2map = JsonUtils.json2map(object.toString());
            list.add(json2map);
        }
        long t3 = System.currentTimeMillis();
        LOG.info("buildList time:{} ms", t3 - t2);
        return list;
    }

    /**
     * 递归查询所有数据
     *
     * @param pageSize
     * @return
     */
    private List<JSONObject> getDeviceListPageByParams(Integer pageSize) {
        long t1 = System.currentTimeMillis();
        List<JSONObject> list = new ArrayList<>();
        String token = this.queryAccessToken();
        Integer pageNo = 1;
        // 递归
        while (true) {
            int countObject = deviceList(pageSize, pageNo, token, list);
            // 相等表示还有下一页
            if (countObject == pageSize) {
                pageNo++;
            } else {
                // 小于表示到了最后一页
                break;
            }
        }
        long t2 = System.currentTimeMillis();
        LOG.info("lechange deviceList all datas time:{} ms", t2 - t1);
        return list;
    }

    /**
     * 只查询指定某页数据
     *
     * @param pageSize
     * @param pageNo   : 指定某页
     * @param token
     * @param list
     * @return
     */
    private int deviceList(Integer pageSize, Integer pageNo, String token, List<JSONObject> list) {
        int start = 1 + pageSize * (pageNo - 1);
        int end = pageSize * pageNo;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("queryRange", start + "-" + end);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/deviceList";
        LOG.info("deviceList param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("deviceList result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code").equals("0")) {
            JSONObject jsonObject3 = jsonObject2.getJSONObject("data");
            JSONArray jsonArray = jsonObject3.getJSONArray("devices");
            int countObject = jsonArray.size();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                list.add(object);
            }
            return countObject;
        }
        return -1;
    }

    /**
     * 排序
     *
     * @param list
     */
    private void sort(List<Map<String, Object>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        long t1 = System.currentTimeMillis();
        list.sort((o1, o2) -> {
            Integer jsonArray1 = Integer.parseInt(o1.get("status").toString());
            Integer jsonArray2 = Integer.parseInt(o2.get("status").toString());
            return jsonArray2 - jsonArray1;
        });
        long t2 = System.currentTimeMillis();
        LOG.info("sort time:{} ms", t2 - t1);
    }

    @Override
    public List<Map<String, Object>> getAllDeviceList(Integer pageNo, Integer pageSize) {
        List<Map<String, Object>> list;
        // 通过乐橙接口,获取指定页pageNo的乐橙设备,pageNo为空则表示获取所有设备
        List<JSONObject> deviceList = this.getAllDeviceListPage(pageNo, pageSize);
        if (CollectionUtils.isEmpty(deviceList)) {
            return new ArrayList<>();
        }
        // 通过boss端接口,获取所有的设备列表,封装成map
        Map<String, GetDeviceListResultVo> map = this.getAllDeviceMapByBoss();
        // 合并数据
        list = this.buildList(deviceList, map);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        // 排序
        this.sort(list);
        return list;
    }

    /**
     * 获取指定页的乐橙设备,为空则表示获取所有设备
     *
     * @param pageNo 分野棉麻
     * @return 设备数据
     */
    private List<JSONObject> getAllDeviceListPage(Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            // 获取所有设备
            List<JSONObject> deviceList = this.getDeviceListPageByParams(pageSize);
            if (CollectionUtils.isEmpty(deviceList)) {
                return new ArrayList<>();
            }
            return deviceList;
        } else {
            // 获取指定页设备
            String token = this.queryAccessToken();
            List<JSONObject> deviceList = new ArrayList<>();
            this.deviceList(pageSize, pageNo, token, deviceList);
            if (CollectionUtils.isEmpty(deviceList)) {
                return new ArrayList<>();
            }
            return deviceList;
        }
    }

    @Override
    public List<Map<String, Object>> getDeviceListForBind(Integer companyId, Integer status) {
        List<Map<String, Object>> list;
        // 获取boss侧所有绑定对应分公司的设备
        Map<String, GetDeviceListResultVo> hbmsMap = this.getDeviceMapByBossStatusAndCompanyId(0, companyId);
        // 通过乐橙接口,查询满足条件的乐橙设备
        List<JSONObject> deviceList = this.getBindDeviceList(hbmsMap, status);
        if (CollectionUtils.isEmpty(deviceList)) {
            return new ArrayList<>();
        }
        // 合并数据
        list = this.buildList(deviceList, hbmsMap);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        // 全部时候,需要排序
        if (status == null) {
            this.sort(list);
        }
        return list;
    }


    /**
     * 通过 乐橙接口,查询满足条件的乐橙设备<br/>
     * 此接口已经实现递归查询<br/>
     *
     * @param hbmsMap      :boss侧绑定设备
     * @param deviceStatus :乐橙侧的设备状态:0-离线,1-在线 ,为空全部
     * @return 设别信息
     */
    private List<JSONObject> getBindDeviceList(Map<String, GetDeviceListResultVo> hbmsMap, Integer deviceStatus) {
        if (hbmsMap == null || hbmsMap.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, GetDeviceListResultVo> entry : hbmsMap.entrySet()) {
            list.add(entry.getKey());
        }
        String token = this.queryAccessToken();
        List<JSONObject> bindList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        int length = (list.size() % 20 == 0) ? list.size() / 20 : list.size() / 20 + 1;// 每次只能请求20个,不足也算一次
        long t1 = System.currentTimeMillis();

        // 异步查询乐橙,获取设备的最新状态
        List<Future<String>> futures = new ArrayList<>();


        List<TaskAction<Object>> taskActions = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int finalI = i;
            taskActions.add(() -> {
                try {
                    List<String> deviceIdList;
                    if (finalI < length - 1) {
                        deviceIdList = list.subList(20 * finalI, 20 * (finalI + 1)); // 除了最后一次
                    } else {
                        deviceIdList = list.subList(20 * finalI, list.size()); // 最后一次
                    }
                    // 将list变成String,中间以逗号分割,最后一次不要逗号
                    StringBuilder deviceIds = new StringBuilder();
                    int j = 0;
                    for (String deviceId : deviceIdList) {
                        deviceIds.append(deviceId);
                        j++;
                        if (j < deviceIdList.size()) {
                            deviceIds.append(",");
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("token", token);
                    jsonObject.put("deviceIds", deviceIds.toString());
                    String combineParam = SignHelper.combineParam(jsonObject.toString());
                    futures.add(lechangeAsync.getDeviceStatus(combineParam));
                } catch (Exception e) {
                    LOG.error("lecheng error message :",e);
                }
                return 1;
            });
        }

        TaskProcessManager.getTaskProcess().executeTask(taskActions);
        //乐橙的设备状态填充
        for (Future<String> future : futures) {
            try {
                String sendPost = future.get();
                JSONObject resultObject = JSON.parseObject(sendPost);
                JSONObject jsonObject2 = resultObject.getJSONObject("result");
                if (jsonObject2.get("code").equals("0")) {
                    JSONArray jsonArray = jsonObject2.getJSONArray("data");
                    for (Object o : jsonArray) {
                        JSONObject object = (JSONObject) o;
                        if (deviceStatus == null) {
                            bindList.add(object);
                        } else {
                            Integer status = object.getInteger("status");
                            // 当前状态：0-离线，1-在线，3-升级中
                            if (status != null && status.intValue() == deviceStatus.intValue()) {
                                bindList.add(object);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        long t2 = System.currentTimeMillis();
        LOG.info("bindDeviceList time:{} ms", t2 - t1);
        return bindList;
    }


    @Override
    public List<Map<String, Object>> getDeviceListForYBJ(Integer status) {
        List<Map<String, Object>> list;
        DicDto vo = dicProxy.queryDicByKey("LECHANGE_DEVICE_IDS");
        if (vo == null || StringUtils.isBlank(vo.getValueDesc())) {
            return new ArrayList<>();
        }
        String[] devices = vo.getValueDesc().split(",");
        List<String> deviceIdList = new ArrayList<>(Arrays.asList(devices));
        // boss侧的所有空闲满足条件设备
        Map<String, GetDeviceListResultVo> hbmsMap = this.getDeviceMapByBossStatusForYBJ(deviceIdList, 3);
        // 通过 乐橙接口,查询满足条件的乐橙设备
        List<JSONObject> deviceList = this.getBindDeviceList(hbmsMap, status);
        if (CollectionUtils.isEmpty(deviceList)) {
            return new ArrayList<>();
        }
        // 合并数据
        list = this.buildList(deviceList, hbmsMap);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        // 全部时候,需要排序
        if (status == null) {
            this.sort(list);
        }
        return list;
    }

    /**
     * 根据boss侧接口,查询满足条件的设备,并封装成map格式
     *
     * @param list   设备ID列表
     * @param status :boss侧 的设备状态 0-现有 3-空闲设备
     * @return 设备数据分组
     */
    private Map<String, GetDeviceListResultVo> getDeviceMapByBossStatusForYBJ(List<String> list, Integer status) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        Map<String, GetDeviceListResultVo> map = new HashMap<>();
        List<GetDeviceListResultVo> resultList = this.getDeviceListByBossStatusAndCompanyId(status, null);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (GetDeviceListResultVo vo : resultList) {
                if (StringUtils.isNotBlank(vo.getCameraSn()) && list.contains(vo.getCameraSn())) {
                    map.put(vo.getCameraSn(), vo);
                }
            }
        }
        return map;
    }

    @Override
    public String bindDevice(String deviceId, String code) {
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        jsonObject.put("code", code);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/bindDevice";
        LOG.info("bindDevice param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("bindDevice result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code") == null) {
            return LechangeCodeEnum.CODE_1.getCode();
        }
        return (String) jsonObject2.get("code");
    }

    @Override
    public String frameReverseStatus(String deviceId, String channelId) {
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        jsonObject.put("channelId", channelId);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/frameReverseStatus";
        LOG.info("frameReverseStatus param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("frameReverseStatus result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.getString("code").equals("0")) {
            JSONObject jsonObject3 = jsonObject2.getJSONObject("data");
            return jsonObject3.getString("direction");
        }
        return null;
    }

    @Override
    public String modifyFrameReverseStatus(String deviceId, String channelId, String direction) {
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        jsonObject.put("channelId", channelId);
        jsonObject.put("direction", direction);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/modifyFrameReverseStatus";
        LOG.info("modifyFrameReverseStatus param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("modifyFrameReverseStatus result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code") == null) {
            return LechangeCodeEnum.CODE_2.getCode();
        }
        return (String) jsonObject2.get("code");
    }

    @Override
    public String modifyDeviceName(String deviceId, String name) {
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        jsonObject.put("name", name);
        jsonObject.put("channelId", "");
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/modifyDeviceName";
        LOG.info("modifyDeviceName param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("modifyDeviceName result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code") == null) {
            return LechangeCodeEnum.CODE_2.getCode();
        }
        return (String) jsonObject2.get("code");
    }

    @Override
    public DeviceDetailResponse queryDeviceInfo(String deviceId) {
        DeviceDetailResponse response = new DeviceDetailResponse();
        GetDeviceListResultVo deviceListResultVo = hbmsProxy.getDeviceBySn(deviceId);
        if (deviceListResultVo != null) {
            response.setCameraSn(deviceListResultVo.getCameraSn());
            response.setNetworkCard(deviceListResultVo.getNetworkCard());
            response.setRouterSn(deviceListResultVo.getRouterSn());
            response.setStatus(deviceListResultVo.getStatus());
            response.setWifiName(deviceListResultVo.getWifiName());
            response.setWifiPassword(deviceListResultVo.getWifiPassword());
        }

        //查询乐橙当前连接信息
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/currentDeviceWifi";
        LOG.info("currentDeviceWifi param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("currentDeviceWifi result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code") != null && LechangeCodeEnum.CODE_0.getCode().equals(jsonObject2.get("code"))) {
            JSONObject jsonObject3 = jsonObject2.getJSONObject("data");
            if (jsonObject3.get("linkEnable") != null) {
                response.setLinkEnable((boolean) jsonObject3.get("linkEnable"));
            }
            if (jsonObject3.get("ssid") != null) {
                response.setSsid((String) jsonObject3.get("ssid"));
            }
        }
        return response;
    }

    @Override
    public List<WifiInfoResponse> queryAroundWifiList(String deviceId) {
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/wifiAround";
        LOG.info("wifiAround param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("wifiAround result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        List<WifiInfoResponse> responses = new ArrayList<>();
        if (jsonObject2.get("code").equals("0")) {
            JSONObject jsonObject3 = jsonObject2.getJSONObject("data");
            JSONArray jsonArray = jsonObject3.getJSONArray("wLan");
            for (Object o : jsonArray) {
                JSONObject object = (JSONObject) o;
                WifiInfoResponse wifiInfo = new WifiInfoResponse();
                wifiInfo.setAuth(object.getString("auth"));
                wifiInfo.setBssid(object.getString("bssid"));
                wifiInfo.setIntensity(object.getInteger("intensity"));
                wifiInfo.setLinkStatus(object.getInteger("linkStatus"));
                wifiInfo.setSsid(object.getString("ssid"));
                responses.add(wifiInfo);
            }
        }

        return responses;
    }

    @Override
    public String modifyDeviceWifi(String deviceId, String ssid, String bssid, String password) {
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        jsonObject.put("ssid", ssid);
        jsonObject.put("bssid", bssid);
        jsonObject.put("linkEnable", true);
        jsonObject.put("password", password);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/controlDeviceWifi";
        LOG.info("modifyDeviceWifi param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("modifyDeviceWifi result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code") == null) {
            return LechangeCodeEnum.CODE_2.getCode();
        }
        return (String) jsonObject2.get("code");
    }

    @Override
    public String unBindDevice(String deviceId, String code) {
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/unBindDevice";
        LOG.info("unBindDevice param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("unBindDevice result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code") == null) {
            return LechangeCodeEnum.CODE_1.getCode();
        }
        return (String) jsonObject2.get("code");
    }

    @Override
    public DeviceInfoLechangeResponse bindDeviceInfo(String deviceId) {
        DeviceInfoLechangeResponse response = new DeviceInfoLechangeResponse();
        String token = this.queryAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("deviceId", deviceId);
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        String url = "https://openapi.lechange.cn/openapi/bindDeviceInfo";
        LOG.info("bindDeviceInfo param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("bindDeviceInfo result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code") != null && LechangeCodeEnum.CODE_0.getCode().equals(jsonObject2.get("code"))) {
            response = JsonUtils.json2obj(JsonUtils.obj2json(jsonObject2.get("data")), DeviceInfoLechangeResponse.class);
        }
        return response;
    }


    @Override
    public List<Map<String, Object>> searchDeviceList(String value, Integer status) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        // 通过乐橙接口,获取指定页pageNo的乐橙设备,pageNo为空则表示获取所有设备
        GetDeviceListByValueParamVo param = new GetDeviceListByValueParamVo()
                .setValue(value)
                .setBrand(0)
                .setStatus(status);
        // 不提供分页
        PagesVo<GetDeviceListResultVo> page = hbmsProxy.getSimpleDeviceList(param);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            //对魔看和乐橙的设备进行分组
            Map<Integer, List<GetDeviceListResultVo>> deviceGroupMap = page.getList().parallelStream().collect(groupingBy(GetDeviceListResultVo::getBrand));
            if (CollectionUtils.isNotEmpty(deviceGroupMap.get(1))) {
                //转换乐橙设备
                Map<String, GetDeviceListResultVo> hbmsMap = deviceGroupMap.get(1).parallelStream().collect(toMap(GetDeviceListResultVo::getCameraSn, getDeviceListResultVo -> getDeviceListResultVo));
                List<JSONObject> deviceList = this.getBindDeviceList(hbmsMap, null);
                // 合并数据
                List<Map<String, Object>> lechengDataList = this.buildList(deviceList, hbmsMap);
                if (CollectionUtils.isNotEmpty(lechengDataList)) {
                    dataList.addAll(lechengDataList);
                }
            }
            //转魔看设备
            if (CollectionUtils.isNotEmpty(deviceGroupMap.get(2))) {
                List<Map<String, Object>> deviceList = deviceGroupMap.get(2).parallelStream().map(getDeviceListResultVo -> {
                    Map<String, Object> result = JsonUtils.json2map(JSONObject.toJSONString(getDeviceListResultVo));
                    result.put("brand", "mokan");
                    //未绑定当离线处理
                    if (getDeviceListResultVo.getCameraStatus() == null) {
                        result.put("status", 0);
                        result.put("name", getDeviceListResultVo.getCameraSn());
                    } else {
                        result.put("status", getDeviceListResultVo.getCameraStatus());
                    }
                    return result;
                }).collect(toList());
                // 合并数据
                dataList.addAll(deviceList);
            }
            this.sort(dataList);
            return dataList;
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> queryDisplayDevices() {
        List<Map<String, Object>> list;
        // 通过乐橙接口,获取指定页pageNo的乐橙设备,pageNo为空则表示获取所有设备
        // 不提供分页
        Map<String, GetDeviceListResultVo> hbmsMap = new HashMap<>();
        for (String deviceId : DeviceConstants.DISPLAY_DEVICE_ID_LIST) {
            GetDeviceListResultVo resultVo = hbmsProxy.getDeviceBySn(deviceId);
            hbmsMap.put(deviceId, resultVo);
        }
        List<JSONObject> deviceList = this.getBindDeviceList(hbmsMap, null);
        list = this.buildList(deviceList, hbmsMap);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public DeviceInfoResponseVo getDeviceInfo(HttpBaseRequest request, String lechangeBaseUrl) {
        HttpUserInfoRequest userInfo = request.getUserInfo();
        if (null == userInfo) {
            throw new BusinessException(HttpResponseCode.ADMIN_ILLEGAL, MessageConstant.USER_NOT_LOGIN);
        }
        // 目前只取订单列表中一条数据
        GetDeviceListParamVo param = new GetDeviceListParamVo();
        param.setStatus(0 + "");
        String mobile = userInfo.getMobile();
        int orderId = 0;
        if (StringUtils.isNotBlank(mobile)) {
            param.setOwnerMobile(mobile);
            //不提供分页
            PagesVo<GetDeviceListResultVo> page = hbmsProxy.getSimpleDeviceList(param);
            if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
                List<GetDeviceListResultVo> list = page.getList();
                for (GetDeviceListResultVo vo : list) {
                    if (StringUtils.isNotBlank(vo.getOrderId())) {
                        orderId = Integer.parseInt(vo.getOrderId());
                        break;
                    }
                }
            }
        }
        if (orderId <= 0) {
            return null;
        }
        // 请求获取设备sn码
        List<GetDeviceListResultVo> data = hbmsProxy.getDeviceByOrderId(Integer.toString(orderId));
        String adminToken = this.queryAccessToken();
        if (null == adminToken || CollectionUtils.isEmpty(data)) {
            throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, "未绑定设备！");
        }

        List<Object> list = new ArrayList<>();
        List<String> deviceIds = new ArrayList<>();
        for (GetDeviceListResultVo getDeviceListResultVo : data) {
            if (StringUtils.isNotBlank(getDeviceListResultVo.getCameraSn())) {
                deviceIds.add(getDeviceListResultVo.getCameraSn());
            }
        }

        if (CollectionUtils.isEmpty(deviceIds)) {
            throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, "未绑定设备！");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", adminToken);
        jsonObject.put("deviceIds", StringUtils.join(deviceIds, ","));
        String combineParam = SignHelper.combineParam(jsonObject.toString());
        //获取多个设备信息
        String url = lechangeBaseUrl + "/bindDeviceList";
        LOG.info("LechangeController bindDeviceList param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("LechangeController bindDeviceList result:{}", sendPost);
        JSONObject resultObject = JSON.parseObject(sendPost);
        JSONObject jsonObject2 = resultObject.getJSONObject("result");
        if (jsonObject2.get("code").equals("0")) {
            JSONArray jsonArray = jsonObject2.getJSONArray("data");
            for (Object o : jsonArray) {
                JSONObject object = (JSONObject) o;
                Map<String, Object> json2map = JsonUtils.json2map(object.toString());
                list.add(json2map);
            }
        }

        DeviceInfoDetailResponseVo detail = new DeviceInfoDetailResponseVo();
        detail.setDevices(list);
        detail.setCount(list.size());
        return new DeviceInfoResponseVo(detail);
    }

    @Override
    public List<Map<String, Object>> getDeviceList(GetDeviceListPageVo getDeviceListPageVo) {
        //不提供分页
        PagesVo<GetDeviceListResultVo> page = hbmsProxy.getDeviceList(getDeviceListPageVo);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            return page.getList().parallelStream().map(getDeviceListResultVo -> {
                Map<String, Object> result = JsonUtils.json2map(JSONObject.toJSONString(getDeviceListResultVo));
                result.put("brand", "mokan");
                //未绑定当离线处理
                if (getDeviceListResultVo.getCameraStatus() == null) {
                    result.put("status", 0);
                    result.put("name", getDeviceListResultVo.getCameraSn());
                } else {
                    result.put("status", getDeviceListResultVo.getCameraStatus());
                }
                return result;
            }).collect(toList());
        }
        return new ArrayList<>();
    }

}

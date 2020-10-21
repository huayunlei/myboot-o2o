package com.ihomefnt.o2o.intf.service.lechange;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.lechange.dto.GetDeviceListPageVo;
import com.ihomefnt.o2o.intf.domain.lechange.dto.GetDeviceListResultVo;
import com.ihomefnt.o2o.intf.domain.lechange.vo.response.DeviceDetailResponse;
import com.ihomefnt.o2o.intf.domain.lechange.vo.response.DeviceInfoLechangeResponse;
import com.ihomefnt.o2o.intf.domain.lechange.vo.response.DeviceInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.lechange.vo.response.WifiInfoResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

public interface LechangeService {

	/**
	 * 查询accessToken
	 * 
	 * 
	 */
	String queryAccessToken();

	/**
	 * 通过boss端接口,获取所有的设备列表,封装成map
	 * 
	 * @return
	 */
	Map<String, GetDeviceListResultVo> getAllDeviceMapByBoss();

	/**
	 * 获取指定页的乐橙设备,为空则表示获取所有设备,效率很低,不推荐使用
	 * 
	 * @param pageNo
	 *            指定页
	 * @return
	 * 
	 */
	List<Map<String, Object>> getAllDeviceList(Integer pageNo, Integer pageSize);

	/**
	 * 通过乐橙接口(status)和boss端(boss侧绑定,companyId)双接口,获取满足条件乐橙设备
	 * 
	 * @param companyId
	 *            所属分公司,为空表示查询全部
	 * @param status
	 *            乐橙状态:0-离线,1-在线 ,为空全部
	 * @return
	 * 
	 */
	List<Map<String, Object>> getDeviceListForBind(Integer companyId, Integer status);

	/**
	 * 通过乐橙接口(样板间分组,status)和boss端(boss侧的空闲),获取满足条件乐橙设备
	 * 
	 * 
	 * @param status
	 *            乐橙状态:0-离线,1-在线 ,为空全部
	 * @return
	 * 
	 */
	List<Map<String, Object>> getDeviceListForYBJ(Integer status);

	/**
	 * 绑定设备
	 * 
	 * @param deviceId
	 * @param code
	 */
	String bindDevice(String deviceId, String code);

	/**
	 * 获取设备翻转状态
	 * 
	 * @param deviceId
	 * @param channelId
	 * @return
	 */
	String frameReverseStatus(String deviceId, String channelId);

	/**
	 * 设置设备翻转状态
	 * 
	 * @param deviceId
	 * @param channelId
	 * @param direction
	 * @return
	 */
	String modifyFrameReverseStatus(String deviceId, String channelId, String direction);

	/**
	 * 修改设备名称
	 * 
	 * @param deviceId
	 * @param name
	 * @return
	 */
	String modifyDeviceName(String deviceId, String name);

	/**
	 * 根据设备ID查询设备详情
	 * 
	 * @param deviceId
	 * @return
	 */
	DeviceDetailResponse queryDeviceInfo(String deviceId);

	/**
	 * 查询周边wifi信息
	 * 
	 * @param deviceId
	 * @return
	 */
	List<WifiInfoResponse> queryAroundWifiList(String deviceId);

	/**
	 * 修改设备连接热点
	 * 
	 * @param deviceId
	 * @param ssid
	 * @param bssid
	 * @param password
	 * @return
	 */
	String modifyDeviceWifi(String deviceId, String ssid, String bssid, String password);

	/**
	 * 解绑设备
	 * 
	 * @param deviceId
	 * @param code
	 */
	String unBindDevice(String deviceId, String code);

	/**
	 * 查询乐橙设备信息
	 * 
	 * @param deviceId
	 */
	DeviceInfoLechangeResponse bindDeviceInfo(String deviceId);

	/**
	 * 搜索设备列表
	 * 
	 * @param deviceId
	 * @return
	 */
	List<Map<String, Object>> searchDeviceList(String deviceId, Integer status);

	/**
	 * 查询大屏展示的9个设备
	 *
	 * @return
	 */
	List<Map<String, Object>> queryDisplayDevices();

	DeviceInfoResponseVo getDeviceInfo(HttpBaseRequest request, String lechangeBaseUrl);

	List<Map<String, Object>> getDeviceList(GetDeviceListPageVo getDeviceListPageVo);
}

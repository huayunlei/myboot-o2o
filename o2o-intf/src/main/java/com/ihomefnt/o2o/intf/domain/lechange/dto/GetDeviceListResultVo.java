package com.ihomefnt.o2o.intf.domain.lechange.dto;

import lombok.Data;

/**
 * 乐橙设备列表
 * @author Charl
 */
@Data
public class GetDeviceListResultVo {
	private String buildingId; //  小区id,
	private String buildingName; //  小区,
	private String cameraSn; //  摄像头sn码,
	private String deviceId; //  设备id,
	private String house; //  楼号,
	private String networkCard; //  无线网卡,
	private String orderId; //  订单id,
	private String orderNum; //  订单编号,
	private String owner; //  业主,
	private String pageNumber; //  页数,
	private String pageSize; //  一页个数,
	private String phone; //  手机号,
	private String routerSn; //  路由sn码,
	private String status; //  设备状态 0:绑定 3 空闲,
	private String projectStatus;//施工状态 0-准备开工 1-开工交底 2-水电验收 3-瓦木验收 4-竣工验收 5施工完成,
	private String wifiName; //  无线网名称,
	private String wifiPassword; //  无线网密码
	private String url; //图片
	private String cityId;//城市id
	private String cityName;//城市
	private Integer companyId ;//分公司id,
	private String  companyName ;// 分公司名称
	private int brand;// 摄像头品牌 1-乐橙 2-摩看
	private String accessToken;//摩看的摄像头token
	private String newSnapshot;//摩看的最新头图
	private String orderStatus;//施工状态
	private Integer cameraStatus; //魔看摄像头在线状态 0:离线 1:在线 3:升级中

}

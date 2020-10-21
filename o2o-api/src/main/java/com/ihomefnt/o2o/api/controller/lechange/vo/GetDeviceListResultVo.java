package com.ihomefnt.o2o.api.controller.lechange.vo;

/**
 * 乐橙设备列表
 * @author Charl
 */
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
	private String status; //  施工状态 0-准备开工 1-开工交底 2-水电验收 3-瓦木验收 4-竣工验收,
	private String wifiName; //  无线网名称,
	private String wifiPassword; //  无线网密码
	
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getCameraSn() {
		return cameraSn;
	}
	public void setCameraSn(String cameraSn) {
		this.cameraSn = cameraSn;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getNetworkCard() {
		return networkCard;
	}
	public void setNetworkCard(String networkCard) {
		this.networkCard = networkCard;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRouterSn() {
		return routerSn;
	}
	public void setRouterSn(String routerSn) {
		this.routerSn = routerSn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWifiName() {
		return wifiName;
	}
	public void setWifiName(String wifiName) {
		this.wifiName = wifiName;
	}
	public String getWifiPassword() {
		return wifiPassword;
	}
	public void setWifiPassword(String wifiPassword) {
		this.wifiPassword = wifiPassword;
	}
	
	
	
}

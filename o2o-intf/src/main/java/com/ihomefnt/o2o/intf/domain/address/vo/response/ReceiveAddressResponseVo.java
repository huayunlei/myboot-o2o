package com.ihomefnt.o2o.intf.domain.address.vo.response;

import java.util.List;

public class ReceiveAddressResponseVo {
	private PCDIdsResponseVo pcdIds;
	private String purchaserName;
	private String purchaserTel;
	private String street;
	private List<ProvinceResponseVo> pList;//省市区列表
	private Long areaId;

	public PCDIdsResponseVo getPcdIds() {
		return pcdIds;
	}

	public void setPcdIds(PCDIdsResponseVo pcdIds) {
		this.pcdIds = pcdIds;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserTel() {
		return purchaserTel;
	}

	public void setPurchaserTel(String purchaserTel) {
		this.purchaserTel = purchaserTel;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public List<ProvinceResponseVo> getpList() {
		return pList;
	}

	public void setpList(List<ProvinceResponseVo> pList) {
		this.pList = pList;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
}

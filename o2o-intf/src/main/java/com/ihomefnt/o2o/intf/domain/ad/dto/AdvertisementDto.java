package com.ihomefnt.o2o.intf.domain.ad.dto;

public class AdvertisementDto {
	private Long id;
	private String name;
	private String description;
	private String imgUrl;
	private String imgUrl2;
	private String rHttpUrl;
	private Integer logon;
	private Integer status;
	private Integer type;
	private Integer position;
	private String fKey;
	private Integer isCity;

	public AdvertisementDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl2() {
		return imgUrl2;
	}

	public void setImgUrl2(String imgUrl2) {
		this.imgUrl2 = imgUrl2;
	}

	public String getrHttpUrl() {
		return rHttpUrl;
	}

	public void setrHttpUrl(String rHttpUrl) {
		this.rHttpUrl = rHttpUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getfKey() {
		return fKey;
	}

	public void setfKey(String fKey) {
		this.fKey = fKey;
	}

	public Integer getLogon() {
		return logon;
	}

	public void setLogon(Integer logon) {
		this.logon = logon;
	}

	public Integer getIsCity() {
		return isCity;
	}

	public void setIsCity(Integer isCity) {
		this.isCity = isCity;
	}

}

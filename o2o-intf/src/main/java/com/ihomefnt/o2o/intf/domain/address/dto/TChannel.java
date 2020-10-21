package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TChannel implements Serializable{

	private static final long serialVersionUID = -1920197118455301255L;
	private Long channelId;
	private String channelCode;
	private String channelName;
	private Integer phase;
    private String telephone;//服务电话
    private Long areaId;
	private String subdomain;
}

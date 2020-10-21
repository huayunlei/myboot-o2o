package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;
import net.sf.json.JSONObject;

import java.io.Serializable;

@Data
public class CityConfigDto implements Serializable{
	
	private static final long serialVersionUID = -7315395920858872765L;
	private Long channelId;
    private String channelName;
    private String channelCode;
    private Integer phase;
    private String content;
    private Long areaId;
    private JSONObject contentObj;
}

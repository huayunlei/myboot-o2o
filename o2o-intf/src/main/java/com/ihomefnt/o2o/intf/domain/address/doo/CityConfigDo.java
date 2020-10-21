package com.ihomefnt.o2o.intf.domain.address.doo;

import lombok.Data;
import net.sf.json.JSONObject;

import java.io.Serializable;

@Data
public class CityConfigDo implements Serializable{
	
	private static final long serialVersionUID = -7315395920858872765L;
	private Long channelId;
    private String channelName;
    private String channelCode;
    private Integer phase;
    private String content;
    private Long areaId;
    private JSONObject contentObj;
}

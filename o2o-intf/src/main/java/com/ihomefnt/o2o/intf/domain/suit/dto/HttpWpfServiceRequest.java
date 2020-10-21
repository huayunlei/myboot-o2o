package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

@Data
public class HttpWpfServiceRequest {
    private String name;    //预约人姓名
    private String mobile;  //手机号码
    private String appointKey;  //预约KEY
    private String activateCode;    //短信验证码
    private String buildingInfo;    //预约楼盘信息
    private Integer appointType;    //预约类型     11  预约装修服务      12   预约免费设计
    private String wpfName; //全品家套装名称
    
}

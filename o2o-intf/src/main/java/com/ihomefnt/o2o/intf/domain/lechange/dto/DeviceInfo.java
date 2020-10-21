package com.ihomefnt.o2o.intf.domain.lechange.dto;

import lombok.Data;

@Data
public class DeviceInfo {

    private String deviceId; //设备id
    private String token; //用户token
    private String livepic; //直播图片
}

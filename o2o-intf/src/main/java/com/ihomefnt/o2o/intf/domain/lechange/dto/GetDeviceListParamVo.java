/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月7日
 * Description:GetDeviceListParamVo.java
 */
package com.ihomefnt.o2o.intf.domain.lechange.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhang
 */
@Data
@Accessors(chain = true)
public class GetDeviceListParamVo {

    private String buildingId;// 小区,
    private String cameraSn;// 摄像头sn码,
    private String orderNum;// 订单编号,


    private String routerSn;// 路由sn码,
    private String status;// 设备状态 0-现有 1-历史绑定 3-空闲设备
    private Integer companyId;//分公司id,
    private String ownerMobile;
    private Integer cameraStatus; //魔看摄像头在线状态 0:离线 1:在线 3:升级中
}

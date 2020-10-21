package com.ihomefnt.o2o.intf.domain.lechange.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: 何佳文
 * @date: 2019-06-21 16:27
 */
@Data
@Accessors(chain = true)
public class GetDeviceListPageVo {
    private Integer buildingId;// 小区,
    private String cameraSn;// 摄像头sn码,
    private String orderNum;// 订单编号,
    private String routerSn;// 路由sn码,
    private Integer status;// 设备状态 0-现有 1-历史绑定 3-空闲设备
    private Integer companyId;//分公司id,
    private Integer pageNo; //页码
    private Integer pageSize; //分页条数
    private Integer brand;
    private Integer cameraStatus; //魔看摄像头在线状态 0:离线 1:在线 3:升级中
}

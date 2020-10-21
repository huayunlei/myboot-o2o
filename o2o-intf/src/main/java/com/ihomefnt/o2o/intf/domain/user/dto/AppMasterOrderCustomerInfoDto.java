package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

/**
 * 主订单客户信息
 *
 * @author liyonggang
 * @create 2019-02-22 2001
 */
@Data
public class AppMasterOrderCustomerInfoDto {


    private Integer userId;// 用户id,

    private String userName;// 用户姓名,

    private String userMobile;// 用户手机号,

    private Integer provinceCode;// 省code,

    private String provinceName;// 省名称,

    private Integer cityCode;// 市code,

    private String cityName;// 市名称,

    private Integer receiverAreaId;// 收货地址区域id,

    private String areaName;// 区名称,

    private String receiverAddressDetail;// 收货详细地址,

    private String buildingNo;// 楼栋号,

    private String houseNo;// 房号
}

package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

/**
 * 房屋位置信息
 * @author ZHAO
 */
@Data
public class CustomerHouseAddressInfo {
    private Integer fidProvince;//省id

    private String provinceName;//省名称

    private Integer fidCity;//市id

    private String cityName;//市名称

    private Integer fidArea;//区id

    private String areaName;//区名称

    private String houseArea;//小区名称

    private String houseNo;//楼栋号

    private String roomNo;//房间号

    private String address;//全地址

}

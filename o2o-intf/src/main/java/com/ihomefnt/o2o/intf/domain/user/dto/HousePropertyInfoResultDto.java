package com.ihomefnt.o2o.intf.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 房产信息
 *
 * @author liyonggang
 * @create 2019-02-22 19:52
 */
@Data
public class HousePropertyInfoResultDto {

    private Integer buildingId;//(, optional): 项目（楼盘）ID,

    private String buildingName;// 项目（楼盘）名称,

    private Integer zoneId;// 分区id,

    private String partitionName;// 分区名称,

    private String buildingType;// 项目类型,

    private Integer layoutId;// 户型ID,

    private String layoutName;// 户型名称,

    private Integer layoutRoom;// 室,

    private Integer layoutLiving;// 厅,

    private Integer layoutKitchen;// 厨房,

    private Integer layoutToilet;// 卫,

    private Integer layoutStorage;// 储藏室,

    private Integer layoutCloak;// 衣帽间,

    private Integer layoutBalcony;// 阳台,

    private String housingNum;// 楼号,

    private String unitNum;// 单元号,

    private String roomNum;// 房号,

    private Integer adviser;// 置家顾问,

    private String adviserName;// 置家顾问姓名,

    private String adviserMobile;// 置家顾问手机号,

    private String layoutInfo;// 户型信息,

    private String housePropertyInfo;// 房产信息,

    private Integer customerHouseId;// 房产id,

    private Integer userId;// 用户id,

    private String area;// 房屋面积,

    private Integer companyId;// 所属公司ID,

    private String companyName;// 所属公司名称,

    private Integer agentId;// 经纪人id,

    private String agentName;// 经纪人姓名,

    private String deliverTime;// 交房时间,

    private String customerName;// 客户姓名,

    private String shortLayoutInfo;// 简单户型信息,

    private String shortHousePropertyInfo;// 简单房产信息,

    private Integer fidDistrict;// 区ID,

    private String buildingInfo;// 手工输入的房产信息

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date createTime;// 房产信息创建时间

    @ApiModelProperty("户型id")
    private Long apartmentId;

    @ApiModelProperty("户型版本")
    private Long apartmentVersion;

    @ApiModelProperty("是否是拆改方案 0 不是 1 是")
    private Integer reformFlag;

}

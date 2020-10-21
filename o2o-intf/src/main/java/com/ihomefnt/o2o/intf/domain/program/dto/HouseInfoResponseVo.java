package com.ihomefnt.o2o.intf.domain.program.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 房产信息
 *
 * @author ZHAO
 */
@ApiModel("房产信息")
@Data
public class HouseInfoResponseVo {
    @ApiModelProperty("房产ID")
    private Integer id;//房产ID

    @ApiModelProperty("省ID")
    private Long provinceId;//省ID

    @ApiModelProperty("省名称")
    private String provinceName;//省名称

    @ApiModelProperty("市id")
    private Long cityId; //市id

    @ApiModelProperty("市区名称")
    private String cityName;//市区名称

    @ApiModelProperty("区id")
    private Long areaId;//区id

    @ApiModelProperty("区名称")
    private String areaName;//区名称

    @ApiModelProperty("楼盘id")
    private Integer houseProjectId;//楼盘id

    @ApiModelProperty("楼盘名称")
    private String houseProjectName;//楼盘名称

    @ApiModelProperty("分区id")
    private Integer zoneId;//分区id

    @ApiModelProperty("分区名称")
    private String partitionName;//分区名称

    @ApiModelProperty("户型id")
    private Integer houseTypeId;//户型id

    @ApiModelProperty("户型")
    private String houseTypeName;//户型

    @ApiModelProperty("户型版本号")
    private Long apartmentVersion;

    @ApiModelProperty("是否是拆改方案 0 不是 1 是")
    private Integer reformFlag;

    @ApiModelProperty("格局id")
    private Integer layoutId;//格局id

    @ApiModelProperty("户型")
    private Integer layoutName;//格局id

    @ApiModelProperty("几室")
    private Integer layoutRoom;//几室

    @ApiModelProperty("几厅")
    private Integer layoutLiving;//几厅

    @ApiModelProperty("几厨")
    private Integer layoutKitchen;//几厨

    @ApiModelProperty("几卫")
    private Integer layoutToliet;//几卫

    @ApiModelProperty("几阳台")
    private Integer layoutBalcony;//几阳台

    @ApiModelProperty("几储物间")
    private Integer layoutStorage;//几储物间

    @ApiModelProperty("几衣帽间")
    private Integer layoutCloak;//几衣帽间

    @ApiModelProperty("详细地址")
    private String houseArea;//详细地址

    @ApiModelProperty("楼栋号")
    private String housingNum;//楼栋号

    @ApiModelProperty("单元号")
    private String unitNum;//单元号

    @ApiModelProperty("房间号")
    private String roomNum;//房间号

    @ApiModelProperty("面积")
    private String size;//面积

    @ApiModelProperty("全地址")
    private String address;//全地址

    @ApiModelProperty("是否有资质：1、没有资质 2、只能看（条件不符、存在退款申请） 3、可以下单")
    private Integer isAvail;// 是否有资质：1、没有资质 2、只能看（条件不符、存在退款申请） 3、可以下单

    @ApiModelProperty("大订单ID")
    private Integer masterOrderId;//大订单ID

    @ApiModelProperty("大订单状态")
    private Integer masterOrderStatus;//大订单状态

    @ApiModelProperty("是否有资质原因")
    private String msg;//是否有资质原因

    @ApiModelProperty("code：9可下单  10存在退款")
    private Integer code;//code：9可下单  10存在退款

    @ApiModelProperty("订单来源：6代客下单")
    private Integer source;//订单来源：6代客下单

    @ApiModelProperty("交房日期")
    private String deliverTime; //交房日期

    @ApiModelProperty("房产信息")
    private String buildingInfo;//房产信息

    @ApiModelProperty("房产信息创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("客户姓名")
    private String customerName;//客户姓名

    @ApiModelProperty("空间标识图")
    private String layoutImgUrl;//空间标识图

    @ApiModelProperty("风格意向集合")
    private List<String> styleList;//风格意向集合

    @ApiModelProperty("户型图")
    private String normalLayoutPic;//户型图
}

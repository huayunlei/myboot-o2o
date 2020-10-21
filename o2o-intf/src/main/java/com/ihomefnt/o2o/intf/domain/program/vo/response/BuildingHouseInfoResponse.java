package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homebuild.vo.response.HouseAddEditCountResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 房产信息
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
@ApiModel("房产信息")
public class BuildingHouseInfoResponse extends HouseAddEditCountResponse {
    @ApiModelProperty("房产ID 已废弃 新版本使用customerHouseId")
    @Deprecated
    private Integer houseId;//房产ID
    @ApiModelProperty("省份ID")
    private Long provinceId;//省份ID
    @ApiModelProperty("省份名称")
    private String provinceName;//省份名称
    @ApiModelProperty("城市ID")
    private Long cityId;//城市ID
    @ApiModelProperty("城市名称")
    private String cityName;//城市名称
    @ApiModelProperty("楼盘项目ID")
    private Integer buildingId;//楼盘项目ID
    @ApiModelProperty("楼盘项目名称")
    private String buildingName;//楼盘项目名称
    @ApiModelProperty("分区id")
    private Integer zoneId;//分区id
    @ApiModelProperty("分区名称")
    private String zoneName;//分区名称
    @ApiModelProperty("户型ID")
    private Integer houseTypeId;//户型ID
    @ApiModelProperty("户型名称")
    private String houseTypeName;//户型名称
    @ApiModelProperty("面积")
    private String size;//面积
    @ApiModelProperty("格局")
    private String layout;//格局
    @ApiModelProperty("户型图")
    private String imgUrl;//户型图
    @ApiModelProperty("交房日期")
    private String handoverDate;//交房日期
    @ApiModelProperty("楼号")
    private String buildingNo;//楼号
    @ApiModelProperty("单元号")
    private String unitNo;//单元号
    @ApiModelProperty("房号")
    private String roomNo;//房号
    @ApiModelProperty("风格偏好ID集合")
    private List<String> styleIds;//风格偏好ID集合
    @ApiModelProperty("大订单ID")
    private Integer orderId;//大订单ID
    @ApiModelProperty("大订单状态")
    private Integer orderStatus;//大订单状态
    @ApiModelProperty("大订单状态")
    private String orderStatusDesc;//大订单状态
    @ApiModelProperty("客户姓名")
    private String customerName;//客户姓名
    @ApiModelProperty("手工输入的房产信息")
    private String buildingInfo;
    @ApiModelProperty("几室")
    private Integer layoutRoom;//几室
    @ApiModelProperty("是否有设计中的设计任务 0：无，1：有")
    private Integer hasUnderDesignDesignTask = 0;
    @ApiModelProperty("客户房产id")
    private Integer customerHouseId;
    //订单子状态
    private Integer orderSubStatus;
    @ApiModelProperty("用户id")
    private Integer userId;
}

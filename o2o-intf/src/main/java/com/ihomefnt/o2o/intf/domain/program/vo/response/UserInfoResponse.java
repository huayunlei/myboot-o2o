package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author ZHAO
 */
@ApiModel("用户信息")
@Data
public class UserInfoResponse implements Serializable {

    @ApiModelProperty("用户ID")
    private Integer userId;//用户ID

    @ApiModelProperty("用户名称")
    private String userName;//用户名称

    @ApiModelProperty("手机号码")
    private String mobileNum;//手机号码

    @ApiModelProperty("手机号影藏中间4位")
    private String hideMobileNum;//手机号影藏中间4位

    @ApiModelProperty("楼盘地址")
    private String buildingAddress;//楼盘地址

    @ApiModelProperty("维修地址")
    private String maintainAddress;//维修地址

    @ApiModelProperty("楼盘名称")
    private String buildingName;//楼盘名称

    @ApiModelProperty("户型名称")
    private String houseName;//户型名称

    @ApiModelProperty("户型格局")
    private String housePattern;//户型格局

    @ApiModelProperty("户型面积")
    private String houseArea;//户型面积

    @ApiModelProperty("户型全称")
    private String houseFullName;//户型全称

    @ApiModelProperty("已收金额")
    private String amount;//已收金额

    @ApiModelProperty("收款时间")
    private String receiptTime;//收款时间

    @ApiModelProperty("置家顾问电话")
    private String adviserMobileNum;//置家顾问电话

    @ApiModelProperty("置家顾问")
    private String adviserMobileName;//置家顾问

    @ApiModelProperty("楼盘ID")
    private Integer buildingId;//楼盘ID

    @ApiModelProperty("公司id 7艾佳总部,8南京公司,9河南公司,10北京公司")
    private String companyCode;//公司id 7艾佳总部,8南京公司,9河南公司,10北京公司

    @ApiModelProperty("房产ID 已废弃，使用customerHouseId")
    @Deprecated
    private Integer houseId;//房产ID

    @ApiModelProperty("户型ID")
    private Integer houseTypeId;//户型ID

    @ApiModelProperty("户型版本号")
    private Long apartmentVersion;

    @ApiModelProperty("是否是拆改方案 0 不是 1 是")
    private Integer reformFlag;

    @ApiModelProperty("户型id")
    private Long layoutId;

    @ApiModelProperty("大订单ID")
    private Integer masterOrderId;//大订单ID

    @ApiModelProperty("订单状态")
    private Integer masterOrderState;//订单状态

    @ApiModelProperty("房产id")
    private Integer customerHouseId;
}

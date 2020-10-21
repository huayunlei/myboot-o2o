package com.ihomefnt.o2o.intf.domain.user.vo;

import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ContractInfoResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 个人中心中间页面数据公共Vo
 *
 * @author liyonggang
 * @create 2019-02-28 14:30
 */
@Data
@ApiModel("个人中心中间页面数据公共Vo")
public class PersonalCenterMiddleInfoVo {

    @Deprecated
    @ApiModelProperty("房产id 已废弃 新版本使用 customerHouseId")
    private Integer houseId;//房产id

    @ApiModelProperty("房产id")
    private Integer customerHouseId;

    @ApiModelProperty("订单id")
    private Integer orderId;//订单id

    @ApiModelProperty("楼盘名称")
    private String buildingName;//楼盘名称

    @ApiModelProperty("楼栋号")
    private String housingNum;//楼栋号

    @ApiModelProperty("单元号")
    private String unitNum;//单元号

    @ApiModelProperty("房间号")
    private String roomNum;//房间号

    @ApiModelProperty("面积")
    private String size;//面积

    @ApiModelProperty("户型")
    private String houseTypeName;//户型

    @ApiModelProperty("跳转按钮文案")
    private String clickCopyWriter;//跳转按钮文案

    @ApiModelProperty("权益版本号")
    private Integer rightsVersion;

    @ApiModelProperty("合同列表")
    private List<ContractInfoResponse> contractList;

    @ApiModelProperty("房产信息")
    private HouseInfoResponseVo houseInfo;

    @ApiModelProperty("是否已开工")
    private Boolean startWork = Boolean.FALSE;

    @ApiModelProperty("售卖类型：0：全品家（软+硬） 1：全品家（软）")
    private Integer orderSaleType;
}

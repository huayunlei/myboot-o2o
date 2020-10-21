package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户房产信息
 *
 * @author ZHAO
 */
@Data
public class HouseResponse {
    private String buildingAddress;//楼盘地址：XX省XX市楼盘项目名

    private String roomAddress;//楼栋号房号

    private String area;// 面积

    private String houseType;//户型

    private String pattern;//格局

    private Integer orderId;//订单ID

    private Integer orderStatus;//订单状态

    private Integer orderSource;//订单来源：6代客下单

    private Integer houseId;//房产ID

    @ApiModelProperty("客户房产id")
    private Integer customerHouseId;

    private String customerName;//客户姓名

    private String handoverDate;//交房日期

    private String houseImage;//户型图

    private Boolean needComplete;//是否需要完善

    private Integer houseTypeId;//户型id

    private String buildingName;//项目名

    private Integer zoneId;//分区ID

    private Integer buildingId;//楼盘ID

    private String buildingInfo;//房产信息
    
    private Integer layoutId;//格局id

    @ApiModelProperty("订单子状态")
    private Integer orderSubStatus;
}

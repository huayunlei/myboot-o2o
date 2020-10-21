package com.ihomefnt.o2o.intf.domain.homecard.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel("项目户型空间详情返回Vo(新)")
@Data
public class HouseRoomVo implements Serializable{

    /**
     *户型空间id
     */
    @ApiModelProperty("户型空间id")
    private Long roomId;

    /**
     *户型id
     */
    @ApiModelProperty("户型id")
    private Long houseId;


    /**
     *空间标识
     */
    @ApiModelProperty("空间标识")
    private Integer usageId;

    /**
     *空间标识名
     */
    @ApiModelProperty("空间标识名称")
    private String usageName;

    /**
     *面积
     */
    @ApiModelProperty("面积")
    private BigDecimal area;

    /**
     *开间
     */
    @ApiModelProperty("开间")
    private BigDecimal longth;

    /**
     *进深
     */
    @ApiModelProperty("进深")
    private BigDecimal width;

    /**
     *层高
     */
    @ApiModelProperty("层高")
    private BigDecimal height;

    /**
     * 顺序
     */
    @ApiModelProperty("顺序")
    private Integer sequenceNum;

}

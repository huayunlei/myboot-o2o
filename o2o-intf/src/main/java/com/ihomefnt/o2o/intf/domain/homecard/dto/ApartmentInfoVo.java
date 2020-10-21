package com.ihomefnt.o2o.intf.domain.homecard.dto;

import com.ihomefnt.o2o.intf.domain.homecard.vo.response.ApartmentRoomVo;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.HouseRoomVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("项目户型空间详情")
@Data
public class ApartmentInfoVo {

    @ApiModelProperty("户型空间信息")
    private List<HouseRoomVo> houseRoomVos;

    @ApiModelProperty("是否有DR户型点位信息 0:无 1:有")
    @Deprecated
    private Integer hasHouseExt;

    @ApiModelProperty("户型ID最新")
    private Integer houseId;

    @ApiModelProperty("户型绘制是否已提交，true:已提交 false:未提交")
    private Boolean committed;
}

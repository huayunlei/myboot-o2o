package com.ihomefnt.o2o.intf.domain.controlcap.vo.response;

import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 户型方案列表
 *
 * @author liyonggang
 * @create 2018-11-28 13:39
 */
@Data
@ApiModel("户型方案列表数据")
public class ProgramListForHouseTypeResponseVo {
    @ApiModelProperty("户型id")
    private Integer houseTypeId;//户型ID
    @ApiModelProperty("户型名称")
    private Integer buildingId;//楼盘ID
    @ApiModelProperty("/楼盘名称")
    private String buildingName;//楼盘名称
    @ApiModelProperty("户型名称")
    private String houseTypeName;//户型名称
    @ApiModelProperty("分区ID")
    private Integer zoneId;
    List<ProgramResponse> programList;

}

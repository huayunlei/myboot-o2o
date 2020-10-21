package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ftp户型信息
 * @author wanyunxin
 * @create 2020-02-13 15:02
 */
@Data
public class HtpHouseTypeResponse {

    @ApiModelProperty("户型id")
    private Integer houseId;

    @ApiModelProperty("是否拆改户型 0:否 1:是")
    private Integer isReform;

    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("户型名称")
    private String houseName;

    @ApiModelProperty("户型类型：0: 普通户型，1: DNA户型")
    private Integer type;

    @ApiModelProperty("面积")
    private Integer area;
}

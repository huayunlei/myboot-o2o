package com.ihomefnt.o2o.intf.domain.htp.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("根据户型id查询户型扩展信息入参")
public class GetExtByHouseIdRequestVo {

    @ApiModelProperty("户型ID")
    private Integer houseId;

}

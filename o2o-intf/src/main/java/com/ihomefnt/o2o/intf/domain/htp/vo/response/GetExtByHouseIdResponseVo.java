package com.ihomefnt.o2o.intf.domain.htp.vo.response;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("根据户型id查询户型扩展信息返回")
public class GetExtByHouseIdResponseVo {

    @ApiModelProperty("户型描述信息")
    private JSONObject houseDescription;

    @ApiModelProperty("户型ID")
    private Integer houseId;

    @ApiModelProperty("house文件路径")
    private String houseUrl;

}

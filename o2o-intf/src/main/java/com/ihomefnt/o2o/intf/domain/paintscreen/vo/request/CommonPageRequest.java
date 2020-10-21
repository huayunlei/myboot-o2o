package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("基础查询信息")
public class CommonPageRequest extends HttpBaseRequest {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("页码")
    private Integer pageNo = 1;
    @ApiModelProperty("页长")
    private Integer pageSize = 20;
}

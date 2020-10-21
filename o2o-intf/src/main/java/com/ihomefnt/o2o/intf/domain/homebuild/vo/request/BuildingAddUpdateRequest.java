package com.ihomefnt.o2o.intf.domain.homebuild.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("新增编辑房产请求参数")
public class BuildingAddUpdateRequest extends HttpBaseRequest {

    @ApiModelProperty("客户姓名")
    private String customerName;

    @Deprecated
    @ApiModelProperty("房产ID")
    private Integer houseId;

    @ApiModelProperty("楼盘项目ID")
    private Integer buildingId;

    @ApiModelProperty("分区ID")
    private Integer zoneId;

    @ApiModelProperty("户型ID")
    private Integer houseTypeId;

    @ApiModelProperty("交房日期")
    private String handoverDate;

    @ApiModelProperty("楼号")
    private String buildingNo;

    @ApiModelProperty("单元号")
    private String unitNo;

    @ApiModelProperty("房号")
    private String roomNo;

    @ApiModelProperty("风格偏好ID集合")
    private List<Integer> styleIds;

    @ApiModelProperty("手工输入的房产信息")
    private String buildingInfo = "";

    @ApiModelProperty("客户房产id")
    private Integer customerHouseId;
}

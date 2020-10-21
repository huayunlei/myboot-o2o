package com.ihomefnt.o2o.intf.domain.homebuild.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Charl
 */
@Data
@ApiModel("分区列表")
public class PartitionVo {

    @ApiModelProperty("分区ID")
    private Long id;

    @ApiModelProperty("所属项目id")
    private Long projectId;

    @ApiModelProperty("分区名称")
    private String partitionName;

    @ApiModelProperty("楼栋数量")
    private Integer buildingAmount;

}

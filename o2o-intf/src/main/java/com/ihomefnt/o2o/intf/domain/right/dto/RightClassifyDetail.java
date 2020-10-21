package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("权益列表区块")
public class RightClassifyDetail {

    @ApiModelProperty("权益标题")
    private String cassificationName;

    @ApiModelProperty("权益标题")
    private String total;


    @ApiModelProperty("各项权益下的详细内容")
    private List<RigthtsItemBaseInfo> classifyDetailList;
}

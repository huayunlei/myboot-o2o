package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("图片信息")
@Data
public class PicRecordDetailVo {

    @ApiModelProperty("图片")
    private List<String> pics;

    @ApiModelProperty("验收项id")
    private String itemId;

    @ApiModelProperty("专项描述")
    private String itemDesc;
}

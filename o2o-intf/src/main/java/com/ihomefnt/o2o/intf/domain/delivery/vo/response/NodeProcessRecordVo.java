package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("验收记录")
@Data
public class NodeProcessRecordVo {

    @ApiModelProperty("记录类型 1-节点验收 2-问题验收")
    private Integer recordType;

    @ApiModelProperty("传图日期")
    private String urlDate;

    @ApiModelProperty("图片性质")
    private String picOperationDesc;

    @ApiModelProperty("节点状态文案")
    private String nodeStatusDesc;

    @ApiModelProperty("整改意见")
    private String reformOpinion;

    @ApiModelProperty("上传的图片")
    private List<PicRecordDetailVo> picRecordDetailVos;

    @ApiModelProperty("操作人 1-项目经理 2-艾管家 3-客户")
    private Integer userType;
}

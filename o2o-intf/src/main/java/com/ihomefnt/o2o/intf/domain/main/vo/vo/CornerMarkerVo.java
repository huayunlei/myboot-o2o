package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("信息图片角标")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CornerMarkerVo{

    public CornerMarkerVo(String location, String copywriting) {
        this.location = location;
        this.copywriting = copywriting;
    }
    @ApiModelProperty("角标状态名称")
    private String cornerMarkerName = "";

    @ApiModelProperty("角标位置：leftTop/rightTop/leftBottom/rightBottom")
    private String location;

    @ApiModelProperty("角标文案颜色")
    private String copywritingColor;

    @ApiModelProperty("角标背景颜色")
    private String backgroundColor;

    @ApiModelProperty("角标文案")
    private String copywriting;
}

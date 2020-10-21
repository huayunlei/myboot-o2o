package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-11-06 15:55
 */
@Data
@ApiModel("方案列表底部展示信息")
public class SolutionBottomVo {

    @ApiModelProperty("底部文案信息")
    private String textInfo;
    @ApiModelProperty("底部跳转文案信息")
    private String jumpTextInfo;
    @ApiModelProperty("跳转地址")
    private String url;
}

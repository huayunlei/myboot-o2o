package com.ihomefnt.o2o.intf.domain.vote.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanyunxin
 * @create 2019-10-18 15:13
 */
@Data
@ApiModel("分析结果返回数据")
public class AnalysisResultResponse {

    @ApiModelProperty("家庭地位")
    private String familyPlace;

    @ApiModelProperty("家庭地位图片")
    private String familyPlaceImg;

    @ApiModelProperty("家庭地位评价")
    private String familyPlaceInfo;

    @ApiModelProperty("家庭地位背景色")
    private String familyBgColor;
}

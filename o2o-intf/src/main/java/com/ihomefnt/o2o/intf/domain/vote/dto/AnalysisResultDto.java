package com.ihomefnt.o2o.intf.domain.vote.dto;

import com.ihomefnt.o2o.intf.domain.vote.vo.response.AnalysisResultResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-10-18 16:41
 */
@NoArgsConstructor
@Data
@ApiModel("性格色彩分析结果")
public class AnalysisResultDto {

    @ApiModelProperty("性格色彩结果")
    private String color;

    @ApiModelProperty("性格色彩结果信息")
    private List<AnalysisResultResponse> familyPlaceList;

}

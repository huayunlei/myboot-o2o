package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/23 0023.
 */
@Data
public class RecommendUserResultDto {

    private Integer  luckyDraw; //是否抽奖 0-否 1-是

    private Integer residueDegree;// 剩余次数

    @ApiModelProperty("经纪人手机号")
    private String mobile;
}

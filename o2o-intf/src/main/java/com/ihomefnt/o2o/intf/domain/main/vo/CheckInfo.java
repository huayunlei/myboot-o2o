package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("验收信息")
@Data
@Accessors(chain = true)
public class CheckInfo {

    @ApiModelProperty(value = "客户最终验收状态 0-未验收；1-不通过；2-已通过，默认未验收")
    private Integer ownerCheckStatus = 0;

    @ApiModelProperty("是否已点评")
    private Boolean isCommented;

    @ApiModelProperty("评价内容")
    private String evaluationContent;

    @ApiModelProperty("评价星数")
    private Integer evaluationStars;
}

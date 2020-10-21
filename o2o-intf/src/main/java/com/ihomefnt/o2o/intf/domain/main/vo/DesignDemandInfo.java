package com.ihomefnt.o2o.intf.domain.main.vo;

import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.PersonalDesignResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("设计需求信息")
@Data
@Accessors(chain = true)
public class DesignDemandInfo {

    @ApiModelProperty("风格问题提交记录ID")
    private Integer commitRecordId;

    @ApiModelProperty("最新任务状态")
    private Integer taskStatus;

    @ApiModelProperty("最新任务状态描述")
    private String taskStatusStr;

    @ApiModelProperty("有效设计任务数量")
    private Integer designDemandCount;

    @ApiModelProperty("最新一条设计需求详情")
    private PersonalDesignResponse designResponse;

}

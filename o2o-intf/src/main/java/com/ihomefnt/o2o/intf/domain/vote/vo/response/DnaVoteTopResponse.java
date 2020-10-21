package com.ihomefnt.o2o.intf.domain.vote.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 投票列表页数据
 *
 * @author liyonggang
 * @create 2019-05-06 16:35
 */
@Data
@ApiModel("投票顶部数据")
public class DnaVoteTopResponse {

    @ApiModelProperty("总投票数量")
    private Integer voteTotalCount;

    @ApiModelProperty("总作品数量")
    private Integer dnaCount;

    @ApiModelProperty("活动开始时间")
    private String startTime;

    @ApiModelProperty("活动结束时间")
    private String endTime;
}

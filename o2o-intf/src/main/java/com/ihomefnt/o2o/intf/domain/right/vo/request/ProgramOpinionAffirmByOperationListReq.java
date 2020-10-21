package com.ihomefnt.o2o.intf.domain.right.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-05 13:57
 */
@Data
@ApiModel("运营确认方案意见参数 list")
@Accessors(chain = true)
public class ProgramOpinionAffirmByOperationListReq {

    @ApiModelProperty("确认方案意见明细")
    private List<ProgramOpinionAffirmByOperationItemReq> items;

}

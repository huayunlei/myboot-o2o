package com.ihomefnt.o2o.intf.domain.right.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-05 13:47
 */
@Data
@ApiModel("运营确认方案意见参数 item")
@Accessors(chain = true)
public class ProgramOpinionAffirmByOperationItemReq {


    @ApiModelProperty("方案意见草稿ID")
    private String programOpinionId;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("手机号码")
    private String mobile; // 手机号码

}

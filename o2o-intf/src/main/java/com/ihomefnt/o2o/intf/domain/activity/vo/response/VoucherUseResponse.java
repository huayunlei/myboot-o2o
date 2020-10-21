package com.ihomefnt.o2o.intf.domain.activity.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/11/26
 */
@Data
@ApiModel("查询商品领券用券信息")
public class VoucherUseResponse {

    @ApiModelProperty("可操作类型：1可领，2可用")
    private Integer operate;

    @ApiModelProperty("券信息")
    private GetVoucherResponse voucherResponse;
}

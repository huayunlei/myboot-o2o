package com.ihomefnt.o2o.intf.domain.kuaidi100.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 查询快递信息入参
 *
 * @author liyonggang
 * @create 2019-01-25 15:16
 */
@ApiModel("查询快递信息")
@Data
public class QueryKuaidi100RequestVo {
    @ApiModelProperty("运输公司编码")
    @NotNull(message = "运输公司编码不能为空")
    private String logisticCompanyCode; //物流公司编码

    @ApiModelProperty("快递单号")
    @NotNull(message = "快递单号")
    private String logisticNum; //物流单号

    @ApiModelProperty("系统订单id，和系统订单号二选一")
    private Integer orderId;

    @ApiModelProperty("系统订单号，和系统订单id二选一")
    private String orderNum;
}

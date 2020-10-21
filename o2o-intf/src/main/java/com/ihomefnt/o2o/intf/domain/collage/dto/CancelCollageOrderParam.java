package com.ihomefnt.o2o.intf.domain.collage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jerfan cang
 * @date 2018/10/17 20:25
 */
@Data
@NoArgsConstructor
@ApiModel("CancelCollageOrderParam")
public class CancelCollageOrderParam {
    @ApiModelProperty("订单号 -必传")
    private Integer orderId;

    @ApiModelProperty("操作人员id")
    private Integer operator;

    @ApiModelProperty("操作人员姓名")
    private String operatorName;

    @ApiModelProperty("取消备注")
    private String remark;

    @ApiModelProperty("调用来源 拼团传7")
    private String  source;

    @ApiModelProperty("操作人手机号 必传-通收货人手机号")
    private String operatorMobile;


    public CancelCollageOrderParam(Integer orderId, String remark, String source, String operatorMobile) {
        this.orderId = orderId;
        this.remark = remark;
        this.source = source;
        this.operatorMobile = operatorMobile;
    }
}

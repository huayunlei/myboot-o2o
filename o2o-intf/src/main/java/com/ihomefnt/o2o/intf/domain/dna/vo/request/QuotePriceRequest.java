package com.ihomefnt.o2o.intf.domain.dna.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wanyunxin
 * @create 2019-11-18 10:23
 */
@Data
@ApiModel("获取装修报价入参")
public class QuotePriceRequest extends HttpBaseRequest {

    @ApiModelProperty("面积")
    private BigDecimal area;

    @ApiModelProperty("室")
    private Integer chamber;

    @ApiModelProperty("厅")
    private Integer hall;

    @ApiModelProperty("卫")
    private Integer toilet;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    private Integer userId;

}

package com.ihomefnt.o2o.intf.domain.dna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wanyunxin
 * @create 2019-11-22 17:44
 */
@Data
public class OrderTimeDto {

    @ApiModelProperty("订单创建时间")
    private String createTime;

    @ApiModelProperty("首次交款")
    private String paySureMoneyTime;

    @ApiModelProperty("提交签约时间")
    private String commitSignTime;

    @ApiModelProperty("付清全款时间")
    private String payFullMoneyTime;

    @ApiModelProperty("方案名称")
    private String solutionName;
}

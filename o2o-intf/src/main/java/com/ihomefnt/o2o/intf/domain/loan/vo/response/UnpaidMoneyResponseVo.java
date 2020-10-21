package com.ihomefnt.o2o.intf.domain.loan.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 订单款项简单信息
 *
 * @author liyonggang
 * @create 2019-03-01 16:00
 */
@ApiModel
@Data
@Accessors(chain = true)
public class UnpaidMoneyResponseVo {

    @ApiModelProperty("未付金额")
    private BigDecimal unpaidMoney;

    @ApiModelProperty("置家顾问手机号")
    private String homeAdviserMobile;

    @ApiModelProperty("订单状态,1:接触阶段,2:意向阶段,3:定金阶段,4:签约阶段,5:交付中,6:已完成,7:已取消")
    private Integer orderStatus;

}

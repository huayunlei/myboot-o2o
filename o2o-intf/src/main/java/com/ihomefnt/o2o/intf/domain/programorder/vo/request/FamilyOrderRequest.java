package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.loan.vo.request.CreateLoanRequestVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 创建订单请求输入
 * 
 * @author wanyunxin
 */
@ApiModel("下单入参数")
@Data
public class FamilyOrderRequest extends CreateLoanRequestVo {

    @ApiModelProperty(value = "全品家订单id")
    private Integer orderId;


    @ApiModelProperty(value = "草稿id")
    private String draftId;


    @ApiModelProperty("操作类型 1 新建订单 2 更新订单")
    private Integer type = 1;

    @ApiModelProperty("房产id")
    @Deprecated
    private Integer houseId;

    @ApiModelProperty("房产id")
    private Integer customerHouseId;

    @ApiModelProperty("草稿可见当前价格")
    private BigDecimal draftLatestTotalPrice;

}

package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/9/6
 */
@Data
public class StyleRecordResponse {

    private List<PersonalDesignResponse> styleRecordList;

    @ApiModelProperty("是否交清全款 0否 1是 2超过应付金额")
    private Integer allMoney = 0;

    @ApiModelProperty("是否是艾佳贷签约")
    private Boolean isLoan = false;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单子状态")
    private Integer orderSubStatus;

}

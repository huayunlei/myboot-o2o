package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-04-23 15:21
 */
@ApiModel("查询订单预算价格与合同模板返回")
@Data
public class QueryPreSignResponse {

    @ApiModelProperty("订单金额")
    private BigDecimal contractAmount;

    @ApiModelProperty("合同模板地址")
    private String serviceContractUrl;

    @ApiModelProperty("合同模板名称")
    private String serviceContractName;

    @ApiModelProperty("提交签约说明")
    private List<String> preSignInfoList;

    @ApiModelProperty("是否交清全款 0否 1是 2超过应付金额")
    private Integer allMoney = 0;
}

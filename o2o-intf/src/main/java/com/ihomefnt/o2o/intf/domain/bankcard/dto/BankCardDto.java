package com.ihomefnt.o2o.intf.domain.bankcard.dto;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * Created by Administrator on 2018/11/2 0002.
 */
@Data
public class BankCardDto {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("总行名称")
    private String headBankName;

    @ApiModelProperty("银行卡号")
    private String bankCardNumber;
    
	private String name;
}

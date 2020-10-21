package com.ihomefnt.o2o.intf.domain.bankcard.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/11/6 0006.
 */
@Data
public class BankCheckResultDto {

    private boolean result;

    private String msg;

    private String bankName;

    private boolean creditCard;

    private Integer code;
}

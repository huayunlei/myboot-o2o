package com.ihomefnt.o2o.intf.domain.coupon.doo;

import lombok.Data;

@Data
public class CashAccountDo {
    private String userName;
    private String userPhone;
    private double totalMoney;  //现金金额
    private double usableMoney;  //可用金额
    private double freezeMoney;  //冻结金额
}

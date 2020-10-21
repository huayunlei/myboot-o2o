package com.ihomefnt.o2o.intf.domain.user.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import lombok.Data;

@Data
public class AccountInfoResponseVo {

    private double voucherTotal; //所有抵用券总额
    private List<Voucher> voucherEnableList;//能用抵用券集合
    private int ajbAmount;  //艾积分个数
}

package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class MyAccountRequestVo extends HttpBaseRequest {

    private Double amountPayable; //本次支付金额

}

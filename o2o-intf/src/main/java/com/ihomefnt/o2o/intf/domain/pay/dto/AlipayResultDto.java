package com.ihomefnt.o2o.intf.domain.pay.dto;

import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/28 12:26
 */
@Data
public class AlipayResultDto {

    //private String orderPayInfo;
    private String payInfo;
    private String orderNum;//订单编号

}

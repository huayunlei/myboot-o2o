package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import lombok.Data;

@Data
public class PromotionActiveUserNumResponseVo {
    private Integer userNum;
    private String adviserMobile;
    private Integer orderId;
    private Integer orderSource;
}

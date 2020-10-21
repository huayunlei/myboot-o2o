package com.ihomefnt.o2o.intf.domain.toc.dto;

import lombok.Data;
import org.aspectj.lang.annotation.DeclareAnnotation;

/**
 * Created by Administrator on 2018/11/21 0021.
 */
@Data
public class OldUserDrawPrizeDto {
    private  Integer remainTimes; //剩余抽奖次数,
    private  Integer prizeAmount; //金额,
    private  Integer totalAmount; //总金额
}

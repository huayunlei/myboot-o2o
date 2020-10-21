package com.ihomefnt.o2o.intf.domain.toc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 是否有抽奖资格
 *
 * @author liyonggang
 * @create 2018-11-21 15:51
 */
@Data
public class LuckyDrawTimeDto implements Serializable {

    private static final long serialVersionUID = -8095791015197342449L;
    private Boolean  hasPrizeQuality ;
    private Integer remainTimes ;

}

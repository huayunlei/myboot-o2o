package com.ihomefnt.o2o.intf.domain.toc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 新用户抽奖-抽奖服务返回
 *
 * @author liyonggang
 * @create 2018-11-21 16:50
 */
@Data
public class NewUserDrawPrizeDto implements Serializable {

    private static final long serialVersionUID = 115541945452570512L;

    private Integer remainTimes;//剩余抽奖次数,

    private Integer prizeInfoId;//奖项编号,

    private Integer prizeType;//奖项类型,

    private String prizeName;//奖项类型,

    private String ruleDesc;// 奖励规则说明
}

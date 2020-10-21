package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 老用户福利明细
 *
 * @author liyonggang
 * @create 2018-11-21 17;//02
 */
@Data
public class OldUserBenefitsDto implements Serializable {
    private static final long serialVersionUID = 2623195484106586830L;
    private Integer userId;// 新用户id,
    private String receiveTime;// 领取时间,
    private Integer money;// 金额,
    private Integer state;// 状态 0=已抽奖，1=待兑现，2=兑现中，3=已兑现，4=待回收，5=已回收，6=已作废
    @ApiModelProperty("奖项类型：0=免费赠送类，1=打折类，2=现金券类，3=注水符，4=现金红包类")
    private Integer type;
}

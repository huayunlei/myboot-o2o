package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新用户福利明细
 *
 * @author liyonggang
 * @create 2018-11-21 17:04
 */
@Data
public class NewUserBenefitsDto implements Serializable {
    private static final long serialVersionUID = -1751681612401279494L;
    private Integer prizeInfoId;//奖项编号,
    private String prizeName;//福利名称,
    private String receiveTime;//领取时间,
    private Integer state;//状态 1=已抽奖，2=待兑现，3=兑现中，4=已兑现，5=待回收，6=已回收，7=已作废,
    private String ruleDesc;//奖励规则说明

    @ApiModelProperty("奖项类型：0=免费赠送类，1=打折类，2=现金券类，3=注水符，4=现金红包类")
    private Integer type;
}

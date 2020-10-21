package com.ihomefnt.o2o.intf.domain.toc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户福利信息,包含新老用户--抽奖服务返回
 *
 * @author liyonggang
 * @create 2018-11-21 16:58
 */
@Data
public class UserBenefitDetailsDto implements Serializable {

    private static final long serialVersionUID = 5828446757013024997L;
    private List<OldUserBenefitsDto> oldUserPrizeDetailDtoList;//老用户福利列表
    private List<NewUserBenefitsDto> newUserPrizeDetailDtoList;//新用户福利列表
}


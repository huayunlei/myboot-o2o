package com.ihomefnt.o2o.intf.proxy.toc;

import com.ihomefnt.o2o.intf.domain.toc.dto.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserIdResultDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/1 0001.
 */
public interface TocProxy {

    InviteCodeResponse getInviteCode(Integer id);

    List<RemainTimeDto> getOldUserLuckyDraws(Map<String, Object> params);

    OldUserDrawPrizeDto doOldUserLuckyDraw(Map<String, Object> params);

    InviteCustomerResultDto getCustomerList(Integer id);

    LuckyDrawTimeResponse bindInviteCodeAndUser(Map<String, Object> params);

    LuckyDrawTimeDto getLuckDrawOrNotInformation(Integer id);

    public Integer judgeNewOrOldCustomer(Integer userId);

    UserBenefitDetailsDto getPrizesInformation(Integer userId);

    NewUserDrawPrizeDto doNewUserLuckyDraw(Integer userId);

    List<AgentAndCustomerDto> queryAgentCustomerList(Integer userId);

    UserIdResultDto queryUserInviteCode(String inviteCode);
}

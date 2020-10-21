package com.ihomefnt.o2o.intf.service.toc;

import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.toc.dto.*;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserIdResultDto;

import java.util.Map;

/**
 * Created by Administrator on 2018/11/1 0001.
 */
public interface TocService {

    InviteCodeResponse getInviteCode(HttpUserInfoRequest user);

    OldLuckyDrawResponse doOldUserLuckyDraw(Map<String, Object> params);

    InviteResultResponse getInviteInformation(Integer id);

    LuckyDrawTimeResponse bindInviteCodeAndUser(Map<String, Object> params);

    LuckyDrawTimeResponse getLuckDrawOrNotInformation(Integer id);

    UserDistinguishDto queryCurrentUserIsOldOrNew(Integer userId);

    PrizeListResponse getPrizesInformation(Integer userId);

    NewLuckyDrawResponse doNewUserLuckyDraw(Integer userId);

    boolean queryCurrentUserIsNewUserWithInvitationCode(Integer userId);

    boolean judgeNewOrOldCustomer(Integer userId);

    boolean queryCurrentUserIsUserWithInvitationCode(Integer userId);

    HasOrderDto getHasOrderOrNot(Integer id);

    UserIdResultDto queryUserInviteCode(String inviteCode);
}

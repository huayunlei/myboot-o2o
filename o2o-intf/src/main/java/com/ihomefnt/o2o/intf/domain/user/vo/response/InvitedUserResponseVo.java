package com.ihomefnt.o2o.intf.domain.user.vo.response;

import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitedUserResponseVo {
    private String mobile;
    private String recMobile;


    public InvitedUserResponseVo(UserDo user) {
        mobile = user.getMobile();
        recMobile = user.getRecMobile();
    }
}

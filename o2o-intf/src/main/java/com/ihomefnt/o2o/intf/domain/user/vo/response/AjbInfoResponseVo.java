package com.ihomefnt.o2o.intf.domain.user.vo.response;

import com.ihomefnt.common.util.Page;
import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import lombok.Data;

@Data
public class AjbInfoResponseVo {

    private AjbAccountDto ajbAccountVo;  //艾积分账号信息
    private Page<AccountBookRecordResponseVo> ajbRecordPage;   //账本信息
}

package com.ihomefnt.o2o.intf.service.bankcard;

import com.ihomefnt.o2o.intf.domain.bankcard.vo.request.CardCheckRequestVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.request.CkeckPhoneCodeRequestVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.BankCardResponseVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.CheckCardResponseVo;

/**
 * Created by Administrator on 2018/11/1 0001.
 */
public interface BankCardService {

    BankCardResponseVo getBankCardDetail(Integer userId);

    CheckCardResponseVo checkCard(CardCheckRequestVo request);

    Integer checkUser(CkeckPhoneCodeRequestVo request, String ip);

    void checkPhoneCode(CkeckPhoneCodeRequestVo request);

}

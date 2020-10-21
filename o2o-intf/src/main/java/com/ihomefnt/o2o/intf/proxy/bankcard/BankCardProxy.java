package com.ihomefnt.o2o.intf.proxy.bankcard;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCardDto;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCheckResultDto;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCheckUserDto;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.CheckCardResponseVo;

/**
 * Created by Administrator on 2018/11/1 0001.
 */
public interface BankCardProxy {
	List<BankCardDto> getBankCardDetail(Integer userId);

    CheckCardResponseVo checkCard(String cardNo);

    BankCheckResultDto checkUser(BankCheckUserDto request);

    Boolean setBankCard(BankCardDto bankCardDto);
}

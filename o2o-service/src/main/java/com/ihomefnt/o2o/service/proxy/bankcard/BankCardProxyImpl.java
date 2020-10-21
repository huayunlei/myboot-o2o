package com.ihomefnt.o2o.service.proxy.bankcard;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCardDto;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCheckResultDto;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCheckUserDto;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.CheckCardResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.FgwWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.secure.RSAUtils;
import com.ihomefnt.o2o.intf.proxy.bankcard.BankCardProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.unionpay.BankCardUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
@Service
public class BankCardProxyImpl implements BankCardProxy {

    @Resource
    private StrongSercviceCaller strongSercviceCaller;

    private static final Logger LOG = LoggerFactory.getLogger(BankCardProxyImpl.class);

    @Override
    public List<BankCardDto> getBankCardDetail(Integer userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId",userId);
        try {
	        ResponseVo<List<BankCardDto>> response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_CUSTOMER_INFO_BY_PARAM, params,
	                new TypeReference<ResponseVo<List<BankCardDto>>>() {
	                });
	        if (response != null && response.isSuccess()) {
	        	return response.getData();
	        }
        } catch (Exception e) {
			return null;
		}
        
        return null;
    }

    @Override
    public CheckCardResponseVo checkCard(String cardNo) {
        Map<String,String> params = new HashMap <String,String>();
        boolean isBankCardFlag=BankCardUtil.checkBankCard(cardNo);
        if(!isBankCardFlag){//卡bin接口保护
            return null;
        }
        params.put("cardNo",cardNo);
        
        try {
	        ResponseVo<CheckCardResponseVo> response = strongSercviceCaller.post(FgwWebServiceNameConstants.LIANLIAN_APP_CARDBIN, params,
	                new TypeReference<ResponseVo<CheckCardResponseVo>>() {
	                });
	        if (response != null && response.isSuccess()) {
	        	return response.getData();
	        }
        } catch (Exception e) {
			return null;
		}
        
        return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    public BankCheckResultDto checkUser(BankCheckUserDto dto) {
        Map<String,Object> params = new HashMap <String,Object>();
        params.put("bankcard",dto.getBankcard());
        params.put("realName",dto.getRealName());
        params.put("cardNo",dto.getCardNo());
        params.put("mobile",dto.getMobile());
        params.put("userId",dto.getUserId());
        params.put("module",dto.getModule());

        SortedMap<String,Object> requestParam = new TreeMap<>(params);
        String signContent = RSAUtils.getSignContent(requestParam);
        String sign=null;
        try {
             sign = RSAUtils.sign(signContent, PayConstants.PRIVATE_KEY_FGW);
        } catch (Exception e) {
            LOG.error("RSAUtils.sign ERROR:{}", e.getMessage());
            return null;
        }
        params.put("sign" ,sign);
        
        ResponseVo<BankCheckResultDto> response = null;
        try {
	        response = strongSercviceCaller.post(FgwWebServiceNameConstants.BANK_INFO_CHECK_AND_RECORD, params,
	                new TypeReference<ResponseVo<BankCheckResultDto>>() {});
        } catch (Exception e) {
			return null;
		}
        
        if (null == response) {
			throw new BusinessException(MessageConstant.FAILED);
		}
		if (!response.isSuccess()) {
			throw new BusinessException(response.getCode(), response.getMsg());
		}
		return response.getData();
    }

    @Override
    public Boolean setBankCard(BankCardDto bankCardDto) {
    	ResponseVo<Boolean> response = null;
    	try {
	    	response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.UPDATE_CUSTOMER_BANKCARK_INFO, bankCardDto,
	                new TypeReference<ResponseVo<Boolean>>() {});
    	} catch (Exception e) {
			return false;
		}
    	if (null == response) {
			throw new BusinessException(MessageConstant.FAILED);
		}
		if (!response.isSuccess()) {
			throw new BusinessException(response.getMsg());
		}
		return response.getData();
    }
}

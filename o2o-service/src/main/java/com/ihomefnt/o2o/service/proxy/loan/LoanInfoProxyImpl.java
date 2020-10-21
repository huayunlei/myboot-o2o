package com.ihomefnt.o2o.service.proxy.loan;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.proxy.loan.LoanInfoProxy;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinLoanInfoResponseVo;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款信息服务代理
 * @author ZHAO
 */
@Service
public class LoanInfoProxyImpl implements LoanInfoProxy {

	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public Long createLoanInfo(Map<String, Object> paramMap) {
		ResponseVo<Long> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.CREATE_LOAN_INFO, paramMap,
					new TypeReference<ResponseVo<Long>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<AladdinLoanInfoResponseVo> queryLoanInfo(Integer orderNum) {
		ResponseVo<List<AladdinLoanInfoResponseVo>> responseVo = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNum", orderNum);
		try {
			responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_LOAN_INFO, paramMap,
					new TypeReference<ResponseVo<List<AladdinLoanInfoResponseVo>>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

}

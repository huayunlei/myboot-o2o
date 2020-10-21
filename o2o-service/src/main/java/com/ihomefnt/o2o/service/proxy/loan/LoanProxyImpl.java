package com.ihomefnt.o2o.service.proxy.loan;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanInfoDto;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanMainInfoDto;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanRateDto;
import com.ihomefnt.o2o.intf.domain.loan.vo.request.CreateLoanRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.loan.LoanProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xiamingyu
 */

@Slf4j
@Service
public class LoanProxyImpl implements LoanProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public List<LoanRateDto> queryAllBankLoanRate() {
        try {
            ResponseVo<List<LoanRateDto>> responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.LOAN_QUERY_ALL_BANK_LOAN_RATE, null,
                    new TypeReference<ResponseVo<List<LoanRateDto>>>() {
                    });


            if (responseVo == null) {
                return null;
            }
            if (responseVo.isSuccess() && responseVo.getData() != null) {
                return responseVo.getData();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public List<LoanRateDto> getLoanRateInfoByOrderId(Map<String, Object> params) {
        try {
            ResponseVo<List<LoanRateDto>> responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_BANK_LOAN_RATE_BY_ORDER, params,
                    new TypeReference<ResponseVo<List<LoanRateDto>>>() {
                    });
            if (responseVo == null) {
                throw new BusinessException(HttpReturnCode.WCM_FAILED, MessageConstant.FAILED);
            }
            if (responseVo.isSuccess() && responseVo.getData() != null) {
                return responseVo.getData();
            } else {
                throw new BusinessException(HttpResponseCode.RESPONSE_DATE_NOT_EXIST, MessageConstant.DATA_TRANSFER_EMPTY);
            }
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.WCM_FAILED, MessageConstant.FAILED);
        }
    }

    /**
     * @param paramMap
     * @return
     */
    @Override
    public ResponseVo<Integer> createLoan(CreateLoanRequestVo paramMap) {
        ResponseVo<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.LOAN_APP_CREATE_LOAN_AIJIA, paramMap,
                    new TypeReference<ResponseVo<Integer>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        return responseVo;
    }

    @Override
    public boolean cancelLoan(Map<String, Object> paramMap) {
        try {
            ResponseVo<Boolean> responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.LOAN_APP_CANCEL_LOAN_AIJIA, paramMap,
                    new TypeReference<ResponseVo<Boolean>>() {
                    });


            if (responseVo == null) {
                return false;
            }
            if (responseVo.isSuccess() && responseVo.getData() != null) {
                return responseVo.getData();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public List<LoanMainInfoDto> queryLoanInfoList(Long orderNum) {
        JSONObject param = new JSONObject();
        param.put("orderNum", orderNum);
        try {
            ResponseVo<List<LoanMainInfoDto>> responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.LOAN_APP_QUERY_LOAN_INFOS, param,
                    new TypeReference<ResponseVo<List<LoanMainInfoDto>>>() {
                    });
            if (responseVo != null && responseVo.isSuccess()) {
                return responseVo.getData();
            } else {
                throw new BusinessException("系统有点小异常，请稍后再试");
            }
        } catch (Exception e) {
            log.error("查询爱家贷失败,失败订单号:{}", orderNum, e);
            throw new BusinessException("系统有点小异常，请稍后再试");
        }
    }

    @Override
    public LoanInfoDto queryLoanInfoById(Long loanId) {
        JSONObject param = new JSONObject();
        param.put("loanId", loanId);

        try {
            ResponseVo<LoanInfoDto> responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.LOAN_APP_QUERY_LOAN_AIJIA_DETAIL, param,
                    new TypeReference<ResponseVo<LoanInfoDto>>() {
                    });


            if (responseVo == null) {
                return null;
            }
            if (responseVo.isSuccess() && responseVo.getData() != null) {
                return responseVo.getData();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}

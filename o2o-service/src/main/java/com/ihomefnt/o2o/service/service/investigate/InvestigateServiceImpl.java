package com.ihomefnt.o2o.service.service.investigate;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.investigate.dto.InvestigateCommitDto;
import com.ihomefnt.o2o.intf.domain.investigate.vo.request.InvestigateCommitRequest;
import com.ihomefnt.o2o.intf.domain.investigate.vo.request.InvestigateQueryRequest;
import com.ihomefnt.o2o.intf.domain.investigate.vo.response.InvestigateQueryResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.investigate.InvestigateProxy;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.service.investigate.InvestigateService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/4 11:01 上午
 */
@Service
public class InvestigateServiceImpl implements InvestigateService {

    @Autowired
    private InvestigateProxy investigateProxy;
    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;
    @Autowired
    private SmsProxy smsProxy;

    @Override
    public InvestigateQueryResponse queryInvestigateInfo(InvestigateQueryRequest request) {

        return investigateProxy.queryInvestigateInfo(request.getInvestigateId());
    }

    @Override
    public void commitInvestigate(InvestigateCommitRequest request) {
        String mobile = "";
        String userName = "";
        if (2 == request.getChannel()) {// H5
            mobile = request.getMobile();
            CheckSmsCodeParamVo checkSmsCodeParamVo = new CheckSmsCodeParamVo();
            checkSmsCodeParamVo.setMobile(request.getMobile());
            checkSmsCodeParamVo.setSmsCode(request.getAuthCode());
            checkSmsCodeParamVo.setType(2);
            boolean result = smsProxy.checkSmsCode(checkSmsCodeParamVo);
            if (!result) {
                throw new BusinessException(HttpReturnCode.FAILED_VERIFY_FOR_MSG_CODE, MessageConstant.CODE_ERROR);
            }
        } else {
            if (null == request.getUserInfo()) {
                throw new BusinessException(HttpResponseCode.ADMIN_ILLEGAL, MessageConstant.ADMIN_ILLEGAL);
            }
            HttpUserInfoRequest userInfo = request.getUserInfo();
            mobile = userInfo.getMobile();
            userName = userInfo.getUsername();
        }

        InvestigateCommitDto commitDto = new InvestigateCommitDto();
        commitDto.setChannel(request.getChannel())
                .setConsumeTime(request.getConsumeTime())
                .setInvestigateId(request.getInvestigateId())
                .setInvestigateName(request.getInvestigateName())
                .setMobile(mobile)
                .setSubmitter(userName)
                .setOrderId(request.getOrderId())
                .setSelectedQuestions(request.getSelectedQuestions());

        // 查询订单状态
        if (null != request.getOrderId() && request.getOrderId() > 0) {
            AppOrderBaseInfoResponseVo orderBaseInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
            if (null != orderBaseInfo) {
                commitDto.setOrderStatus(orderBaseInfo.getOrderStatus());
            }
        }
        investigateProxy.commitInvestigate(commitDto);
    }
}

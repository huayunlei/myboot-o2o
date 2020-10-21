package com.ihomefnt.o2o.service.service.loan;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanInfoDto;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanMainInfoDto;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanRateDto;
import com.ihomefnt.o2o.intf.domain.loan.vo.request.CancelLoanRequestVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.request.CreateLoanRequestVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserIdCardRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.loan.LoanConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IdCardUtil;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.loan.LoanService;
import com.ihomefnt.o2o.service.proxy.loan.LoanProxyImpl;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiamingyu
 * @date 2018/6/21
 */

@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger LOG = LoggerFactory.getLogger(LoanServiceImpl.class);

    private final SimpleDateFormat dayFormat_DAY = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private LoanProxyImpl loanProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    private ProductProgramOrderProxyImpl orderProxy;

    @Autowired
    private HomeBuildingService homeBuildingService;

    @Autowired
    private DicProxy dicProxy;

    @Override
    public LoanBaseDataResponseVo queryAllBankLoanRate() {
        List<LoanRateDto> loanRateVoList = loanProxy.queryAllBankLoanRate();
        if (CollectionUtils.isEmpty(loanRateVoList)) {
            return null;
        }
        LoanBaseDataResponseVo response = new LoanBaseDataResponseVo();
        List<LoanMapDataResponseVo> loanMapDataList = new ArrayList<>();
        for (LoanRateDto loanRateVo : loanRateVoList) {
            if (loanRateVo != null && loanRateVo.getBankId().equals(LoanConstant.BANK_OF_CHINA_ID) && IntegerUtil.equals(loanRateVo.getLoanType(), 1) && IntegerUtil.equals(loanRateVo.getCompanyId(), 9)) {
                LoanMapDataResponseVo loanMapData = new LoanMapDataResponseVo();
                loanMapData.setYear(loanRateVo.getLoanTerm());
                loanMapData.setInterestRate(loanRateVo.getLoanRate());
                loanMapDataList.add(loanMapData);
            }
        }
        response.setBankName(LoanConstant.BANK_OF_CHINA_NAME);
        response.setBankId(LoanConstant.BANK_OF_CHINA_ID);
        response.setLoanMapDataList(loanMapDataList);
        return response;
    }

    @Override
    public LoanBaseDataResponseVo getLoanRateInfoByOrderId(Integer orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bankId", LoanConstant.BANK_OF_CHINA_ID);
        params.put("loanType", 1);// 写死贷款类型为分期分摊
        params.put("orderNum", orderId);

        LoanBaseDataResponseVo response = new LoanBaseDataResponseVo();
        List<LoanRateDto> loanRateVoList = loanProxy.getLoanRateInfoByOrderId(params);
        if (CollectionUtils.isNotEmpty(loanRateVoList)) {
            List<LoanMapDataResponseVo> loanMapDataList = new ArrayList<>();
            for (LoanRateDto loanRateVo : loanRateVoList) {
                if (loanRateVo != null) {
                    LoanMapDataResponseVo loanMapData = new LoanMapDataResponseVo();
                    loanMapData.setYear(loanRateVo.getLoanTerm());
                    loanMapData.setInterestRate(loanRateVo.getLoanRate());
                    loanMapDataList.add(loanMapData);
                }
            }
            response.setBankName(LoanConstant.BANK_OF_CHINA_NAME);
            response.setBankId(LoanConstant.BANK_OF_CHINA_ID);
            response.setLoanMapDataList(loanMapDataList);
        }
        return response;
    }

    @Override
    public Integer createLoan(CreateLoanRequestVo request) {
        UserIdCardRequestVo userRequest = new UserIdCardRequestVo();
        userRequest.setAccessToken(request.getAccessToken());
        userRequest.setIdCardNum(request.getApplyerIdNum());
        userRequest.setRealName(request.getApplyer());
        ResponseVo<Integer> loan = loanProxy.createLoan(request);
        if (loan == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.SUBMIT_APPLY_FAILED);
        }
        /**
         * code   	描述
         * 1		成功
         * -1		创建爱家贷失败
         * 1001	订单不存在或订单已完成或取消不能创建爱家贷！
         * 1002	根据政策要求，目前仅支持已签约订单发起爱家贷申请~
         * 1003	系统没下单不支持创建爱家贷，请先进行系统下单操作
         * 1004	订单已存在未取消的爱家贷记录，不能创建爱家贷！
         * 1005	贷款申请金额需要小于订单待付金额
         * 1006	根据政策要求，分期付款不支持爱家贷申请~
         * 1007	系统暂时不支持同时添加不同银行的爱家贷申请，您可以考虑取消之前的爱家贷后再添加或联系技术支持同事。
         * 1008	系统暂时不支持多笔爱家贷使用不同的贷款方式，您可以考虑取消之前的爱家贷后再添加或联系技术支持同事。
         * 1009 身份证号不合法
         */
        if (loan.getCode() == 1005) {//单独处理金额问题
            String msg = loan.getMsg();
            if (msg.contains("金额")) {
                throw new BusinessException(HttpResponseCode.FAILED, String.format(MessageConstant.CREATE_LOAN_MESSAGE_ACCOUNTS_DIFFER, msg.substring(msg.lastIndexOf("金额") + 2)));
            }
        } else if (loan.getCode() == 1) {
            return loan.getData();
        } else if (loan.getCode() == 1009) {
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, MessageConstant.ID_NUMBER_ILLEGAL);
        } else {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.SUBMIT_APPLY_FAILED);
        }
        return loan.getData();
    }

    public static void checkParamsForSetUserIdCard(UserIdCardRequestVo request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (StringUtils.isBlank(request.getRealName())) {
            throw new BusinessException("真实姓名不能为空");
        }
        if (StringUtils.isBlank(request.getRealName())) {
            throw new BusinessException("身份证号码不能为空");
        }

        //校验身份证号码合法性
        boolean isIdCardFlag = IdCardUtil.judgeIdCard(request.getIdCardNum());
        if (!isIdCardFlag) {
            throw new BusinessException("身份证号码不合法，请输入正确的身份证号码");
        }
    }


    @Override
    public boolean cancelLoan(CancelLoanRequestVo request) {
        if (request == null || IntegerUtil.isEmpty(request.getLoanId())) {
            LOG.info("cancelLoan ERROR: param is bad:" + JsonUtils.obj2json(request));
            return false;
        }
        if (IntegerUtil.isEmpty(request.getReasonId())) {
            request.setReasonId(LoanConstant.REASON_ID_OTHER);
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loanId", request.getLoanId());
        paramMap.put("reasonId", request.getReasonId());
        paramMap.put("reason", request.getReason());
        return loanProxy.cancelLoan(paramMap);
    }

    @Override
    public LoanListResponseVo queryLoanInfoList(Long orderNum) {
        if (orderNum == null || orderNum <= 0) {
            LOG.info("queryLoanInfoList ERROR: param orderId is bad:", orderNum);
            return null;
        }
        LoanListResponseVo response = new LoanListResponseVo();
        List<LoanMainInfoDto> responseVo = loanProxy.queryLoanInfoList(orderNum);
        if (responseVo != null) {
            List<LoanDetailDataResponseVo> dataList = new ArrayList<>();
            List<LoanDetailDataResponseVo> dataListApply = new ArrayList<>();
            List<LoanDetailDataResponseVo> dataListCompleted = new ArrayList<>();
            List<LoanDetailDataResponseVo> dataListCancel = new ArrayList<>();
            List<LoanDetailDataResponseVo> dataListOther = new ArrayList<>();

            for (LoanMainInfoDto vo : responseVo) {
                if (vo != null) {
                    LoanDetailDataResponseVo data = new LoanDetailDataResponseVo();
                    data.setLoanId(vo.getLoanId());
                    data.setLoanStatus(vo.getFrontStatus());
                    data.setLoanStatusStr(vo.getFrontStatusStr());
                    data.setLoanMoney(vo.getAmount());
                    if (vo.getApplyTime() != null) {
                        data.setApplyTime(dayFormat_DAY.format(vo.getApplyTime()));
                    }
                    if (vo.getAccountedTime() != null) {
                        data.setAccountedTime(dayFormat_DAY.format(vo.getAccountedTime()));
                    }
                    if (vo.getFrontStatus().equals(LoanConstant.STATUS_CANCEL) && vo.getUpdateTime() != null) {
                        data.setCancelTime(dayFormat_DAY.format(vo.getUpdateTime()));
                    }
                    //排序需要
                    if (vo.getFrontStatus() == null) {
                        dataListOther.add(data);
                    } else if (vo.getFrontStatus().equals(LoanConstant.STATUS_PROCESS) || vo.getFrontStatus().equals(LoanConstant.STATUS_ENTRY) || vo.getFrontStatus().equals(LoanConstant.STATUS_ENTERTAIN)) {
                        dataListApply.add(data);
                    } else if (vo.getFrontStatus().equals(LoanConstant.STATUS_COMPLETED)) {
                        dataListCompleted.add(data);
                    } else if (vo.getFrontStatus().equals(LoanConstant.STATUS_CANCEL) || vo.getFrontStatus().equals(LoanConstant.STATUS_REJECT)) {
                        dataListCancel.add(data);
                    } else {
                        dataListOther.add(data);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(dataListApply)) {
                dataList.addAll(dataListApply);
            }
            if (CollectionUtils.isNotEmpty(dataListCompleted)) {
                dataList.addAll(dataListCompleted);
            }
            if (CollectionUtils.isNotEmpty(dataListCancel)) {
                dataList.addAll(dataListCancel);
            }
            if (CollectionUtils.isNotEmpty(dataListOther)) {
                dataList.addAll(dataListOther);
            }
            response.setLoanList(dataList);
            return response;
        }
        return null;
    }

    @Override
    public LoanDetailDataResponseVo queryLoanInfoById(Long loanId) {
        if (loanId == null) {
            LOG.info("queryLoanInfoById ERROR: param loanId is bad:", loanId);
            return null;
        }
        LoanInfoDto responseVo = loanProxy.queryLoanInfoById(loanId);
        if (responseVo == null) {
            return null;
        }
        List<LoanMainInfoDto> loanListVo = loanProxy.queryLoanInfoList(responseVo.getOrderNum());
        LoanDetailDataResponseVo loanDetailData = new LoanDetailDataResponseVo();
        HomeConsultantResponseVo homeConsultant = new HomeConsultantResponseVo();
        homeConsultant.setName(responseVo.getAdviserName());
        homeConsultant.setMobile(responseVo.getAdviserMobile());
        loanDetailData.setHomeConsultant(homeConsultant);
        if (responseVo.getApplyTime() != null) {
            loanDetailData.setApplyTime(dayFormat_DAY.format(responseVo.getApplyTime()));
        }

        if (responseVo.getEntertainTime() != null) {
            loanDetailData.setEntertainTime(dayFormat_DAY.format(responseVo.getEntertainTime()));
        }

        if (responseVo.getAccountedTime() != null) {
            loanDetailData.setAccountedTime(dayFormat_DAY.format(responseVo.getAccountedTime()));
        }

        if (responseVo.getFrontStatus().equals(LoanConstant.STATUS_CANCEL)) {
            if (responseVo.getUpdateTime() != null) {
                loanDetailData.setCancelTime(dayFormat_DAY.format(responseVo.getUpdateTime()));
            }
            loanDetailData.setCancelReason(responseVo.getReason());
        }
        if (responseVo.getAnnualRate() != null) {
            BigDecimal rate = new BigDecimal(String.valueOf(responseVo.getAnnualRate()));
            loanDetailData.setInterestRate(Float.parseFloat(rate.multiply(new BigDecimal(100)).toString()));
        }
        loanDetailData.setLoanId(responseVo.getLoanId());
        loanDetailData.setYear(responseVo.getLoanYears());
        loanDetailData.setLoanStatusStr(responseVo.getFrontStatusStr());
        loanDetailData.setLoanStatus(responseVo.getFrontStatus());
        loanDetailData.setLoanType(responseVo.getPaymentTypeStr());
        loanDetailData.setBank(responseVo.getBankName());
        loanDetailData.setLoanMoney(responseVo.getAmount());
        loanDetailData.setOrderId(responseVo.getOrderNum());
        loanDetailData.setAccountedAmount(responseVo.getAccountedAmount());
        loanDetailData.setReApply(true);
        LoanMainInfoDto loanMainInfoVo = null;
        for (LoanMainInfoDto mainInfoVo : loanListVo) {
            //如果已经有非已取消状态的爱家贷，则不可重新申请
            if (!mainInfoVo.getFrontStatus().equals(LoanConstant.STATUS_CANCEL)) {
                loanDetailData.setReApply(false);
            }
            if (mainInfoVo.getLoanId().equals(loanId)) {
                loanMainInfoVo = mainInfoVo;
            }
        }
        if (loanMainInfoVo != null) {
            //设置申请人姓名
            if (loanMainInfoVo != null && loanMainInfoVo.getApplyer() != null) {
                loanDetailData.setApplyer(loanMainInfoVo.getApplyer());
            }
            //设置申请人身份增信息
            if (loanMainInfoVo != null && loanMainInfoVo.getApplyerIdNum() != null) {
                //隐藏身份证中间八位
                loanDetailData.setApplyerIdNum(loanMainInfoVo.getApplyerIdNum().replaceAll("(\\d{6})\\d{8}(\\w{4})", "$1*****$2"));
            }
        }
        return loanDetailData;
    }

    /**
     * 查询订单款项简单信息
     *
     * @param orderid
     * @return
     */
    @Override
    public UnpaidMoneyResponseVo getUnpaidMoneyByOrderId(Integer orderid) {
        UnpaidMoneyResponseVo result = new UnpaidMoneyResponseVo();
        //订单交款信息
        BigDecimal paidAmount = BigDecimal.ZERO;//已付款金额
        BigDecimal contractAmount = BigDecimal.ZERO;//合同额
        AppOrderBaseInfoResponseVo orderInfo = orderProxy.queryAppOrderBaseInfo(orderid);
        if (orderInfo == null) {
            return result.setUnpaidMoney(new BigDecimal(0));
        }
        if (orderInfo.getContractAmount() != null) {
            contractAmount = orderInfo.getContractAmount();
        }
        if (orderInfo.getFundAmount() != null) {
            paidAmount = orderInfo.getFundAmount();
        }
        result.setUnpaidMoney(contractAmount.equals(BigDecimal.ZERO) ? contractAmount : contractAmount.subtract(paidAmount));
        result.setHomeAdviserMobile(orderInfo.getAdviserPhone());
        result.setOrderStatus(homeBuildingService.getOrderStatus(orderInfo.getOrderStatus()));
        return result;
    }
}

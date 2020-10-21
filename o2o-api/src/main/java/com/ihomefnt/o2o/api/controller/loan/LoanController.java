package com.ihomefnt.o2o.api.controller.loan;

import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.loan.vo.request.*;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.service.loan.LoanService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xiamingyu
 */
@RestController
@Api(tags = "【爱家贷API】")
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoanService loanService;

    @ApiOperation(value = "查询爱家贷年限利率对应关系", notes = "爱家贷年限利率对应关系")
    @RequestMapping(value = "/getLoanBaseData", method = RequestMethod.POST)
    public HttpBaseResponse<LoanBaseDataResponseVo> getLoanBaseData(@RequestBody LoanRateRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        LoanBaseDataResponseVo response = new LoanBaseDataResponseVo();
        // 传入订单号，则根据订单获取对应分公司/市 贷款利率；不传默认河南公司贷款利率
        if(null == request.getOrderId()){
            response = loanService.queryAllBankLoanRate();
        }else {
            response = loanService.getLoanRateInfoByOrderId(request.getOrderId());
        }
        if (response == null) {
        	return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "提交爱家贷申请", notes = "提交爱家贷申请")
    @RequestMapping(value = "/createLoan", method = RequestMethod.POST)
    public HttpBaseResponse<CreateLoanResponseVo> submitLoanData(@RequestBody CreateLoanRequestVo request) {
        if (request == null || StringUtil.isBlank(request.getAccessToken()) || request.getOrderNum() == null || request.getLoanYears() == null || request.getAmount() == null || request.getBankId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        UserDto userDto = userService.getUserByToken(request.getAccessToken());
        if (userDto==null){
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN,MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(new CreateLoanResponseVo().setLoanId(loanService.createLoan(request)));
    }

    @ApiOperation(value = "取消爱家贷", notes = "取消爱家贷")
    @RequestMapping(value = "/cancelLoan", method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> cancelLoan(@RequestBody CancelLoanRequestVo request) {
        if (request == null) {
        	return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        boolean result = loanService.cancelLoan(request);
        if (!result) {
        	return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "查询订单的爱家贷列表", notes = "爱家贷列表")
    @RequestMapping(value = "/queryLoanList", method = RequestMethod.POST)
    public HttpBaseResponse<LoanListResponseVo> queryLoanList(@RequestBody LoanListRequestVo request) {
        if (request == null) {
        	return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        UserDto userDto = userService.getUserByToken(request.getAccessToken());
        if (StringUtil.isBlank(request.getAccessToken()) || userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
        	return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        
        LoanListResponseVo response = loanService.queryLoanInfoList(request.getOrderId());
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "根据id查询爱家贷详情", notes = "爱家贷详情")
    @RequestMapping(value = "/queryLoanDetail", method = RequestMethod.POST)
    public HttpBaseResponse<LoanDetailDataResponseVo> queryLoanDetail(@RequestBody LoanDetailRequestVo request) {
    	if (request == null) {
        	return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
    	
        LoanDetailDataResponseVo response = loanService.queryLoanInfoById(request.getLoanId());
        if (response == null) {
        	return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/getUnpaidMoneyByOrderId",method = RequestMethod.POST)
    @ApiOperation(value = "查询订单未付款金额",notes = "查询订单未付款金额")
    public HttpBaseResponse<UnpaidMoneyResponseVo> getUnpaidMoneyByOrderId(@RequestBody LoanListRequestVo request) {
        if (StringUtils.isBlank(request.getAccessToken()) || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        UserDto user = userService.getUserByToken(request.getAccessToken());
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(loanService.getUnpaidMoneyByOrderId(request.getOrderId().intValue()));
    }

}

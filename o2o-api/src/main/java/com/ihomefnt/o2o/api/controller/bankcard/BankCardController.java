package com.ihomefnt.o2o.api.controller.bankcard;

import com.ihomefnt.o2o.intf.domain.bankcard.vo.request.CardCheckRequestVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.request.CkeckPhoneCodeRequestVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.BankCardResponseVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.CheckCardResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.manager.util.common.SmsUtils;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IpUtils;
import com.ihomefnt.o2o.intf.service.bankcard.BankCardService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "【银行卡API】")
@RestController
@RequestMapping("/bankCard")
public class BankCardController {
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private UserService userService;


    @ApiOperation(value = "获取银行卡信息", notes = "获取银行卡具体信息")
    @RequestMapping(value = "/getBankCardDetail", method = RequestMethod.POST)
    public HttpBaseResponse<BankCardResponseVo> getBankCardDetail(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        BankCardResponseVo bankCard = bankCardService.getBankCardDetail(user.getId());
        if (bankCard == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.BANK_CARD_NOT_EXISTS);
        }
        return HttpBaseResponse.success(bankCard);
    }

    @ApiOperation(value = "银行卡合法性校验", notes = "银行卡合法性校验+二元素校验")
    @RequestMapping(value = "/checkCard", method = RequestMethod.POST)
    public HttpBaseResponse<CheckCardResponseVo> checkCard(@RequestBody CardCheckRequestVo request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CheckCardResponseVo checkCard = bankCardService.checkCard(request);
        return HttpBaseResponse.success(checkCard);
    }

    @ApiOperation(value = "用户身份（手机号）校验", notes = "用户身份（手机号）校验")
    @RequestMapping(value = "/checkUserSendSmsCode", method = RequestMethod.POST)
    public HttpBaseResponse<Void> checkUserSendSmsCode(HttpServletRequest servletRequest, @RequestBody CkeckPhoneCodeRequestVo request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String ip = IpUtils.getIpAddr(servletRequest);
        return SmsUtils.handlerSmsSendResult(bankCardService.checkUser(request, ip));
    }

    @ApiOperation(value = "验证码认证", notes = "验证码认证并保存银行卡信息")
    @RequestMapping(value = "/checkPhoneCode", method = RequestMethod.POST)
    public HttpBaseResponse<Void> checkPhoneCode(@RequestBody CkeckPhoneCodeRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        bankCardService.checkPhoneCode(request);
        return HttpBaseResponse.success("发送成功");
    }


}

package com.ihomefnt.o2o.service.service.bankcard;

import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCardDto;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCheckResultDto;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCheckUserDto;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.request.CardCheckRequestVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.request.CkeckPhoneCodeRequestVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.BankCardResponseVo;
import com.ihomefnt.o2o.intf.domain.bankcard.vo.response.CheckCardResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.domain.sms.dto.SendSmsCodeParamVo;
import com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.unionpay.BankCardUtil;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IdCardUtil;
import com.ihomefnt.o2o.intf.proxy.bankcard.BankCardProxy;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.bankcard.BankCardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.regex.Pattern;


@Service
public class BankCardServiceImpl implements BankCardService {

    @Autowired
    private BankCardProxy bankCardDao;

    @Autowired
    UserProxy userProxy;

    @Autowired
    SmsProxy smsProxy;

    /**
     * 获取卡片明细数据
     *
     * @param userId
     * @return
     */
    @Override
    public BankCardResponseVo getBankCardDetail(Integer userId) {
        BankCardResponseVo response = new BankCardResponseVo();
        List<BankCardDto> bankCardDtoList = bankCardDao.getBankCardDetail(userId);
        if (CollectionUtils.isEmpty(bankCardDtoList)) {
            response.setState(0);
        } else {
            BankCardDto bankCardDto = bankCardDtoList.get(0);
            if (StringUtils.isNotBlank(bankCardDto.getBankCardNumber())) {
                response.setState(1);
                response.setBankName(bankCardDto.getHeadBankName());
                response.setCardNumber(bankCardDto.getBankCardNumber());
            } else {
                response.setState(0);
            }
            response.setName(bankCardDto.getName());
        }
        return response;
    }

    /**
     * 卡片校验
     *
     * @param request
     * @return
     */
    @Override
    public CheckCardResponseVo checkCard(CardCheckRequestVo request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        BankCardResponseVo bankCard = this.getBankCardDetail(user.getId());
        if (bankCard != null && bankCard.getCardNumber() != null &&
                (bankCard.getCardNumber().equals(request.getCardNumber()))) {
            throw new BusinessException(HttpResponseCode.CHECK_CARD_FAILED,MessageConstant.BANKCARDBAND_FAILED);
        }

        CheckCardResponseVo checkCard = bankCardDao.checkCard(request.getCardNumber());
        if (checkCard == null) {
            throw new BusinessException(HttpResponseCode.CHECK_CARD_FAILED,MessageConstant.CHECK_CARD_FAILED);
        }
        if (checkCard.getCardType() == 3) {
            throw new BusinessException(HttpResponseCode.CHECK_CARD_FAILED,MessageConstant.CREDITCARDS_NOT_SUPPORT);
        }
        //阿里云二元素校验接口下架
//        CkeckPhoneCodeRequestVo ckeckPhoneCodeRequest = new CkeckPhoneCodeRequestVo();
//        ckeckPhoneCodeRequest.setCardNumber(request.getCardNumber());
//        ckeckPhoneCodeRequest.setName(request.getName());
//        BankCheckResultDto bankCheckResult = this.checkUserTwo(ckeckPhoneCodeRequest, user.getId());//卡bin接口验证通过后进行两元素校验
//        if (bankCheckResult == null || !bankCheckResult.isResult()) {
//            String msg = null;
//            if (bankCheckResult != null) {
//                msg = bankCheckResult.getMsg();
//            } else {
//                msg = MessageConstant.FAILED;
//            }
//            throw new BusinessException(msg);
//        }
        return checkCard;
    }

    /**
     * 四元素校验身份信息
     *
     * @param request
     * @return
     */
    @Override
    public Integer checkUser(CkeckPhoneCodeRequestVo request, String ip) {
        String checkMsg = checkMobile(request.getMobile());
        if (StringUtils.isNotBlank(checkMsg)) {//验证手机号
            throw new BusinessException(checkMsg);
        }
        boolean isIdCardFlag = IdCardUtil.judgeIdCard(request.getCardNo());//验证身份证号码合法性
        if (!isIdCardFlag) {
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, MessageConstant.IDCARD_ERROR);
        }
        boolean isBankCardFlag = BankCardUtil.checkBankCard(request.getCardNumber());//银行卡合法性校验
        if (!isBankCardFlag) {
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, MessageConstant.CHECK_CARD_FAILED);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        if (user.getMobile() != null && !request.getMobile().equals(user.getMobile())) {//手机号需与全品家订单一致
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, MessageConstant.MOBILE_NOT_MATCH_ERROR);
        }
        BankCheckResultDto result = checkBankCardMessage(request);
        if (!result.isResult()) {
            throw new BusinessException(result.getMsg());
        }
        BankCheckUserDto bankCheckUserDto = assembleBankCheckUserDto(request, user.getId());
        BankCheckResultDto bankCheckResult = bankCardDao.checkUser(bankCheckUserDto);
        if (bankCheckResult == null) {
            throw new BusinessException(MessageConstant.FAILED);
        }
        if (!bankCheckResult.isResult() && bankCheckResult.getCode() != null) {
            throw new BusinessException(changeCode(bankCheckResult.getCode()), getMessageByCode(bankCheckResult.getCode()));
        }

        SendSmsCodeParamVo param = new SendSmsCodeParamVo();
        param.setIp(ip);
        param.setMobile(request.getMobile());
        param.setType(request.getSmsType());
        return smsProxy.sendSmsCode(param);//发送验证码
    }

    private BankCheckUserDto assembleBankCheckUserDto(CkeckPhoneCodeRequestVo request, Integer userId) {
        BankCheckUserDto bankCheckUserDto = new BankCheckUserDto();
        bankCheckUserDto.setBankcard(request.getCardNumber());
        bankCheckUserDto.setRealName(request.getName());
        bankCheckUserDto.setCardNo(request.getCardNo());
        bankCheckUserDto.setMobile(request.getMobile());
        bankCheckUserDto.setUserId(userId);
        bankCheckUserDto.setModule(PayConstants.MODULE_CODE_FGW);
        return bankCheckUserDto;
    }

    private String checkMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "手机号为空";
        }
        Pattern pattern = Pattern.compile("^-?[0-9]+");
        if (!pattern.matcher(mobile).matches()) {
            //非数字
            return "手机号格式不正确";
        }
        return null;
    }

    /**
     * 二元素校验身份信息
     * 因四元素验证接口无法区分银行卡、身份证、手机号错误，在卡号验证时添加一个二元素认证
     *
     * @param request
     * @return
     */
    @Deprecated
    private BankCheckResultDto checkUserTwo(CkeckPhoneCodeRequestVo request, Integer userId) {
        BankCheckUserDto bankCheckUserDto = assembleBankCheckUserDto(request, userId);
        BankCheckResultDto bankCheckResult = bankCardDao.checkUser(bankCheckUserDto);
        if (bankCheckResult != null && !bankCheckResult.isResult() && bankCheckResult.getCode() != null) {
            bankCheckResult.setMsg(getMessageByCodeSecond(bankCheckResult.getCode()));
        }
        return bankCheckResult;
    }

    /**
     * 认证验证码并录入数据
     *
     * @param request
     * @return
     */
    @Override
    public void checkPhoneCode(CkeckPhoneCodeRequestVo request) {
        if (StringUtils.isBlank(request.getCardNumber())) {
            throw new BusinessException(MessageConstant.BANKCARD_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        CheckSmsCodeParamVo param = new CheckSmsCodeParamVo();
        param.setMobile(request.getMobile());
        param.setSmsCode(request.getCode());
        param.setType(request.getSmsType());
        boolean result = smsProxy.checkSmsCode(param);
        if (!result) {//验证码认证结果
            throw new BusinessException(HttpReturnCode.FAILED_VERIFY_FOR_MSG_CODE, MessageConstant.CODE_ERROR);
        }
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setUserId(user.getId());
        bankCardDto.setBankCardNumber(request.getCardNumber());
        bankCardDto.setHeadBankName(request.getBankName());

        Boolean rs = bankCardDao.setBankCard(bankCardDto);
        if (!rs) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    /**
     * 银行卡四元素信息校验
     *
     * @return
     */
    private BankCheckResultDto checkBankCardMessage(CkeckPhoneCodeRequestVo request) {
        BankCheckResultDto bankCheckResult = new BankCheckResultDto();
        bankCheckResult.setResult(false);
        if (StringUtils.isBlank(request.getName())) {
            bankCheckResult.setMsg(MessageConstant.NAME_IS_NULL);
            return bankCheckResult;
        }
        if (StringUtils.isBlank(request.getCardNumber())) {
            bankCheckResult.setMsg(MessageConstant.CARDNO_IS_NULL);
            return bankCheckResult;
        }
        if (StringUtils.isBlank(request.getMobile())) {
            bankCheckResult.setMsg(MessageConstant.PHONENO_IS_NULL);
            return bankCheckResult;
        }
        if (StringUtils.isBlank(request.getCardNo())) {
            bankCheckResult.setMsg(MessageConstant.USERNO_IS_NULL);
            return bankCheckResult;
        }
        bankCheckResult.setResult(true);
        return bankCheckResult;

    }

    /**
     * code码转换
     *
     * @param code
     * @return
     */
    private Long changeCode(Integer code) {
        switch (code) {
            case 102:
                return HttpReturnCode.DING_MONITOR_WHITE_END;
            case 103:
                return HttpReturnCode.DING_MONITOR_WHITE_END;
            default:
                return code.longValue();
        }
    }

    /**
     * 四元素校验返回码
     *
     * @param code
     * @return
     */
    private String getMessageByCode(Integer code) {
        String message = "系统内部错误";
        switch (code) {
            case 100:
                message = "成功";
                break;
            case 101:
                message = "系统内部错误";
                break;
            case 102:
                message = "卡号、身份证或手机号码信息有误，请核对后再试。";
                break;
            case 103:
                message = "您的身份证号信息有误，请核对后再试。";
                break;
            default:
                message = "卡号、身份证或手机号码信息有误，请核对后再试。";
                break;
        }
        return message;
    }

    /**
     * 二元素校验返回码
     *
     * @param code
     * @return
     */
    @Deprecated
    private String getMessageByCodeSecond(Integer code) {
        String message = "系统内部错误";
        switch (code) {
            case 100:
                message = "成功";
                break;
            case 101:
                message = "系统内部错误";
                break;
            case 102:
                message = MessageConstant.USER_CARDCHECK_ERROR;
                break;
            default:
                message = MessageConstant.USER_CARDCHECK_ERROR;
                break;
        }
        return message;
    }
}

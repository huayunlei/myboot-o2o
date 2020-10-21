package com.ihomefnt.o2o.intf.manager.constant.pay;

/**
 * @Description:
 * @Author hua
 * @Date 2019-07-09 10:41
 */
public enum PayCommonErrorCodeEnum {

    EXCEED_SINGLE_PAYMENT_LIMIT(1019, "超过单笔支付限额，请调整金额分多笔支付"),
    ACCOUNT_BALANCE_NOT_ENOUGH(9104, "账户余额不足"),
    EXCESSIVE_RISK_LEVEL(9910, "银行卡风险等级过高，请联系发卡银行"),
    TRANSACTION_AMOUNT_EXCEEDS_LIMIT(1016, "交易金额超过银行限制"),
    DAILY_AMOUNT_OR_TRADE_COUNT_EXCEEDS_LIMIT(1014, "日累计金额或笔数超过银行限制，请隔日再付"),
    NOT_SUPPORTED_ONLINE_PAYMENT(1107, "您的银行卡暂不支持在线支付业务"),
    INCORRECT_IDENTITY_INFORMATION(1108, "您输入的证件号、姓名或手机号有误，请检查"),
    INCORRECT_IDENTITY_INFORMATION2(1004, "您输入的证件号、姓名或手机号有误，请检查"),

    CARD_AND_IDENTITY_NOT_MATCH(1109, "您的银行卡号和证件号不符，请检查"),
    ABNORMAL_CARD_STATE(1110, "您的银行卡状态异常，请咨询发卡银行"),
    IDENTITY_ERROR(1112, "您输入的证件号有误，请检查"),
    CARDHOLDER_NAME_ERROR(1113, "您输入的持卡人姓名有误，请检查"),
    CARDHOLDER_MOBILE_ERROR(1114, "您输入的手机号有误，请检查是否是银行预留的手机号"),
    CARD_NO_RESERVED_PHONE_NUMBER(1115, "该银行卡未在银行预留手机号，请于发卡银行联系"),

    BANK_ONLINE_NOT_SUPPORT(1200, "该银行卡暂不支持线上支付，请重新选择其他银行卡"),
    NOT_SUPPORT_THE_BANK(1805, "暂不支持该银行，请查看支持银行范围然后使用其他银行卡"),
    BANK_ACCOUNT_TYPE_NOT_SUPPORT(1005, "不支持该银行账户类型，请重新选择其他银行卡"),
    SYSTEM_ERROR(9999, "系统错误，请联系专属小艾管家或线上客服")

    ;

    private Integer code;
    private String showMsg;

    private PayCommonErrorCodeEnum (Integer code, String showMsg) {
        this.code = code;
        this.showMsg = showMsg;
    }


    public static String getShowMsg(Integer code) {
        if (null == code) {
            return PayCommonErrorCodeEnum.SYSTEM_ERROR.getShowMsg();
        }
        for (PayCommonErrorCodeEnum item : PayCommonErrorCodeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getShowMsg();
            }
        }
        return PayCommonErrorCodeEnum.SYSTEM_ERROR.getShowMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }
}

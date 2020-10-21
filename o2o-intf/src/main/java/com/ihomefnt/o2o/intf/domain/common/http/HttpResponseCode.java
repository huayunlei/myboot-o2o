package com.ihomefnt.o2o.intf.domain.common.http;

/**
 * Created by shirely_geng on 15-1-14.
 */
public class HttpResponseCode {

    public static final Long SUCCESS = 1L;
    public static final Long FAILED = 2L;
    public static final Long Data_EXISTS = 3L;
    public static final Long WRONG_REQUEST = 7L;// 错误的请求
    public static final Long QUERY_FAILED = 23L;// 查询失败
    public static final Long PARAMS_NOT_EXISTS = 4L;// 参数缺失
    public static final Long PARAMS_VERIFICATION_ERROR = 102623L;// 参数验证失败
    public static final Long ADMIN_ILLEGAL = 102603L;//未登录或该账号在其他地方登录

    // 返回为空 MessageConstant.DRAFT_NOT_EXIST
    public static final Long RESPONSE_DATE_NOT_EXIST = 102611L;

    //account related
    public static final Long TOKEN_EXPIRE = 102602L;
    public static final Long USER_NOT_EXISTS = 102601L;
    public static final Long USER_NOT_LOGIN = 102600L;
    public static final Long USER_NAME_PWD_EMPTY = 102619L;
    public static final Long PROGRAM_OPINION_ERROR = 102620L;
    public static final Long SOLUTION_OFFLINE = 102621L;
    public static final Long CHECK_CARD_FAILED = 102622L;
    public static final Long USER_NOT_SPECIFIC = 102611L;//用户不是特定用户
    public static final Long USER_REFUND = 1048581L;//用户申请退款
    public static final Long USER_LOGIN_FAILED = 102604L;//登陆失败
    public static final Long SMS_SEND_LIMIT = 102605L; //发送短信受限
    public static final Long SMS_SEND_MOBILE_FAILED = 102606L; //接收短信的手机号错误

    public static final Long USER_NICK_EXISTS = 102608L; //用户已经存在

    public static final Long SMS_CODE_ERROR = 102609L; //验证码不正确

    public static final Long ADD_HOUSE_REPEAT = 102610L; //新增房产重复

    //product related
    public static final Long PRODUCT_NOT_EXISTS = 102607L;

    //National marketing
    public static final Long MOBILE_NOT_EXISTS = 4194305L;

    //绑定信息
    public static final Long BINGDING_SUCCESS = 5242880L;
    public static final Long INVITE_NOT_AIJIA_USER = 5242881L;
    public static final Long INVITED_NOT_AIJIA_USER = 5242882L;
    public static final Long INVITE_IS_AIJIA_USER = 5242883L;
    public static final Long INVITED_IS_AIJIA_USER = 5242884L;
    public static final Long REPEATER_BINGDING = 5242885L;

    public static final Long STOCK_IS_ZERO = 16L;//库存为0

    public static final Long AJB_NOT_ENOUTH = 17L;//艾积分余额不足

    public static final Long MONEY_ERROR = -12L;// 金钱不对

    public static final Long PRODUCT_ORDER_INVALID = -13L;// 订单数据不正确

    public static final Long ORDER_PAY_FAILED = -14L;// 订单支付失败

    public static final Long ORDER_CANCLE_FAILED = -15L;//订单取消失败

    public static final Long ORDER_STATUS_FAILED = -16L;//错误订单状态;

    public static final Long SERVICE_EXCEPTION = 111L;//服务异常

    public static final Long SERVICE_RESPONSE_NULL = 112L;//服务返回空

    public static final Long SERVICE_RESPONSE_SUCCESS_FALSE = 113L;//服务success值为false


    /**
     * 微信小程序  经纪人
     */
    public static final Long AGENT_SUCCESS = 1L;//成功

    public static final Long AGENT_FAILED = 4L;//失败
    public static final Long AGENT_TOKEN_INVALID = 101L;//token过期

    public static final Long PARAMS_ERROR = -1L;

    public static final Long SERVICE_BUSY = -2L;

    public static final Long SUCCESS_RESPONSE_VO = 30000L;

    public static final Long ABNORMAL_OPERATION = 102702L;//异常操作


    public static final Long SUBMIT_FAILURE = 102701L;

}

package com.ihomefnt.o2o.intf.domain.common.http;

/**
 * @author wanyunxin
 * @create 2019-03-20 20:29
 */
public class HttpReturnCode {

    public static final Long SUCCESS = 001L;

    /**
     * o2o
     */
    public static final Long O2O_FAILED = 102002L;//系统异常
    public static final Long O2O_PARAMS_NOT_EXISTS = 102003L;//参数错误

    public static final Long O2O_NEED_LOGIN = 102697L;//需要登陆
    public static final Long O2O_NEED_HOT_UPDATE = 102698L;//需要热更
    public static final Long O2O_NEED_FORCE_UPDATE  = 102699L;//需要强制更新

    /**
     * dingMonitorWhite
     * 钉钉告警白名单 102600-102700
     */
    public static final Long DING_MONITOR_WHITE_START = 102600L;
    /**
     * 短信验证码验证失败
     */
    public static final Long FAILED_VERIFY_FOR_MSG_CODE = 102613L;
    /**
     * 手机号不正确
     */
    public static final Long MOBILE_NOT_CORRECT = 102614L;

    /**
     * 已点赞
     */
    public static final Long ADD_PRAISE_ALREADY = 102615L;

    /**
     * 记录数限制
     */
    public static final Long RECORD_LIMIT = 102616L;

    /**
     * 无演示权限
     */
    public static final Long NO_PERMISSION = 102618L;
    public static final Long BUILDING_NO_PERMISSION = 102618L;

    /**
     * 不是你的房产
     */
    public static final Long NOT_YOUR_HOUSE = 102619L;

    /**
     * 以下code在HttpResponseCode使用
     * public static final Long USER_NOT_LOGIN = 102600L;
     * public static final Long TOKEN_EXPIRE = 102602L;
     * public static final Long USER_NOT_EXISTS = 102601L;
     * public static final Long ADMIN_ILLEGAL = 102603L;//未登录或该账号在其他地方登录
     **/
    /**
     * 小艾确认设计任务失败
     */
    public static final Long XA_CONFIRM_FAIL = 102612L;

    /**
     * 昵称不合法
     */
    public static final Long NICK_NAME_ILLEGAL = 102616L;

    public static final Long TO_C_KNOWN_ERROR = 102617L;
    /**
     * dms验收
     */
    public static final Long DMS_FAIL_FINAL_CHECK_ERROR = 102663L;

    public static final Long DING_MONITOR_WHITE_END = 102700L;


    /**
     * dolly-web
     */
    public static final Long DOLLY_FAILED = 102102L;//系统异常


    /**
     * ftp-house
     */
    public static final Long FTP_FAILED = 102802L;//系统异常

    /**
     * wcm
     */
    public static final Long WCM_FAILED = 102302L;//系统异常

    /**
     * aladdin-order
     */
    public static final Long ALADDIN_ORDER_FAILED = 102202L;//系统异常

    /**
     * product-web
     */
    public static final Long PRODUCT_WEB_FAILED = 102402L;//系统异常
    public static final Long PRODUCT_WEB_DATE_ERROR = 102403L;//返回数据异常
    public static final Long PRODUCT_WEB_DATE_EMPTY = 102404L;// 返回数据为空

    /**
     * fgw
     */
    public static final Long FGW_FAIL = 102450L;//拉起支付失败
    public static final Long FGW_RESPONSE_EMPTY = 102451L;//支付返回为null


}

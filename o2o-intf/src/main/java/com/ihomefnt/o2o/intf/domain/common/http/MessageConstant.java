package com.ihomefnt.o2o.intf.domain.common.http;

/**
 * Created by shirely_geng on 15-1-14.
 */
public interface MessageConstant {

    String USER_NOT_EXISTS = "用户或密码不正确！";
    String USER_NICK_EXISTS = "用户昵称已经存在！";
    String USER_PASS_EMPTY = "用户名密码为空！";
    String USER_REGISTERED = "用户已经注册";
    String USER_NOT_LOGIN = "你的账号已在其他地方登录，请重新登录";
    String QUERY_FAILED = "查询失败";
    String SOLUTION_OFFLINE = "方案不存在或已下线";
    String WRONG_REQUEST = "错误的请求";
    String DATE_EMPTY = "没有更多数据了";

    //登录注册优化提示
    String USER_LOGIN_FAILED_PSD = "登录失败，若忘记密码请通过验证码登录";

    String DATA_TRANSFER_EMPTY = "传入参数有空值,或传入参数有误";
    // HttpResponseCode.RESPONSE_DATE_NOT_EXIST
    String DRAFT_NOT_EXIST = "数据不存在，请刷新后重试";
    String PRODUCT_NOT_EXISTS = "产品不存在或者已经下架！";
    String SKU_NOT_EXISTS = "商品库存不足！";
    String SKU_OFFLINE = "商品已经下架！";
    String PRODUCT_ORDER_INVALID = "订单数据不正确";

    String MOBILE_NOT_EXISTS = "手机号码不正确";
    String ILLEGAL_USER = "非法用户";
    String QUERY_SUCCESS = "查询成功";
    String PARAMS_NOT_EXISTS = "参数为空或者参数格式不正确";
    String NOT_YOUR_HOUSE = "不是你的房产";
    String NO_PERMISSION = "无演示权限";
    String BUILDING_NO_PERMISSION = "非演示项目";
    String BANKCARD_NOT_EXISTS = "银行卡参数错误";
    String SUCCESS = "成功";
    String OP_SUCCESS = "操作成功";
    String OP_FAILED = "操作失败";
    String FAILED = "系统有点小异常，请稍后再试";
    String FGW_RESPONSE_EMPTY = "支付失败";

    String QUERY_USER_ORDER_FAIL = "查询用户订单失败";


    // 绑定信息
    String INVITE_NOT_AIJIA_USER = "邀请者不是艾佳用户";
    String INVITED_IS_AIJIA_USER = "受邀者已经是艾佳用户";
    String REPEATER_BINGDING = "重复绑定";
    // content 溢出
    String CONTENT_OVER_FLOW = "内容溢出";

    String DATA_EXISTS = "此号码已经报名";

    String ADMIN_ILLEGAL = "您还未登录，请登录后操作";

    String MONEY_OVER_FLOW = "本次付款金额数值不合法";

    String VOUCHER_OVERTIME = "活动到期，下次再来";

    String NO_VOUCHER_EXIST = "领过啦，已经充值到您的账户";

    String NO_EXPSTORE_EXIST = "输入的体验店ID不存在";

    String NO_PAGE_EXIST = "没有更新启动页";

    String UPDATE_FAILED = "更新数据失败";

    String STOCK_ZERO = "库存为0";

    String ITEM_NOT_SUPPORT = "商品不存在或者您已经点击喜欢";

    String AJB_NOT_EXIST = "账户艾积分余额不足！";

    String ITEM_NOT_EXIST = "商品获取失败";

    String FROZE_AJB_FAILED = "冻结艾积分失败";

    String ORDER_PAY_FAILED = "订单支付失败";

    String DDATA_GET_FAILED = "数据获取失败";

    String MONEY_ERROR = "金钱不对";

    String ORDER_CANCLE_FAILED = "订单取消失败";

    String ORDER_CANCLE_FAILED_SEND = "艺术品正在备货中，不能取消订单！";

    String ORDER_STATUS_FAILED = "错误订单状态";

    String ORDER_UNCANCLE_FAILED = "商品已开始定制，取消失败";

    String USER_NOT_SPECIFIC = "oh！您的用户状态存在异常，请联系客服后再操作";

    String ALADDIN_ORDER_REFUND = "您的订单申请取消，不能选择方案，请联系客服";

    String QUERY_EMPTY = "查询为空";

    String ORDER_DRAWBACK = "oh！您有款项处于退款中，请联系客服后再操作";

    String BANK_CARD_NOT_EXISTS = "用户银行卡信息不存在";

    String CHECK_CARD_FAILED = "银行卡输入有误，请检查后重新输入。";

    String NAME_IS_NULL = "姓名不能为空";

    String CARDNO_IS_NULL = "卡号不能为空";

    String PHONENO_IS_NULL = "手机号不能为空";

    String USERNO_IS_NULL = "省份证号不能为空";

    String CODE_ERROR = "验证码输入错误";

    String IDCARD_ERROR = "您的身份证号输入有误，请检查后重新输入。";

    String CREDITCARDS_NOT_SUPPORT = "不支持信用卡";

    String BANKCARDBAND_FAILED = "当前银行卡已绑定";

    String MOBILE_NOT_MATCH_ERROR = "为了您的资金安全，银行卡预留的手机号须和您的全品家订单持有人手机号一致。如须变更订单手机号码，请联系客服进行变更。";

    String INVITERUSER_IS_NULL = "被邀请人不能为空";

    String NEW_USER_OLNY = "您已领取过新人福利，不能重复领取哦";

    String USER_CARDCHECK_ERROR = "您的银行卡有误，或持卡人姓名与全品家订单姓名不符，请核对后再试。";

    /*经纪人小程序*/
    String GRADE_UPGRADE_STANDARD = "{'gradeUpgradeStandardTitle':'权益升级标准','gradeUpgradeStandardDesc':'全品家订单权益，将根据订单交款数额及时间等因素自动获得，详见下表','gradeUpgradeStandardInfo':{'gradeUpgradeStandardInfoTitle':{'gradeLevel':'权益等级','gradeLevelLimit':[{'gradeLevelAmount':'达标金额','gradeLevelTimeLimit':'时限'}]},'gradeUpgradeStandardInfoList':[{'gradeId':3,'gradeLevel':'钻石','gradeLevelLimit':[{'gradeLevelAmount':'交全款，且全款金额≥18万元','gradeLevelTimeLimit':'交首笔定金1个月内'}]},{'gradeId':2,'gradeLevel':'铂金','gradeLevelLimit':[{'gradeLevelAmount':'交全款，且全款金额≥12万元','gradeLevelTimeLimit':'交首笔定金2个月内'}]},{'gradeId':1,'gradeLevel':'黄金','gradeLevelLimit':[{'gradeLevelAmount':'交全款，且全款金额≥5万元','gradeLevelTimeLimit':'交首笔定金3个月内'}]}]}}";
    String GRADE_UPGRADE_STANDARD_NEW = "{'gradeUpgradeStandardTitle':'权益升级标准','gradeUpgradeStandardDesc':'全品家订单权益，将根据订单交款数额及时间等因素自动获得，详见下表','gradeUpgradeStandardInfo':{'gradeUpgradeStandardInfoTitle':{'gradeLevel':'权益等级','gradeLevelLimit':[{'gradeLevelAmount':'达标金额','gradeLevelTimeLimit':'时限'}]},'gradeUpgradeStandardInfoList':[{'gradeId':3,'gradeLevel':'钻石','gradeLevelLimit':[{'gradeLevelAmount':'交款总额≥18万元','gradeLevelTimeLimit':'交首笔定金1个月内，距离交房日期>3个月'}]},{'gradeId':2,'gradeLevel':'铂金','gradeLevelLimit':[{'gradeLevelAmount':'交款总额≥12万元','gradeLevelTimeLimit':'交首笔定金2个月内，距离交房日期>3个月'}]},{'gradeId':1,'gradeLevel':'黄金','gradeLevelLimit':[{'gradeLevelAmount':'交款总额≥5万元','gradeLevelTimeLimit':'交首笔定金3个月内，距离交房日期>3个月'}]},{'gradeId':4,'gradeLevel':'白银','gradeLevelLimit':[{'gradeLevelAmount':'交款总额≥5万元','gradeLevelTimeLimit':'交首笔定金1个月内，距离交房日期≤3个月'}]},{'gradeId':5,'gradeLevel':'青铜','gradeLevelLimit':[{'gradeLevelAmount':'交款总额≥2万元','gradeLevelTimeLimit':'交首笔定金3个月内'}]}]}}";

    String RIHGHT_DETAIL = "[{'detail':[{'title':'立减特权','name':'不享受','url':'https://static.ihomefnt.com/1/image/icon_normal-ljtq@3x.png'},{'title':'天降喜福','name':'不享受','url':'https://static.ihomefnt.com/1/image/icon_normal-tjxft@3x.png'},{'title':'情义无价','name':'不享受','url':'https://static.ihomefnt.com/1/image/icon_normal-qywj@3x.png'},{'title':'基本权益','name':'享受7项','url':'https://static.ihomefnt.com/1/image/icon_normal-jbqy@3x.png'}]},{'detail':[{'title':'立减特权','name':'享受2项','url':'https://static.ihomefnt.com/1/image/icon_golden-ljtq@3x.png'},{'title':'天降喜福','name':'享受5项','url':'https://static.ihomefnt.com/1/image/icon_golden-tjxh@3x.png'},{'title':'情义无价','name':'16选5项','url':'https://static.ihomefnt.com/1/image/icon_golden-qywj@3x.png'},{'title':'基本权益','name':'享受7项','url':'https://static.ihomefnt.com/1/image/icon_golden-jbqy@3x.png'}]},{'detail':[{'title':'立减特权','name':'享受2项','url':'https://static.ihomefnt.com/1/image/icon_platinum-ljtq@3x.png'},{'title':'天降喜福','name':'高配6项','url':'https://static.ihomefnt.com/1/image/icon_platinum-tjxf@3x.png'},{'title':'情义无价','name':'16选8项','url':'https://static.ihomefnt.com/1/image/icon_platinum-qywj@3x.png'},{'title':'基本权益','name':'享受7项','url':'https://static.ihomefnt.com/1/image/icon_platinum-jbqy@3x.png'}]},{'detail':[{'title':'立减特权','name':'享受2项','url':'https://static.ihomefnt.com/1/image/icon_diamond-ljtq@3x.png'},{'title':'天降喜福','name':'顶配7项','url':'https://static.ihomefnt.com/1/image/icon_diamond-tjxf@3x.png'},{'title':'情义无价','name':'享受16项','url':'https://static.ihomefnt.com/1/image/icon_diamond-qywj@3x.png'},{'title':'基本权益','name':'享受7项','url':'https://static.ihomefnt.com/1/image/icon_diamond-jbqy@3x.png'}]}]";
    String RIHGHT_NEW_DETAIL = "[{'detail':[{'title':'立减特权','name':'不享受','url':'https://static.ihomefnt.com/1/image/icon_normal-ljtq@3x.png'},{'title':'专属特权','name':'不享受','url':'https://static.ihomefnt.com/1/image/icon_normal-tjxft@3x.png'},{'title':'基本权益','name':'享受6项','url':'https://static.ihomefnt.com/1/image/icon_normal-jbqy@3x.png'}]},{'detail':[{'title':'立减特权','name':'享受2项','url':'https://static.ihomefnt.com/1/image/icon_golden-ljtq@3x.png'},{'title':'专属特权','name':'享受7项','url':'https://static.ihomefnt.com/1/image/icon_golden-tjxh@3x.png'},{'title':'基本权益','name':'享受6项','url':'https://static.ihomefnt.com/1/image/icon_golden-jbqy@3x.png'}]},{'detail':[{'title':'立减特权','name':'享受2项','url':'https://static.ihomefnt.com/1/image/icon_platinum-ljtq@3x.png'},{'title':'专属特权','name':'享受7项','url':'https://static.ihomefnt.com/1/image/icon_platinum-tjxf@3x.png'},{'title':'基本权益','name':'享受6项','url':'https://static.ihomefnt.com/1/image/icon_platinum-jbqy@3x.png'}]},{'detail':[{'title':'立减特权','name':'享受2项','url':'https://static.ihomefnt.com/1/image/icon_diamond-ljtq@3x.png'},{'title':'专属特权','name':'享受7项','url':'https://static.ihomefnt.com/1/image/icon_diamond-tjxf@3x.png'},{'title':'基本权益','name':'享受6项','url':'https://static.ihomefnt.com/1/image/icon_diamond-jbqy@3x.png'}]},{'detail':[{'title':'立减特权','name':'享受1项','url':'https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b8e27bf4293c117dd749dbdc59cae012af.png!L-SMALL'},{'title':'专属特权','name':'享受2项','url':'https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b82d673c4858d6034cca5815d4d9512a9d.png!L-SMALL'},{'title':'基本权益','name':'享受6项','url':'https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b86d2cb3765142fbdb0bfa0bf0965a4786.png!L-SMALL'}]},{'detail':[{'title':'立减特权','name':'享受2项','url':'https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b87b0acc77de38a91e81045f68d1ae26a3.png!L-SMALL'},{'title':'专属特权','name':'不享受','url':'https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b843220d9cd1a9b756afddda39babbbc50.png!L-SMALL'},{'title':'基本权益','name':'享受6项','url':'https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b89895a0941d8188b31152bf2aa9c0335b.png!L-SMALL'}]}]";
    String RIHGHT_NEW_DETAIL_NEW = "[{\"detail\":[{\"title\":\"基本权益\",\"name\":\"享受6项\",\"url\":\"https://static.ihomefnt.com/1/image/icon_normal-jbqy@3x.png\"},{\"title\":\"专属特权\",\"name\":\"不享受\",\"url\":\"https://static.ihomefnt.com/1/image/icon_normal-tjxft@3x.png\"}]},{\"detail\":[{\"title\":\"专属特权\",\"name\":\"享受7项\",\"url\":\"https://static.ihomefnt.com/1/image/icon_golden-tjxh@3x.png\"},{\"title\":\"基本权益\",\"name\":\"享受6项\",\"url\":\"https://static.ihomefnt.com/1/image/icon_golden-jbqy@3x.png\"}]},{\"detail\":[{\"title\":\"专属特权\",\"name\":\"享受7项\",\"url\":\"https://static.ihomefnt.com/1/image/icon_platinum-tjxf@3x.png\"},{\"title\":\"基本权益\",\"name\":\"享受6项\",\"url\":\"https://static.ihomefnt.com/1/image/icon_platinum-jbqy@3x.png\"}]},{\"detail\":[{\"title\":\"专属特权\",\"name\":\"享受7项\",\"url\":\"https://static.ihomefnt.com/1/image/icon_diamond-tjxf@3x.png\"},{\"title\":\"基本权益\",\"name\":\"享受6项\",\"url\":\"https://static.ihomefnt.com/1/image/icon_diamond-jbqy@3x.png\"}]},{\"detail\":[{\"title\":\"专属特权\",\"name\":\"享受2项\",\"url\":\"https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b82d673c4858d6034cca5815d4d9512a9d.png!L-SMALL\"},{\"title\":\"基本权益\",\"name\":\"享受6项\",\"url\":\"https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b86d2cb3765142fbdb0bfa0bf0965a4786.png!L-SMALL\"}]},{\"detail\":[{\"title\":\"基本权益\",\"name\":\"享受6项\",\"url\":\"https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b89895a0941d8188b31152bf2aa9c0335b.png!L-SMALL\"},{\"title\":\"专属特权\",\"name\":\"不享受\",\"url\":\"https://img13.ihomefnt.com/0b97f2b24cc29f1163e710be9d5fa1b843220d9cd1a9b756afddda39babbbc50.png!L-SMALL\"}]}]";
    String DELETE_SUCCESS = "删除成功";

    String SUBMIT_APPLY_FAILED = "提交申请失败";

    String NO_MATCH_SOLUTION = "当前用户名下无匹配方案";

    String CREATE_LOAN_MESSAGE_ACCOUNTS_DIFFER = "您最多可申请%s元，请重新输入";

    String SIGN_VALIDATE_FAIL = "签名验签失败";


    String NICK_NAME_ILLEGAL = "用户昵称不能含特殊字符";

    String SHOW_USER_REGISTERED = "您已注册,请登录";

    String ORDER_NOT_EXITS = "订单无效！";

    String NONSUPPORT_ORDER = "不支持的下单类型";

    String ID_NUMBER_ILLEGAL = "请输入正确的身份证号!";

    String NO_QUALIFICATION = "您还没有资格参与哦！";

    String FINAL_CHECK_ERROR_MESSAGE_FOR_SALES_RETURN = "您有部分商品正在退换货，暂时不能进行验收，如有疑问可联系艾师傅。";

    String FINAL_CHECK_ERROR_MESSAGE_FOR_UNINSTALLED = "您有部分商品尚未安装完成，暂时不能进行验收，如有疑问可联系艾师傅。";

    String FINAL_CHECK_ERROR_MESSAGE_RESET_PRICE_CHECK = "您有部分商品正在调价审核中，暂时不能进行验收，如有疑问可联系艾师傅。";

    String SUBMIT_FAILURE = "提交失败！";

    String CUSTOM_SKU_NOT_EXISTS ="无可定制商品！";

    String ART_ORDER_END = "该订单已支付，请勿重复提交！";

    String MUST_UPDATE_APP = "请升级到最新版本客户端后再使用该功能";

    String XA_CONFIRM_FAIL = "确认失败，请刷新后重试";

    String RECORD_LIMIT_100 = "每次数量不能超过100";

    String FAIL_COMMIT_INVESTIGATE = "问卷提交失败";
}

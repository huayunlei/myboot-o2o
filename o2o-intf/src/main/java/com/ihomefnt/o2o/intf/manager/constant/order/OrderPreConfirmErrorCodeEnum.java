package com.ihomefnt.o2o.intf.manager.constant.order;

public enum OrderPreConfirmErrorCodeEnum {
    FAIL(2,"系统异常", "系统有点小异常，请稍后再试"),
    ORDER_NOT_EXIST(1000,"订单不存在","订单查询异常，请返回APP首页刷新重试"),
    HAS_CONFIRMED(1001,"已预确认清单","订单状态异常，请返回APP首页刷新重试"),
    ERROR_STATUS(1002,"订单状态不符","订单状态不符，请返回APP首页刷新重试"),
    CASH_NOT_RIGHT(1003,"订单收款进度不符","交款状态异常，请返回APP首页刷新重试"),
    ONLY_AIJIADAI(1004,"贷款专用方案不允许确认清单	","确认方案失败，当前方案仅可用于申请爱家贷"),
    ORDER_NOT_SIGN(1005,"订单未下单不允许操作","方案确认异常，请返回APP首页刷新重试");


    private int code;
    private String errorInfo;
    private String showMsg;

    OrderPreConfirmErrorCodeEnum(int code,String errorInfo, String showMsg) {
        this.code = code;
        this.errorInfo = errorInfo;
        this.showMsg = showMsg;
    }

    public static String getShowMsgByCode(int code) {
        OrderPreConfirmErrorCodeEnum[] values = values();
        for (OrderPreConfirmErrorCodeEnum v : values) {
            if (v.getCode() == code) {
                return v.getShowMsg();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }
}

package com.ihomefnt.o2o.intf.manager.constant.common;

/**
*@description: ResponseCodeEnum
*@author: lichensi
*@create: 2018/8/17
*/
public enum ResponseCodeEnum {

    SUCCESS(1L,"成功",true),
    INVALID_PARAMETER(499L,"参数不合法",false),
     SYSTEM_EXCEPTION(500L,"系统异常",false);

    private Long code;

    private String msg;

    private boolean success;

    ResponseCodeEnum(Long code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public Long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}

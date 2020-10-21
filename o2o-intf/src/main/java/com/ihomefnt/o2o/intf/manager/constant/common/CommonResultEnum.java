package com.ihomefnt.o2o.intf.manager.constant.common;

public enum CommonResultEnum {

    SUCCESS(1,"成功"),

    DATA_ACCESS_ERROR(2,"数据访问出错"),

    SYSTEM_ERROR(500,"系统错误");

    private Integer code;

    private String msg;


    CommonResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CommonResultEnum getEnumByCode(int code){
        CommonResultEnum [] values = values();
        for (CommonResultEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

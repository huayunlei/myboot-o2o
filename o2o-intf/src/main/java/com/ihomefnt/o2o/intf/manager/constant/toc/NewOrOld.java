package com.ihomefnt.o2o.intf.manager.constant.toc;

/**
 * 新用户和老用户
 *
 * @author liyonggang
 * @create 2018-11-20 10:45
 */
public enum NewOrOld {
    NEW(0, "新用户"),
    OLD(1, "老用户");
    Integer code;
    String message;

    NewOrOld(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static NewOrOld getEnumByCode(Integer code) {
        NewOrOld[] values = NewOrOld.values();
        for (NewOrOld value : values) {
            if (value.getCode().equals(code))
                return value;
        }
        return null;
    }
}

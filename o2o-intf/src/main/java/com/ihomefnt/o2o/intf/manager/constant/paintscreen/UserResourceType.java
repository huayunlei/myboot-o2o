package com.ihomefnt.o2o.intf.manager.constant.paintscreen;

/**
 * 用户资源类型
 *
 * @author liyonggang
 * @create 2018-12-10 19:31
 */
public enum UserResourceType {
    CUSTOM(0,"用户上传"),SYSTEM(1,"应用提供");
    Integer code;
    String message;

    UserResourceType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

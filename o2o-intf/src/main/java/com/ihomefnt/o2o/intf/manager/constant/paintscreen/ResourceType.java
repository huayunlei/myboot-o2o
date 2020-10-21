package com.ihomefnt.o2o.intf.manager.constant.paintscreen;

/**
 * 资源类型
 *
 * @author liyonggang
 * @create 2018-12-10 15:06
 */
public enum ResourceType {

    ART_WORK(0,"画作"),ART_BOOK(1,"画集");

    Integer code;
    String message;

    ResourceType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return "ResourceType{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

package com.ihomefnt.o2o.intf.manager.constant.paintscreen;

/**
 * @author liyonggang
 * @create 2018-12-10 15:11
 */
public enum CollectionAction {

    COLLECT(2, "收藏"), CANCEL_COLLECT(1, "取消收藏");

    Integer code;
    String message;

    CollectionAction(Integer code, String message) {
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

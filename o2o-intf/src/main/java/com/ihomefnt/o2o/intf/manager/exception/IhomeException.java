package com.ihomefnt.o2o.intf.manager.exception;

/**
 * Created by onefish on 2017/8/18 0018.
 */
public class IhomeException extends Exception {
    
    private String errorCode;
    private String errorMsg;
    
    public IhomeException(String msg, Throwable e) {
        super(msg ,e);
    }

    public IhomeException(String message) {
        super(message);
    }

    public IhomeException(Throwable cause) {
        super(cause);
    }

    public IhomeException(String errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public IhomeException() {
        super();
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}

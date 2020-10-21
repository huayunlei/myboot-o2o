package com.ihomefnt.o2o.intf.manager.exception;

import java.io.Serializable;

/**
 * Created by onefish on 2017/8/18 0018.
 */
public class IhomeSecureException extends IhomeException implements Serializable{
    
    private static final long serialVersionUID = -238091758285157331L;

    public IhomeSecureException() {
    }

    public IhomeSecureException(String message, Throwable cause) {
        super(message, cause);
    }

    public IhomeSecureException(String message) {
        super(message);
    }

    public IhomeSecureException(Throwable cause) {
        super(cause);
    }

    public IhomeSecureException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }
}

package com.ihomefnt.o2o.intf.manager.exception;

/**
 * Created by shirely_geng on 15-1-15.
 */
@SuppressWarnings("serial")
public class UserAlreadyRegisterException extends BaseException {
    public UserAlreadyRegisterException(String message) {
        super(message);
    }
}

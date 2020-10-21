/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BaseException.java
 * Author:   12070619@cnsuning.com
 * Date:     Oct 24, 2013 10:52:27 AM
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.ihomefnt.o2o.intf.manager.exception;

/**
 * 
 * 功能描述： 系统错误基础类
 * @author piweiwen@126.com
 */
@SuppressWarnings("serial")
public class BaseException extends Exception {

    /**
     * 
     * @param message error message
     * @param cause  cause
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param cause cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 
     * @param message error message
     */
    public BaseException(String message) {
        super(message);
    }

    public BaseException() {
        super();
    }

}

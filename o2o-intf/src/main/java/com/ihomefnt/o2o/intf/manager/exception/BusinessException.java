package com.ihomefnt.o2o.intf.manager.exception;

import com.ihomefnt.o2o.intf.manager.constant.returncode.ReturnCodeEnum;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-11 下午3:08
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BusinessException extends RuntimeException{

    private long code;
    private String message;

    public BusinessException(long code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = HttpResponseCode.FAILED;
    }
    
    
    public BusinessException(ReturnCodeEnum returnCodeEnum) {
        this.code = returnCodeEnum.getCode();
        this.message = returnCodeEnum.getMsg();
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}

/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月26日
 * Description:PushErrorConstant.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.push;

/**
 * @author zhang
 * 推送错误码
 */
public enum PushErrorConstant {
	
	    ERROR_PUSH("调用公共服务push接口推送失败", -1);
	    
	    private String errorMsg;
	    
	    private int errorCode;
	    
	    private PushErrorConstant(String errorMsg, Integer errorCode) {
	        this.errorMsg = errorMsg;
	        this.errorCode = errorCode;
	    }
	    
	    public static String getErrorMsg(Integer errorCode) {
	        for (PushErrorConstant c : PushErrorConstant.values()) {
	            if (c.getErrorCode() == errorCode) {
	                return c.errorMsg;
	            }
	        }
	        return null;
	    }
	    
	    public String getErrorMsg() {
	        return errorMsg;
	    }

	    public void setErrorMsg(String errorMsg) {
	        this.errorMsg = errorMsg;
	    }

	    public int getErrorCode() {
	        return errorCode;
	    }

	    public void setErrorCode(int errorCode) {
	        this.errorCode = errorCode;
	    }
	    
}

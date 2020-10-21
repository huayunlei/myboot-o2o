/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月3日
 * Description:ArtStatusConstant.java 
 */
package com.ihomefnt.o2o.intf.proxy.art;

/**
 * @author zhang
 */
public enum ArtStatusConstant {
	
	CREATE_ERROR("创建失败", 0),
	
    STOCK_EMPTY("库存不足", -1),
    
    AJB_EMPTY("艾积分不足", -2),
    
    ADDRESS_EMPTY("用户没有填写收货地址", -3),

    VOUCHER_INVALID("券已失效",2013),

    VOUCHER_NOT_EXIST("券不存在",2014);

    
    private String errorMsg;
    
    private int errorCode;
    
    private ArtStatusConstant(String errorMsg, Integer errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
    
    public static String getErrorMsg(Integer errorCode) {
        for (ArtStatusConstant c : ArtStatusConstant.values()) {
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

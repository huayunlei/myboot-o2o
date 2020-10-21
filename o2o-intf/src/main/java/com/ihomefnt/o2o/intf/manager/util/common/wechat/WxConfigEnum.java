package com.ihomefnt.o2o.intf.manager.util.common.wechat;

public enum WxConfigEnum {

    IHOME_LIFE("wx6e6da3d54cbf104e","88def669f09b71cf89bee5b7cbfb9992"),

    NEW_STAR("wx4b23054d5f5e9005","a02846b4925bb067cb37b13a72ba559b");

    private String appId;

    private String secret;

    WxConfigEnum(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    public static WxConfigEnum getEnumByAppId(String appId){
        WxConfigEnum [] values = values();
        for (WxConfigEnum value : values) {
            if (value.getAppId().equals(appId)) {
                return value;
            }
        }
        return null;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

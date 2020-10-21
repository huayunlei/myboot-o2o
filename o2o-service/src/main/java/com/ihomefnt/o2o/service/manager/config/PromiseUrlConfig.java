package com.ihomefnt.o2o.service.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PromiseUrlConfig {

    @Value("${promiseUrl}")
    private String promiseUrl;

    public String getPromiseUrl() {
        return promiseUrl;
    }

    public void setPromiseUrl(String promiseUrl) {
        this.promiseUrl = promiseUrl;
    }
    
    
}

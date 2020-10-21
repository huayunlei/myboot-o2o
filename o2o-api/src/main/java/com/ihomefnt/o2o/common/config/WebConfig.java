package com.ihomefnt.o2o.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by shirely_geng on 15-3-5.
 */
@Component
public class WebConfig {
    @Value("${host}")
    public String HOST;
    
    @Value("${cmsUrl}")
    public String cmsUrl;
}

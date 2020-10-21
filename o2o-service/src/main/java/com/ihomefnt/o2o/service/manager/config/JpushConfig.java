package com.ihomefnt.o2o.service.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JpushConfig {
    @Value("${time_to_live}")
    public long TIME_TO_LIVE;

    @Value("${is_produce}")
    public boolean IS_PRODUCE;
}

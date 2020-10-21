package com.ihomefnt.o2o;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySources;
import com.ihomefnt.o2o.common.configuration.MyFilterType;
import com.ihomefnt.o2o.common.configuration.UniqueNameGenerator;
import com.ihomefnt.starter.semporna.EnableSemporna;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSemporna(mongo = true, activeMQ = true, rocketMQ = true, redis = true, xxl = true)
@NacosPropertySource(dataId = "o2o-api", autoRefreshed = true)
@NacosPropertySources({@NacosPropertySource(dataId = "o2o-api", autoRefreshed = true),
        @NacosPropertySource(dataId = "app-monitor-config", groupId = "wcm", autoRefreshed = true),
        @NacosPropertySource(dataId = "program_detail_resource", groupId = "o2o-api", autoRefreshed = true),
        @NacosPropertySource(dataId = "business-properties", groupId = "o2o-api", autoRefreshed = true),
        @NacosPropertySource(dataId = "gui_bom_property_range", groupId = "o2o-api", autoRefreshed = true),
        @NacosPropertySource(dataId = "character_color_resource", groupId = "o2o-api", autoRefreshed = true),
        @NacosPropertySource(dataId = "static_page_data", groupId = "o2o-api", autoRefreshed = true),
        @NacosPropertySource(dataId = "main_core_config", groupId = "o2o-api", autoRefreshed = true),
        @NacosPropertySource(dataId = "demo_config", groupId = "o2o-api", autoRefreshed = true)})
@ComponentScan(nameGenerator = UniqueNameGenerator.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyFilterType.class})}
)
@EnableAsync
public class SpringBootO2oApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootO2oApplication.class, args);
    }

}


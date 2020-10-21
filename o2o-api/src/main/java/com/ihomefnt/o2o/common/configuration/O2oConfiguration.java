package com.ihomefnt.o2o.common.configuration;

import com.ihomefnt.common.util.ServiceLocator;
import com.ihomefnt.o2o.common.filter.ControllerFilter;
import com.ihomefnt.o2o.common.interceptor.AllInterceptor;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonMapperArgumentResolver;
import com.ihomefnt.o2o.service.manager.config.ImageConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Configuration
public class O2oConfiguration implements WebMvcConfigurer {

    @Autowired
    private AllInterceptor allInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(allInterceptor);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new JsonMapperArgumentResolver());
    }

    @Bean
    public FilterRegistrationBean myFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new ControllerFilter());
        filterRegistrationBean.addUrlPatterns("/homePage/*","/deliveryComment/*");
        filterRegistrationBean.setName("paramFilter");
        return filterRegistrationBean;
    }

    /**
     * 欢迎页面
     */
    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/").setViewName("forward:/index.html");
        viewControllerRegistry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.port}")
    private Integer mailPort;
    @Value("${mail.protocol}")
    private String mailProtocol;
    @Value("${mail.defaultEncoding}")
    private String mailDefaultEncoding;
    @Value("${mail.username}")
    private String mailUsername;
    @Value("${mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(this.mailHost);
        javaMailSender.setPort(this.mailPort);
        javaMailSender.setProtocol(this.mailProtocol);
        javaMailSender.setDefaultEncoding(this.mailDefaultEncoding);
        javaMailSender.setUsername(this.mailUsername);
        javaMailSender.setPassword(this.mailPassword);

        return javaMailSender;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setKeepAliveSeconds(300);
        taskExecutor.setQueueCapacity(1000);
        return taskExecutor;
    }

    @Bean
    public ServiceLocator ServiceLocator() {
        return ServiceLocator.init();
    }


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;
    @Bean
    public JedisPool jedisPool() {
        GenericObjectPoolConfig pc = new GenericObjectPoolConfig();
        pc.setMaxIdle(300);
        pc.setMaxTotal(60000);
        pc.setTestOnBorrow(true);
        return new JedisPool(pc, redisHost, redisPort,2000,redisPassword);
    }

    @Bean
    public ImageConfig imageConfig() {
        return ImageConfig.init();
    }

}

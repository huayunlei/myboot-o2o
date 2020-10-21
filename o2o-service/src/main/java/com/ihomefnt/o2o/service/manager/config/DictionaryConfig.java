package com.ihomefnt.o2o.service.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * 功能描述： 安卓客户端相关配置类
 * 
 * @author 作者12074272
 */
@Component
public class DictionaryConfig {

    // 验证客户端请求头名
    @Value("${dictionaryHomeUrl}")
    public String dictionaryHomeUrl;
    
    // 支付宝通知url
    @Value("${notifyUrl}")
    public String notifyUrl;
    
    // 微信通知url
    @Value("${wnotifyUrl}")
    public String wnotifyUrl;
    
    // 微信分笔通知url
    @Value("${subwnotifyUrl}")
    public String subwnotifyUrl;
    
    // 支付宝分笔通知url
    @Value("${subAlipayNotifyUrl}")
    public String subAlipayNotifyUrl;
    
    // 艺术品支付宝分笔通知url
    @Value("${subH5AlipayNotifyUrl}")
    public String subH5AlipayNotifyUrl;
    
    // 艺术品微信分笔通知url
    @Value("${subH5wnotifyUrl}")
    public String subH5wnotifyUrl;
    
    //艺术品支付宝支付（加入艾积分）
    @Value("${subAlipayNotifyUrl290}")
    public String subAlipayNotifyUrl290;
    
    //艺术品微信支付（加入艾积分）
    @Value("${subwnotifyUrl290}")
    public String subwnotifyUrl290;
    
    //文旅商品支付宝回调url
    @Value("${subCultureAlipayNotifyUrl}")
    public String subCultureAlipayNotifyUrl;
    
    //文旅商品微信支付回调url
    @Value("${subCulturewnotifyUrl}")
    public String subCulturewnotifyUrl;
}

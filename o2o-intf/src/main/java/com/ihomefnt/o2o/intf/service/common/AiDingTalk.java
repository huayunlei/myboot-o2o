package com.ihomefnt.o2o.intf.service.common;

import java.math.BigDecimal;

public interface AiDingTalk {

    /**
     * 组装并发送钉钉告警
     *
     * @param classMethodName 类名.方法名
     * @param url             接口名
     * @param params          入参
     * @param code            错误码
     * @param msg             错误信息
     */
    void sendDingTalkWarn(String classMethodName, String url, String params, Long code, String msg);


    /**
     * 组装并发送钉钉告警
     *
     * @param classMethodName 类名.方法名
     * @param url             接口名
     * @param params          入参
     * @param code            错误码
     * @param msg             错误信息
     */
    void sendOrderDingTalkWarn(String classMethodName, String url, String params, Long code, String msg);

    /**
     * 组装并发送钉钉消息
     * @param classMethodName 方法名
     * @param params 请求参数
     * @param draftPrice 草稿价格
     * @param contractAmount 下单价格
     */
    void sendDingTalkInfo(String classMethodName, String params, BigDecimal draftPrice, BigDecimal contractAmount);

    /**
     * 发送钉钉消息
     *
     * @param token  钉钉群token
     * @param params 入参 {"msgtype":"text","at":"@手机号","text":{"content":"发送内容"}}
     */
    void sendDingTalk(String token, String params);

    /**
     * 发送价格校验钉钉告警
     */
    void asynSendCheckPriceDingTalk(String classMethodName, String params, BigDecimal draftPrice, BigDecimal contractAmount);

    /**
     * 并发送钉钉告警（订单）
     *
     */
    void asynSendOrderDingTalkWarn(String classMethodName, String url, String params, Long code, String msg);
}

package com.ihomefnt.o2o.service.manager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihomefnt.o2o.service.manager.config.JpushConfig;

import cn.jpush.api.*;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

@Component
public class JpushUtils {

    @Autowired
    private JpushConfig jpushConfig;
    
	public static final String appKey = "28c97ed0b241a23f766e26af";
	public static final String masterSecret = "c5667272e06eb5b06c8d8325";
	
	public void push_android_alias(final String content,final String title,final String alia,final String description) {
        JPushClient jpush = new JPushClient(masterSecret, appKey,true,864000);
        String [] alias = alia.split(",");
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform. android())
                .setNotification(Notification.android(description,title,null))
                .setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(alias)).build())
                .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                .build();
        try {
        	PushResult result = jpush.sendPush(payload);
            System.out.println(result.toString() + "................................");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public  void push_android_tag(final String content,final String title,final String tag,final String description) {
        JPushClient jpush = new JPushClient(masterSecret, appKey,jpushConfig.IS_PRODUCE,jpushConfig.TIME_TO_LIVE);
        String[] tags = tag.split(",");
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform. android())
                .setNotification(Notification.android(description,title,null))
                .setAudience(Audience.tag(tags))
                .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                .build();
        try {
        	PushResult result = jpush.sendPush(payload);
            System.out.println(result.toString() + "................................");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public  void push_android(String content,String title,String description) {
        JPushClient jpush = new JPushClient(masterSecret, appKey,jpushConfig.IS_PRODUCE,jpushConfig.TIME_TO_LIVE);
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform. android())
                .setNotification(Notification.android(description,title,null))
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                .build();
        try {
        	PushResult result2 = jpush.sendPush(payload);
        	System.out.println(result2.toString() + "................................");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public  void push_ios_alias(String content,String title,String alia,long id,String description,long l) {
        JPushClient jpush = new JPushClient(masterSecret, appKey,jpushConfig.IS_PRODUCE,jpushConfig.TIME_TO_LIVE);
        String [] alias = alia.split(",");
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform. ios())
        		.setNotification(Notification.newBuilder().setAlert(description + "&" + id).addPlatformNotification(IosNotification.newBuilder().incrBadge(+1).build()).build())
                .setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(alias)).build())
                .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                .setOptions(Options.newBuilder().setApnsProduction(jpushConfig.IS_PRODUCE).build())
                .build();
        try {
            if(payload.toString().length() > 200){
            	content = "{\"t\":\"2\",\"e\":" + l +",\"c\":\"" + id + "\",\"m\":\"\",\"p\":\"\",\"b\":\""+ title + "\",\"i\":"+ id +"}";
            	payload = PushPayload.newBuilder().setPlatform(Platform. ios())
            			.setNotification(Notification.newBuilder().setAlert(description + "&" + id).addPlatformNotification(IosNotification.newBuilder().incrBadge(+1).build()).build())
                        .setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(alias)).build())
                        .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                        .setOptions(Options.newBuilder().setApnsProduction(jpushConfig.IS_PRODUCE).build())
                        .build();
            	PushResult result = jpush.sendPush(payload);
            	System.out.println(result.toString() + "................................");
            } else {
            	PushResult result2 = jpush.sendPush(payload);
            	System.out.println(result2.toString() + "................................");
            }
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public  void push_ios_tag(String content,String title,String tag,long id,String description,long l) {
        JPushClient jpush = new JPushClient(masterSecret, appKey,jpushConfig.IS_PRODUCE,jpushConfig.TIME_TO_LIVE);
        String[] tags = tag.split(",");
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform. ios())
        		.setNotification(Notification.newBuilder().setAlert(description + "&" + id).addPlatformNotification(IosNotification.newBuilder().incrBadge(+1).build()).build())
        		.setAudience(Audience.tag(tags))
                .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                .setOptions(Options.newBuilder().setApnsProduction(jpushConfig.IS_PRODUCE).build())
                .build();
        try {
            if(payload.toString().length() > 200){
            	content = "{\"t\":\"2\",\"e\":" + l +",\"c\":\"" + id + "\",\"m\":\"\",\"p\":\"\",\"b\":\""+ title + "\",\"i\":"+ id +"}";
            	payload = PushPayload.newBuilder().setPlatform(Platform. ios())
            			.setNotification(Notification.newBuilder().setAlert(description + "&" + id).addPlatformNotification(IosNotification.newBuilder().incrBadge(+1).build()).build())
                        .setAudience(Audience.tag(tags))
                        .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                        .setOptions(Options.newBuilder().setApnsProduction(jpushConfig.IS_PRODUCE).build())
                        .build();
            	PushResult result = jpush.sendPush(payload);
            	System.out.println(result.toString() + "................................");
            } else {
            	PushResult result2 = jpush.sendPush(payload);
            	System.out.println(result2.toString() + "................................");
            }
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
        }
	}
	
	/**
	 * .setNotification(Notification.ios("1",null).ios_auto_badge().alert(description + "&" + l))
	 * @param content
	 * @param title
	 * @param id
	 * @param description
	 * @param l
	 */
	public  void push_ios(String content,String title,long id,String description,long l) {
        JPushClient jpush = new JPushClient(masterSecret, appKey,jpushConfig.IS_PRODUCE,jpushConfig.TIME_TO_LIVE);
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform. ios())
        		.setNotification(Notification.newBuilder().setAlert(description + "&" + id).addPlatformNotification(IosNotification.newBuilder().incrBadge(+1).build()).build())
        		.setAudience(Audience.all())
                .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                .setOptions(Options.newBuilder().setApnsProduction(jpushConfig.IS_PRODUCE).build())
                .build();
        try {
            if(payload.toString().length() > 200){
            	content = "{\"t\":\"2\",\"e\":" + l +",\"c\":\"" + id + "\",\"m\":\"\",\"p\":\"\",\"b\":\""+ title + "\",\"i\":"+ id +"}";
            	payload = PushPayload.newBuilder().setPlatform(Platform. ios())
            			.setNotification(Notification.newBuilder().setAlert(description + "&" + id).addPlatformNotification(IosNotification.newBuilder().incrBadge(+1).build()).build())
                        .setAudience(Audience.all())
                        .setMessage(Message.newBuilder().setMsgContent(content).setTitle(title).build())
                        .setOptions(Options.newBuilder().setApnsProduction(jpushConfig.IS_PRODUCE).build())
                        .build();
                PushResult result = jpush.sendPush(payload);
                System.out.println(result.toString() + "................................");
            } else {
                PushResult result2 = jpush.sendPush(payload);
                System.out.println(result2.toString() + "................................");
            }
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
        }
	}
}

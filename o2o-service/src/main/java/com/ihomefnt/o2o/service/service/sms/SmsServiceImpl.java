package com.ihomefnt.o2o.service.service.sms;

import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.domain.sms.dto.SendSmsCodeParamVo;
import com.ihomefnt.o2o.intf.manager.util.common.RegexUtil;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.service.sms.SmsService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shirely_geng on 15-1-15.
 */
@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger LOG = LoggerFactory
            .getLogger(SmsServiceImpl.class);

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
    private static Map<String, String> mapMsg=new HashMap<String, String>();
    @Autowired
    private SmsProxy smsProxy;
    @Autowired
    ThreadPoolTaskExecutor mTaskExecutor;
    static{
    	mapMsg.put("validateCode", "验证码：sms，请在20分钟内完成验证。如非本人操作，请忽略。");
        mapMsg.put("InvitedUrl", "您的好友 name 在艾佳挑选家居设计方案，通过以下链接 url 加入查看。");
    	mapMsg.put("qingzhuCode", "恭喜您报名成功，验证码为qingzhuCode，凭验证码至艾佳家居泰达青筑任意体验店即可领取礼品一份（泰达青筑21栋205、206/15栋1701、1702、1703），咨询电话：400-9699-360。");
    	mapMsg.put("rushCode", "尊敬的用户您好，您已成功拍下itemname，请凭手机号码mobile，于time前在location领取。");
    }

    @Override
    public boolean sendSms(String sms, String mobile) {
        System.out.println("send sms called");
         String content=mapMsg.get("validateCode");
         content=content.replaceAll("sms", sms);
         mTaskExecutor.execute(new SmsTask(content, mobile));
        return true;
    }
    
    @Override
    public boolean sendSmsKey(String code, String mobile, String key){
         System.out.println("send qingzhuCode called");
         String content=mapMsg.get(key);
         content=content.replaceAll(key, code);
         mTaskExecutor.execute(new SmsTask(content, mobile));
         return true;
    }
    
    @Override
	public boolean sendSms(Map<String,String> map) {
    	
    	String mobile=map.get("mobile");
    	String keyMsg=map.get("keyMsg");
    	if(RegexUtil.isMobileNO(mobile)){
    	String sms=mapMsg.get(keyMsg);
    	//1.发送邀请短信消息
    	if(keyMsg!=null&&keyMsg.equals("InvitedUrl")){
    		String name=map.get("name");
    		String url=map.get("url");
    		if(name==null||name.equals("")||name.toLowerCase().equals("null")){
    			name="";
    		}
    		sms=sms.replace("name", name);
   		    sms=sms.replace("url", url);
    	}
    	if(mobile!=null&&!mobile.equals("")&&sms!=null&&!sms.equals("")){
    		 mTaskExecutor.execute(new SmsTask(sms, mobile));
    	}
    	}
		return false;
	}
    
    @Override
	public boolean sendRushSms(Map<String,String> map) {
    	
    	String mobile=map.get("mobile");
    	String keyMsg=map.get("keyMsg");
    	
    	if(RegexUtil.isMobileNO(mobile)){
    	String sms=mapMsg.get(keyMsg);
    	//1.发送邀请短信消息
    	if(keyMsg!=null&&keyMsg.equals("rushCode")){
    		String itemName = map.get("itemName");
        	String time = map.get("time");
        	String location = map.get("location");
    		
    		if(itemName==null||itemName.equals("")||itemName.toLowerCase().equals("null")){
    			itemName="";
    		}
    		sms=sms.replace("time", time);
    		sms=sms.replace("mobile", mobile);
    		sms=sms.replace("itemname", itemName);
    		sms=sms.replace("location", location);
    	}
    	if(mobile!=null&&!mobile.equals("")&&sms!=null&&!sms.equals("")){
    		 mTaskExecutor.execute(new SmsTask(sms, mobile));
    	}
    	}
		return false;
	}

    @Override
    public boolean checkSmsCode(CheckSmsCodeParamVo param) {
        return smsProxy.checkSmsCode(param);
    }

    @Override
    public int sendSmsCode(SendSmsCodeParamVo param) {
        return smsProxy.sendSmsCode(param);
    }

    private class SmsTask implements Runnable {
        public String sms;
        public String mobile;

        public SmsTask(String sms, String mobile) {
            this.sms = sms;
            this.mobile = mobile;
        }

        @Override
        public void run() {
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(Url);
            client.getParams().setContentCharset("UTF-8");
            method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

            String content = sms;
            LOG.info("SmsServiceImpl:sms:" + content);
            NameValuePair[] data = {
                    new NameValuePair("account", "cf_ihomefnt"),
                    new NameValuePair("password", "aijia1234567"),
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("content", content),
            };

            method.setRequestBody(data);
            try {
                client.executeMethod(method);
                String SubmitResult = method.getResponseBodyAsString();

                Document doc = DocumentHelper.parseText(SubmitResult);
                Element root = doc.getRootElement();
                String code = root.elementText("code");
                String msg = root.elementText("msg");
                String smsid = root.elementText("smsid");
                LOG.info("mobile:" + mobile + ", code:" + code + ",msg:" + msg + ",smsid:" + smsid);

                if (code.equals("2")) {
                    LOG.info("success!");
                } else {
                    LOG.info("failed!");
                }
            } catch (HttpException e) {
                LOG.info(e.getMessage());
            } catch (IOException e) {
                LOG.info(e.getMessage());
            } catch (DocumentException e) {
                LOG.info(e.getMessage());
            }finally {
                method.releaseConnection();
                client.getHttpConnectionManager().closeIdleConnections(0);
            }
        }
    }

}

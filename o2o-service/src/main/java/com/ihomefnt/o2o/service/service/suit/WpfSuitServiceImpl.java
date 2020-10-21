package com.ihomefnt.o2o.service.service.suit;

import java.util.ArrayList;
import java.util.List;

import com.ihomefnt.o2o.intf.dao.suit.WpfSuitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.cms.utils.StringUtil;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.service.manager.config.MailConfig;
import com.ihomefnt.o2o.intf.service.mail.MailService;
import com.ihomefnt.o2o.intf.domain.mail.dto.MailBean;
import com.ihomefnt.o2o.intf.service.suit.WpfSuitService;
import com.ihomefnt.o2o.intf.domain.suit.dto.HttpWpfAppointmentRequest;
import com.ihomefnt.o2o.intf.domain.suit.dto.HttpWpfServiceRequest;
import com.ihomefnt.o2o.intf.domain.suit.dto.HttpWpfSubmitOrderRequest;
import com.ihomefnt.o2o.intf.domain.suit.dto.HttpWpfSuitRequest;
import com.ihomefnt.o2o.intf.domain.suit.dto.TWpfMaterial;
import com.ihomefnt.o2o.intf.domain.suit.dto.TWpfStyle;
import com.ihomefnt.o2o.intf.domain.suit.dto.TWpfStyleImage;
import com.ihomefnt.o2o.intf.domain.suit.dto.TWpfSuit;
import com.ihomefnt.o2o.intf.domain.suit.dto.TWpfSuitAd;
import com.ihomefnt.o2o.intf.domain.suit.dto.WpfCaseItem;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IpUtils;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageQuality;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;


@Service
public class WpfSuitServiceImpl implements WpfSuitService {

    @Autowired
    WpfSuitDao wpfSuitDao;
    
    @Autowired
    MailConfig mailConfig;
    
    @Autowired
    MailService mailService;
    
    @Autowired
    ApiConfig apiConfig;
    
    private static final Long[] WpfCaseID = {};
    
    @Override
    public List<TWpfSuit> getWpfSuitList() {
    	List<TWpfSuit> wpfSuitList = wpfSuitDao.getWpfSuitList();
    	for (TWpfSuit tWpfSuit : wpfSuitList) {
    		tWpfSuit.setWpfFirstImage(QiniuImageUtils.compressImage(tWpfSuit.getWpfFirstImage(), QiniuImageQuality.LOW, 640, 0));
		}
        return wpfSuitList;
    }

    @Override
    public TWpfSuit getWpfSuitDetail(HttpWpfSuitRequest httpWpfSuitRequest) {
		TWpfSuit tWpfSuit = wpfSuitDao.getWpfSuitDetail(httpWpfSuitRequest.getWpfSuitId());
    	if(tWpfSuit != null){
    		if(!com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil.isNullOrEmpty(tWpfSuit.getWpfAppFirstImage())){
    			tWpfSuit.setWpfAppFirstImage(QiniuImageUtils.compressImage(tWpfSuit.getWpfAppFirstImage(), QiniuImageQuality.LOW,640, 0));
    		}
    		if(!com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil.isNullOrEmpty(tWpfSuit.getWpfFirstImage())){
    			tWpfSuit.setWpfFirstImage(QiniuImageUtils.compressImage(tWpfSuit.getWpfFirstImage(), QiniuImageQuality.LOW, 640, 0));
    		}
        	List<TWpfStyle> wpfStyleList = tWpfSuit.getWpfStyleList();
        	for(int i = 0; i<wpfStyleList.size();i++){
        		TWpfStyle tWpfStyle = wpfStyleList.get(i);
        		String styleName = tWpfStyle.getStyleName();
        		String wpfStyle = styleName.substring(0, 2);
        		String wpfStyleName = styleName.substring(2);
        		tWpfStyle.setWpfStyle(wpfStyle+"风格");
        		tWpfStyle.setWpfStylename(wpfStyleName);
        		wpfStyleList.set(i, tWpfStyle);
        		if(tWpfStyle.getWpfStyleId() == httpWpfSuitRequest.getWpfStyleId()){
        			tWpfSuit.setWpfStyleName(tWpfStyle.getWpfStylename());
        			tWpfSuit.setWpfStyle(tWpfStyle.getWpfStyle());
        		}
        		if(!com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil.isNullOrEmpty(tWpfStyle.getStyleFirstImage())){
        			tWpfStyle.setStyleFirstImage(QiniuImageUtils.compressImage(tWpfStyle.getStyleFirstImage(), QiniuImageQuality.LOW, 640, 0));
        		}
        		
        		List<TWpfStyleImage> wpfStyleImageList = tWpfStyle.getWpfStyleImageList();
        		for (TWpfStyleImage tWpfStyleImage : wpfStyleImageList) {
        			if(!com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil.isNullOrEmpty(tWpfStyleImage.getImage())){
        				tWpfStyleImage.setImage(QiniuImageUtils.compressImage(tWpfStyleImage.getImage(), QiniuImageQuality.LOW, 640, 0));
        			}
    			}
        	}
        	tWpfSuit.setWpfStyleList(wpfStyleList);
        	
        	List<TWpfMaterial> queryWpfMaterialItems = wpfSuitDao.queryWpfMaterialItems(httpWpfSuitRequest.getWpfSuitId());
        	if(null != queryWpfMaterialItems && queryWpfMaterialItems.size()>0) {
        		for (TWpfMaterial tWpfMaterial : queryWpfMaterialItems) {
					switch (tWpfMaterial.getMaterialId()) {
					case 1:
						tWpfMaterial.setMaterialName("主材配置单");
						break;
					case 2:
						tWpfMaterial.setMaterialName("辅材配置单");
						break;
					case 3:
						tWpfMaterial.setMaterialName("家具配置单");
						break;
					default:
						tWpfMaterial.setMaterialName("主材配置单");
						break;
					}
				}
        		
        	}
        	tWpfSuit.setMaterialList(queryWpfMaterialItems);
            return tWpfSuit;
    	}else {
    		return null;
    	}
    	
    }

    @Override
    public List<TWpfStyleImage> getWpfSuitBomImage(HttpWpfSuitRequest httpWpfSuitRequest) {
    	List<TWpfStyleImage> wpfSuitBomImage = wpfSuitDao.getWpfSuitBomImage(httpWpfSuitRequest);
    	for (TWpfStyleImage tWpfStyleImage : wpfSuitBomImage) {
    		if(tWpfStyleImage.getDisplayOrder() == 1){
    			if(!com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil.isNullOrEmpty(tWpfStyleImage.getImage())){
    				tWpfStyleImage.setImage(QiniuImageUtils.compressImage(tWpfStyleImage.getImage(), QiniuImageQuality.LOW, 640, 0));
    			}
    		}
		}
        return wpfSuitBomImage;
    }

    @Override
    public List<TWpfSuitAd> getWpfSuitAd() {
    	List<TWpfSuitAd> wpfSuitAd = wpfSuitDao.getWpfSuitAd();
    	for (TWpfSuitAd tWpfSuitAd : wpfSuitAd) {
    		if(tWpfSuitAd.getDisplayOrder() == 1){
    			if(!com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil.isNullOrEmpty(tWpfSuitAd.getImage())){
    				tWpfSuitAd.setImage(QiniuImageUtils.compressImage(tWpfSuitAd.getImage(), QiniuImageQuality.LOW, 640, 0));
    			}
    		}
		}
        return wpfSuitAd;
    }

    @Override
    public int applyWpfService(HttpWpfServiceRequest httpWpfServiceResquest) {
        int num = wpfSuitDao.applyWpfService(httpWpfServiceResquest);
        if (num > 0) {
            String mobile = httpWpfServiceResquest.getMobile();
            String testIphone = mailConfig.getTestIphone();
            if (testIphone.indexOf(mobile) == -1) {
                Integer appointType = httpWpfServiceResquest.getAppointType();
                String name = httpWpfServiceResquest.getName();
                String wpfName = httpWpfServiceResquest.getWpfName();
                String buildingInfo = httpWpfServiceResquest.getBuildingInfo();
                
                String appointSubject = "预约装修服务";
                StringBuilder sb = new StringBuilder();
                
                if (appointType == 11) {
                    sb.append("接客啦，客户看了");
                    sb.append(wpfName);
                    sb.append("套装之后吵着闹着要预约装修服务哦，客服快快联系！</br>");
                    sb.append("用户希望这样称呼Ta：");
                    sb.append(name);
                    sb.append("</br>");
                    sb.append("手机号码：");
                    sb.append(mobile);
                } else if (appointType == 12) {
                    appointSubject = "预约免费设计";
                    sb.append("接客啦，客户想泡设计师啦，ta要预约免费设计，客服快快联系！");
                    sb.append("用户希望这样称呼Ta：");
                    sb.append(name);
                    sb.append("</br>");
                    sb.append("手机号码：");
                    sb.append(mobile);
                    sb.append("</br>");
                    sb.append("楼盘地址：");
                    sb.append(buildingInfo);
                }  
                
                //创建邮件  
                MailBean mailBean = new MailBean();
                mailBean.setFrom("admin@ihomefnt.com");
                mailBean.setSubject(new StringBuilder(appointSubject).append("邮件通知").toString());
                String recipients = "khtz@ihomefnt.com"; //"rd@ihomefnt.com"
                if (recipients != null) {
                    String[] recipientList = recipients.split(",");
                    mailBean.setToEmails(recipientList);
                }
                
                mailBean.setTemplate(sb.toString());
                //发送邮件  
                mailService.send(mailBean);
            }
        }
        
        return num;
    }

	@Override
	public String setWpfAppointment(HttpWpfAppointmentRequest request) {
	    String city = IpUtils.getLocationCityBySina(request.getIp());
		String result = "";
		request.setCity(city);
		Integer wpfSuitId = request.getWpfSuitId();
		switch (wpfSuitId) {
		case 0:
			request.setSuit_name("全品家系列");
			result = "";
			break;
		case 1:
			request.setSuit_name("舒适家系列");
			result = "舒适家1314元/m²";
			break;
		case 2:
			request.setSuit_name("品质家系列");
			result = "品质家1912元/m²";
			break;
		case 3:
			request.setSuit_name("尊贵家系列");
			result = "尊贵家2399元/m²";
			break;
		case 4:
			request.setSuit_name("奢享家系列");
			result = "奢享家2999元/m²";
			break;
		default:
			break;
		}
		
		int applyH5WpfService = wpfSuitDao.applyH5WpfService(request);
		if(applyH5WpfService > 0){
			String mobile = request.getPhoneNum();
            String testIphone = mailConfig.getTestIphone();
            if (testIphone.indexOf(mobile) == -1) {
                String name = request.getName();
                String wpfName = request.getSuit_name();
                
                String appointSubject = "预约服务";
                StringBuilder sb = new StringBuilder();
                
                    sb.append("接客啦，客户看了");
                    sb.append(wpfName);
                    sb.append("套装之后点击了预约服务哦，客服快快联系！</br>");
                    sb.append("用户希望这样称呼Ta：");
                    sb.append(name);
                    sb.append("</br>");
                    sb.append("手机号码：");
                    sb.append(mobile);
                    sb.append("</br>");
                    sb.append("用户所在城市：");
                    sb.append(request.getCity());
               
                //创建邮件  
                MailBean mailBean = new MailBean();
                mailBean.setFrom("admin@ihomefnt.com");
                mailBean.setSubject(new StringBuilder(appointSubject).append("邮件通知").toString());
                String recipients = "khtz@ihomefnt.com"; //"rd@ihomefnt.com"
                if (recipients != null) {
                    String[] recipientList = recipients.split(",");
                    mailBean.setToEmails(recipientList);
                }
                mailBean.setTemplate(sb.toString());
                //发送邮件  
                mailService.send(mailBean);
            }
		}
		return result;
	}

	@Override
	public List<WpfCaseItem> getWpfCaseList() {
		List<Long> wpfCaseIdList = new ArrayList<Long>();
		for(int i=0;i<WpfCaseID.length;i++){
			wpfCaseIdList.add(i, WpfCaseID[i]);
		}
		List<WpfCaseItem> queryWpfCaseList = null;
		if(wpfCaseIdList.size()>0) {
			queryWpfCaseList = wpfSuitDao.queryWpfCaseList(wpfCaseIdList);
			if(null != queryWpfCaseList && queryWpfCaseList.size()>0) {
				for (WpfCaseItem wpfCaseItem : queryWpfCaseList) {
					
					wpfCaseItem.setWpfCaseUrl(apiConfig.getWpfCaseUrl() + wpfCaseItem.getWpfCaseId());
					wpfCaseItem.setWpfCaseImageUrl(QiniuImageUtils.compressImage(wpfCaseItem.getWpfCaseImageUrl(), QiniuImageQuality.LOW, 640, 390));
				}
			} 
		}
		
		return queryWpfCaseList;
	}

	@Override
	public boolean submitWpfOrder(HttpWpfSubmitOrderRequest request) {
		
		if(null != request){
			String mobile = request.getMobile();
            String testIphone = mailConfig.getTestIphone();
            if (testIphone.indexOf(mobile) == -1) {
                String appointSubject = "";
                StringBuilder sb = new StringBuilder();
                    sb.append("用户预约了全品家！</br>");
                    sb.append("用户姓名：");
                    sb.append(StringUtil.isNullOrEmpty(request.getName()) ? "": request.getName());
                    sb.append("</br>");
                    sb.append("用户电话：");
                    sb.append(StringUtil.isNullOrEmpty(request.getMobile()) ? "" : request.getMobile());
                    sb.append("</br>");
                    sb.append("下单账号：");
                    sb.append(StringUtil.isNullOrEmpty(request.getSubmitMobile()) ? "" : request.getSubmitMobile());
                    sb.append("</br>");
                    sb.append("基本信息：");
                    sb.append(StringUtil.isNullOrEmpty(request.getCityName()) ? "" : request.getCityName());
                    sb.append(" ");
                    sb.append(StringUtil.isNullOrEmpty(request.getCommunity()) ? "" : request.getCommunity());
                    sb.append(" ");
                    sb.append(StringUtil.isNullOrEmpty(request.getSize()) ? "" : request.getSize());
                    sb.append("㎡");
                    sb.append("</br>");
                    sb.append("预约项目：");
                    sb.append(StringUtil.isNullOrEmpty(request.getWpfName()) ? "" : request.getWpfName());
                    sb.append(",");
                    sb.append(StringUtil.isNullOrEmpty(request.getExtraPackName()) ? "" : request.getExtraPackName());
                    sb.append("</br>");
                    sb.append("预估总价：");
                    sb.append(request.getOrderPrice());
                    sb.append("元");
                    sb.append("</br>");
                    sb.append("请在24小时内致电回复");
               
                //创建邮件  
                MailBean mailBean = new MailBean();
                mailBean.setFrom("admin@ihomefnt.com");
                mailBean.setSubject(new StringBuilder(appointSubject).append("有人预约全品家,请在24小时内致电回复!").toString());
                String recipients = "khtz@ihomefnt.com"; //"rd@ihomefnt.com"   khtz@ihomefnt.com
                if (recipients != null) {
                    String[] recipientList = recipients.split(",");
                    mailBean.setToEmails(recipientList);
                }
                mailBean.setTemplate(sb.toString());
                //发送邮件  
                boolean send = mailService.send(mailBean);
                return send;
            }
		}
		
		return false;
	}
	
	
    
}

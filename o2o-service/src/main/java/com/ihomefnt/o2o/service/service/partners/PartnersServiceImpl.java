package com.ihomefnt.o2o.service.service.partners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.service.manager.config.MailConfig;
import com.ihomefnt.o2o.intf.service.mail.MailService;
import com.ihomefnt.o2o.intf.domain.mail.dto.MailBean;
import com.ihomefnt.o2o.intf.service.partners.PartnersService;
import com.ihomefnt.o2o.intf.dao.partners.PartnersDao;
import com.ihomefnt.o2o.intf.domain.partners.dto.Area;
import com.ihomefnt.o2o.intf.domain.partners.dto.Building;
import com.ihomefnt.o2o.intf.domain.partners.dto.TPartners;

@Service
public class PartnersServiceImpl implements PartnersService{
	
	@Autowired
	PartnersDao partnersDao;
	
	@Autowired
	MailService mailService;
	
    @Autowired
    MailConfig mailConfig;

	@Override
	public Long enrollPartners(TPartners partners) {
		Long id = partnersDao.enrollPartners(partners);
		if(null != id && id >0){
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("areaId", partners.getAreaId());
			params.put("buildingId", partners.getBuildingId());
			List<Area> list = partnersDao.queryAreaBuilding(params);
			String areaName = "";
			String buildingName = "";
			if(null != list && list.size() > 0){
				areaName = list.get(0).getAreaName();
				List<Building> blist = list.get(0).getBuildingList();
				if(null != blist && blist.size() > 0){
					buildingName = blist.get(0).getBuildingName();
				}
			}
			 String testIphone = mailConfig.getTestIphone();
			  if (testIphone.indexOf(partners.getMobile()) == -1) {
			        //创建邮件  
			        MailBean mailBean = new MailBean();
			        mailBean.setFrom("admin@ihomefnt.com");
			        mailBean.setSubject("招募合伙人-活动报名");
			        String recipients = mailConfig.getModel();
			        if (recipients != null) {
			            String[] recipientList = recipients.split(",");
			            mailBean.setToEmails(recipientList);
			        }
			        mailBean.setTemplate(
			        		  "姓名:" + partners.getName() + "</br>"  
			                + "年龄:" + partners.getAge() + "</br>" 
			        		+ "联系电话:" + partners.getMobile() + "</br>"
			        		+ "可以服务区域以及楼盘:" + areaName + " " + buildingName + "</br>"
			        		+ "从事过的行业:" + partners.getVocation()+ "</br>"
			        		);
			        //发送邮件  
			        mailService.send(mailBean);
			  }
			

		}
		return id;
	}

	@Override
	public List<String> queryEnrollList() {
		return partnersDao.queryEnrollList();
	}

	@Override
	public String queryEnrollByMobile(String mobile) {
		return partnersDao.queryEnrollByMobile(mobile);
	}

	@Override
	public List<Area> queryAreaBuilding(String cityCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cityCode", cityCode);
		return partnersDao.queryAreaBuilding(params);
	}

}

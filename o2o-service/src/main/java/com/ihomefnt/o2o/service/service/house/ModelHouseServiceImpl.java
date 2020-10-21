package com.ihomefnt.o2o.service.service.house;

import com.ihomefnt.o2o.intf.domain.building.doo.Building;
import com.ihomefnt.o2o.intf.domain.house.dto.THouse;
import com.ihomefnt.o2o.intf.domain.suit.dto.TSuit;
import com.ihomefnt.o2o.service.manager.config.MailConfig;
import com.ihomefnt.o2o.intf.service.mail.MailService;
import com.ihomefnt.o2o.intf.domain.mail.dto.MailBean;
import com.ihomefnt.o2o.intf.service.house.ModelHouseService;
import com.ihomefnt.o2o.intf.dao.house.ModelHouseDao;
import com.ihomefnt.o2o.intf.domain.house.dto.TModelHouses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelHouseServiceImpl implements ModelHouseService{

	@Autowired
	ModelHouseDao modelHouseDao;
	
	@Autowired
	MailService mailService;
	
    @Autowired
    MailConfig mailConfig;

	@Override
	public Long enrollModelHouse(TModelHouses house) {
		Long id = modelHouseDao.enrollModelHouse(house);
		if(null != id && id >0){
			 String testIphone = mailConfig.getTestIphone();
			  if (testIphone.indexOf(house.getMobile()) == -1) {
			        //创建邮件  
			        MailBean mailBean = new MailBean();
			        mailBean.setFrom("admin@ihomefnt.com");
			        mailBean.setSubject("征集样板间-活动报名");
			        String recipients = mailConfig.getModel();
			        if (recipients != null) {
			            String[] recipientList = recipients.split(",");
			            mailBean.setToEmails(recipientList);
			        }
			        mailBean.setTemplate(
			        		  "业主姓名:" + house.getName() + "</br>"  
			                + "联系电话:" + house.getMobile() + "</br>" 
			        		+ "小区名称:" + house.getAddress() + "</br>"
			        		+ "家居风格:" + house.getStyleName() + "</br>"
			        		+ "家具材质:" + house.getMaterial() + "</br>"
			        		+ "开支预算:" + house.getBudget() + "</br>");
			        //发送邮件  
			        mailService.send(mailBean);
			  }

		}
		return id;
	}

	@Override
	public List<String> queryEnrollList() {
		return modelHouseDao.queryEnrollList();
	}

	@Override
	public Building queryBuildingById(Long houseId) {
		return modelHouseDao.queryBuildingById(houseId);
	}

	@Override
	public THouse queryHouseById(Long houseId) {
		return modelHouseDao.queryHouseById(houseId);
	}

	@Override
	public List<TSuit> querySuitList(Long houseId) {
		return modelHouseDao.querySuitList(houseId);
	}
	
}

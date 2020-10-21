package com.ihomefnt.o2o.service.service.formaldehyde;

import com.ihomefnt.o2o.service.manager.config.MailConfig;
import com.ihomefnt.o2o.intf.service.formaldehyde.FormaldehydeService;
import com.ihomefnt.o2o.intf.dao.formaldehyde.FormaldehydeDao;
import com.ihomefnt.o2o.intf.domain.formaldehyde.dto.TFormaldehyde;
import com.ihomefnt.o2o.intf.service.mail.MailService;
import com.ihomefnt.o2o.intf.domain.mail.dto.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaldehydeServiceImpl implements FormaldehydeService{

	@Autowired
	FormaldehydeDao formaldehydeDao;
	
	@Autowired
	MailService mailService;
	
    @Autowired
    MailConfig mailConfig;
	
	@Override
	public Long enrollFormaldehyde(TFormaldehyde formaldehyde) {
		Long id =  formaldehydeDao.enrollFormaldehyde(formaldehyde);
		
		if(null != id && id >0){
			 String testIphone = mailConfig.getTestIphone();
			  if (testIphone.indexOf(formaldehyde.getMobile()) == -1) {
			        //创建邮件  
			        MailBean mailBean = new MailBean();
			        mailBean.setFrom("admin@ihomefnt.com");
			        mailBean.setSubject("免费测甲醛-活动报名");
			        String recipients = mailConfig.getModel();
			        if (recipients != null) {
			            String[] recipientList = recipients.split(",");
			            mailBean.setToEmails(recipientList);
			        }
			        mailBean.setTemplate(
			        		  "业主姓名:" + formaldehyde.getName() + "</br>"  
			                + "联系手机:" + formaldehyde.getMobile() + "</br>" 
			        		+ "楼盘地址:" + formaldehyde.getAddress() + "</br>"
			        		+ "户型空间:" + formaldehyde.getRoom() + "房" + formaldehyde.getLiving() + "厅" + "</br>"
			        		+ "希望上门时间:" + formaldehyde.getAppointTime() + "</br>");
			        //发送邮件  
			        mailService.send(mailBean);
			  }

		}
		return id;
	}

	@Override
	public List<String> queryFormaldehyde() {
		return formaldehydeDao.queryFormaldehyde();
	}

}

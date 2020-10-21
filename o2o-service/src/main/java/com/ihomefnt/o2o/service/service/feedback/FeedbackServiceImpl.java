package com.ihomefnt.o2o.service.service.feedback;

import com.ihomefnt.o2o.intf.dao.feedback.FeedbackDao;
import com.ihomefnt.o2o.intf.domain.feedback.doo.TFeedbackDto;
import com.ihomefnt.o2o.intf.service.feedback.FeedbackService;
import com.ihomefnt.o2o.service.manager.config.MailConfig;
import com.ihomefnt.o2o.intf.service.mail.MailService;
import com.ihomefnt.o2o.intf.domain.mail.dto.MailBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by piweiwen on 15-1-20.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackDao feedbackDao;
    
	@Autowired
	MailService mailService;

	@Autowired
	MailConfig mailConfig;

    @Override
    public Long addFeedback(TFeedbackDto feedback) {
        feedback.setFeedbackTime(new Timestamp(System.currentTimeMillis()));
        Long id = feedbackDao.addFeedback(feedback);
        if(null != id && id > 0){
        	sendOrderMail(feedback);
        }
        return id;
    }
    
	public void sendOrderMail(TFeedbackDto res) {
		if (res != null) {
			 String testIphone = mailConfig.getTestIphone();
			  if (testIphone.indexOf(res.getPhoneNumber()) == -1) {
					// 创建邮件
					MailBean mailBean = new MailBean();
					mailBean.setFrom("admin@ihomefnt.com");
					if(1==res.getType()){
						mailBean.setSubject("意见反馈邮件通知");
					} else {
						mailBean.setSubject("客户咨询邮件通知");
					}
					
					String recipients = mailConfig.getRdRecipients();
					if (recipients != null) {
						String[] recipientList = recipients.split(",");
						mailBean.setToEmails(recipientList);
					}
					mailBean.setTemplate("Hello,来客了,赶快联系他(她)吧!RD的小伙伴们!</br>" + "来客问题："
							+ res.getContent() + "</br>" + "联系电话:"
							+ res.getPhoneNumber() + "</br>");
					// 发送邮件
					mailService.send(mailBean);
			  }

		}
	}
}

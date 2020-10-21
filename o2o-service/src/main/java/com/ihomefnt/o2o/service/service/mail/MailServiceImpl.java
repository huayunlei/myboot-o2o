/**
 * 
 */
package com.ihomefnt.o2o.service.service.mail;

import com.ihomefnt.o2o.intf.service.mail.MailService;
import com.ihomefnt.o2o.intf.domain.mail.dto.MailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * spring发送mail工具
 * 
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    ThreadPoolTaskExecutor mTaskExecutor;


    private void sendMail(MimeMessage msg, MailBean mailBean) {
        log.info("$$$ Send mail Subject:" + mailBean.getSubject() + ", TO:"
                + arrayToStr(mailBean.getToEmails()));
        javaMailSender.send(msg);
    }

    /* 
     * 记日记用的 
     */
    private String arrayToStr(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : array) {
            sb.append(str + " , ");
        }
        return sb.toString();
    }

    /*  
     * 根据 mailBean 创建 MimeMessage 
     */
    private MimeMessage createMimeMessage(MailBean mailBean) throws MessagingException {
        if (!checkMailBean(mailBean)) {
            return null;
        }
        String text = getMessage(mailBean);
        if (text == null) {
            log.warn("@@@ warn mail text is null (Thread name=" + Thread.currentThread().getName()
                    + ") @@@ " + mailBean.getSubject());
            return null;
        }
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        //messageHelper.setFrom(mailBean.getFrom());  
        try {
            messageHelper.setFrom(mailBean.getFrom(), mailBean.getFromName());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());

        }
        messageHelper.setSubject(mailBean.getSubject());
        messageHelper.setTo(mailBean.getToEmails());
        messageHelper.setText(text, true); // html: true  

        return msg;
    }

    /* 
     * @param mailBean 
     * @return 
     */
    private String getMessage(MailBean mailBean) {
        return mailBean.getTemplate();
    }

    /* 
     * check 邮件 
     */
    private boolean checkMailBean(MailBean mailBean) {
        if (mailBean == null) {
            log.warn("@@@ warn mailBean is null (Thread name=" + Thread.currentThread().getName()
                    + ") ");
            return false;
        }
        if (mailBean.getSubject() == null) {
            log.warn("@@@ warn mailBean.getSubject() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (mailBean.getToEmails() == null) {
            log.warn("@@@ warn mailBean.getToEmails() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (mailBean.getTemplate() == null) {
            log.warn("@@@ warn mailBean.getTemplate() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see com.ihomefnt.o2o.intf.service.mail.MailService#send(com.ihomefnt.o2o.intf.domain.mail.dto.MailBean)
     */
    @Override
    public boolean send(MailBean mailBean) {
        mTaskExecutor.execute(new MailTask(mailBean));
        return true;
    }

    private class MailTask implements Runnable {
        MailBean mailBean;
        public MailTask(MailBean mailBean) {
            this.mailBean = mailBean;
        }

        @Override
        public void run() {
            MimeMessage msg = null;
            if (mailBean == null) {
                return;
            }
            try {
                msg = createMimeMessage(mailBean);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            if (msg == null) {
                return;
            }

            try {
                sendMail(msg, mailBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}

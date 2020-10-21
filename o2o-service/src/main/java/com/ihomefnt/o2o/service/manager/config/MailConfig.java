/**
 * 
 */
package com.ihomefnt.o2o.service.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Administrator
 *
 */
@Component
public class MailConfig {
    @Value("${mail.recipients}")
    private String recipients;
    @Value("${test.iphone}")
    private String testIphone;
    
    @Value("${mail.rdRecipients}")
    private String rdRecipients;

    @Value("${mail.model}")
    private String model;
    
    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getTestIphone() {
        return testIphone;
    }

    public void setTestIphone(String testIphone) {
        this.testIphone = testIphone;
    }

	public String getRdRecipients() {
		return rdRecipients;
	}

	public void setRdRecipients(String rdRecipients) {
		this.rdRecipients = rdRecipients;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}

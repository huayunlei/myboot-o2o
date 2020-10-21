/**
 * 
 */
package com.ihomefnt.o2o.intf.service.mail;

import com.ihomefnt.o2o.intf.domain.mail.dto.MailBean;

/**
 * @author Administrator
 *
 */
public interface MailService {
    public boolean send(MailBean mailBean);

}

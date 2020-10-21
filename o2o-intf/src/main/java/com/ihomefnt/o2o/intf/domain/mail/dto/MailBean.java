/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.mail.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Administrator
 *
 */
@Data
public class MailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String from;
    private String fromName;
    private String[] toEmails;

    private String subject;

    private Map<?, ?> data; //邮件数据  
    private String template; //邮件模板  
}


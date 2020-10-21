package com.ihomefnt.o2o.intf.domain.user.doo;

import lombok.Data;

/**
 * 
 * 功能描述：演示entity
 * @author piweiwen@126.com
 */
@Data
public class RegisterDo {
    
    /**主鍵 */
    private Long rId;
    private String password;
    private String mobile;
    private String activateCode;
    private String registerKey;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }
}

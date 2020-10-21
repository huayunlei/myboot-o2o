package com.ihomefnt.o2o.intf.domain.user.doo;

import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;


/**
 * 
 * 功能描述：演示entity
 * @author piweiwen@126.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class UserDo {
    
    /**主鍵 */
    private Long uId;
    private String name;
    private String nickName;
    private String password;
    private String mobile;
    private String telephone;
    private String uImg;
    private String recMobile;

    private String deviceToken;
    private Integer osType;
    private String location;
    private String pValue;

    private Integer grade; 
    private Timestamp regTime; 
    private String briefIntroduce;

    private String loginSms;
    private Timestamp loginSmsDeadLine;

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }
	public String getuImg() {
		return uImg;
	}

	public void setuImg(String uImg) {
		this.uImg = uImg;
	}
}

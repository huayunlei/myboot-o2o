package com.ihomefnt.o2o.intf.domain.user.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.emchat.vo.response.EmchatIMUserResponseVo;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-12.
 */
@Data
public class LoginResponseVo{
    private String accessToken;
    private String refreshToken;
    private Long expire;
    private List<String> tag;//新增字段，返回用户tag
    
    private String nickName = "";//用户昵称(昵称没有返回值为null)
    private String avatar = "";
    
    /*
     * 环信登录成功返回easemobToken
     */
    private String easemobToken;
    
    /*
     * 环信登录成功返回easemobExpire
     */
    private String easemobExpire;
    
    private EmchatIMUserResponseVo emchatIMUser;
    
    private int chargeAjb = 0; //0.不充值艾积分  1.已充值50艾积分

    private String realName;//真实姓名

    private String idCardNum;//身份证号码
    
}

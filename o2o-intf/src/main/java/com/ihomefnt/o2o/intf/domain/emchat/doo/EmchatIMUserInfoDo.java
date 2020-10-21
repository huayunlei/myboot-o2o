package com.ihomefnt.o2o.intf.domain.emchat.doo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmchatIMUserInfoDo {

    private String userName;    //环信IM用户名
    private String encryptedPassword;   //加密后的环信IM用户密码
    private Integer statusCode; //注册环信用户时返回的状态码
    private Integer activated;  //注册后返回的用户状态   -1  不存在  0  没有激活     1  激活
    private String uuid;    //环信IM用户唯一标识
    private String nickName;    //环信IM用户昵称
    
    public EmchatIMUserInfoDo(String userName, Integer statusCode) {
        this.userName = userName;
        this.statusCode = statusCode;
    }
    
    public EmchatIMUserInfoDo(String userName, Integer statusCode, String nickName, String encryptedPassword, Integer activated, String uuid) {
        this.userName = userName;
        this.statusCode = statusCode;
        this.nickName = nickName;
        this.encryptedPassword = encryptedPassword;
        this.activated = activated;
        this.uuid = uuid;
    }
}

package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MemberDto{

	/**
     *主键
     */
    private Integer id;

    /**
     *用户id
     */
    private Integer userId;

    /**
     *昵称
     */
    private String nickName;

    /**
     *头像
     */
    private String uImg;

    /**
     *1.男2.女 0.未知
     */
    private Integer gender;

    /**
     *年龄
     */
    private Integer age;
    
    /**
     * 个人简介
     */
    private String brief;

    /**
     *创建时间
     */
    private Date createTime;

    /**
     *修改时间
     */
    private Date updateTime;
    
    /**
	 * 用户名
	 */
	private String username;
    
    /**
     *出生日期
     */
 	private Date birthDate;
 	
    /**
     *个人照片
     */
 	private String photoImg;

    public String getuImg() {
        return uImg;
    }

    public void setuImg(String uImg) {
        this.uImg = uImg;
    }
}
package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("小星星艺术家信息")
public class StarArtistDto implements Serializable {

	private static final long serialVersionUID = 9174773413745168837L;

	/**
     *用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;
    
    /**
     *用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

    /**
     *昵称
     */
    @ApiModelProperty("昵称")
    private String nickName;

    /**
     *头像
     */
    @ApiModelProperty("头像")
    private String uImg;

    /**
     *1.男2.女 0.未知
     */
    @ApiModelProperty("1.男2.女 0.未知")
    private Integer gender;

    /**
     *年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;
    
    /**
     * 个人简介
     */
    @ApiModelProperty("个人简介")
    private String brief;

    /**
     *出生日期
     */
    @ApiModelProperty("出生日期")
 	private Date birthDate;
 	
    /**
     *个人照片
     */
    @ApiModelProperty("个人照片")
 	private String photoImg;
    
    /**
     *出生日期 yyyy-MM-dd
     */
 	private String birthDateStr;
}

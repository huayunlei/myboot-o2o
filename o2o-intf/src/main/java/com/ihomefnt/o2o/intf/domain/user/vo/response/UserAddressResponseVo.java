package com.ihomefnt.o2o.intf.domain.user.vo.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel("UserAddressResponseVo")
public class UserAddressResponseVo {
	
	/**
     *主键
     */
	@ApiModelProperty("地址ID")
    private Integer id;

    /**
     *用户id
     */
	@ApiModelProperty("用户ID")
    private Integer userId;

    /**
     *省id
     */
	@ApiModelProperty("省id")
    private Integer provinceId;

    /**
     *城市id
     */
	@ApiModelProperty("城市id")
    private Integer cityId;

    /**
     *区县id
     */
	@ApiModelProperty("区县id")
    private Integer countryId;

    /**
     *地址
     */
	@ApiModelProperty("地址")
    private String address;

    /**
     *是否默认地址 0 不是 1是
     */
	@ApiModelProperty("是否默认地址 0 不是 1是")
    private Boolean isDefault;

    /**
     *邮政编号
     */
	@ApiModelProperty("邮政编号")
    private String postCode;

    /**
     *邮箱
     */
	@ApiModelProperty("邮箱")
    private String email;

    /**
     *电话号码
     */
	@ApiModelProperty("电话号码")
    private String telephone;

    /**
     *手机号码
     */
	@ApiModelProperty("手机号码")
    private String mobile;

    /**
     *收货人姓名
     */
	@ApiModelProperty("收货人姓名")
    private String consignee;

    /**
     *创建时间
     */
	@ApiModelProperty("创建时间")
    private Date createTime;

    /**
     *更新时间
     */
	@ApiModelProperty("更新时间")
    private Date updateTime;

}

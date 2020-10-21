package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

import java.util.Date;

/**
 * 商户
 * @author Ivan Shen
 */
@Data
public class SellerVo {

	/**
     *主键
     */
    private Integer id;

    /**
     *商户名称
     */
    private String name;

    /**
     *商户电话
     */
    private String tel;

    /**
     *商户地址
     */
    private String addr;

    /**
     *邮件
     */
    private String email;

    /**
     *区域ID
     */
    private Integer areaId;

    /**
     *商户密码（用于消费确认）
     */
    private String pwd;

    /**
     *已删除
     */
    private Integer deleted;

    /**
     *创建时间
     */
    private Date createTime;

    /**
     *修改时间
     */
    private Date updateTime;

}
package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.util.Date;
@Data
public class RoleDto {

	private Integer id;

    /**
     *角色名称
     */
    private String name;

    /**
     *角色别称
     */
    private String alias;

    /**
     *角色代码
     */
    private String code;

    /**
     *角色描述
     */
    private String description;

    /**
     *是否可用（0：不可用 1：可用）
     */
    private Boolean enable;

    /**
     *排序号
     */
    private Integer orderNo;

    /**
     *更新时间
     */
    private Date updateTime;

}
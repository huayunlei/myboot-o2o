package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.util.Date;
@Data
public class DesignerVo{
	
	private Integer id;

    /**
     *用户ID
     */
    private Integer userId;
    
    /**
     * 真实姓名
     */
    private String name;

    /**
     *设计案例
     */
    private String designCase;

    /**
     *公司
     */
    private String company;

    /**
     *住所
     */
    private String residency;

    /**
     *补充
     */
    private String extra;

    private Date updatetime;

    private Date createtime;
}
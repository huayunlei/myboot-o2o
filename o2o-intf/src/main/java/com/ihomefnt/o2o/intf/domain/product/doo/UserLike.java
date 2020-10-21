package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.sql.Date;

@Data
public class UserLike {

	/*
	 * id
	 */
    private Long id;
	
    /*
	 * 用户ID
	 */
    private Long userId;
    
    /*
	 * 点赞对象ID
	 */
    private Long productId ;
    
    /*
	 * 点赞类型
	 */
    private Date createTime;
    
    /*
	 * 点赞类型
	 */
    private String type;
    
    /*
	 * 状态
	 */
    private int status;

}

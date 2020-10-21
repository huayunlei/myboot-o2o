package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.sql.Date;
import java.util.List;
@Data
public class UserComment {

	/*
	 * id
	 */
    private String commentId;
	
    /*
	 * 用户ID
	 */
    private String userId;
    
	/*
	 * 用户名
	 */
    private String userName;
    
    /*
	 * 评论星数
	 */
    private int starCount;
    
    /*
	 * 评论内容
	 */
    private String content;
    
    /*
	 * 评论时间
	 */
    private Date createTime ;
    
    /*
	 * 图片链接
	 */
    private List<String> imageList ;
    
    /*
	 * 图片字符串
	 */
    private String images;
    
    /*
	 * 评论对象ID
	 */
    private Long targetId ;
    
    /*
	 * 评论类型
	 */
    private String type;
    
    /*
	 * 状态
	 */
    private String status;
    
    /*
	 * 购买日期
	 */
    private String purchaseTime;
    
    /*
	 * 购买类型
	 */
    private String purchaseType;
    
    /*
     * 评论者昵称 
     */
    private String customerNickName;

}

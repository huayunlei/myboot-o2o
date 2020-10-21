package com.ihomefnt.o2o.intf.domain.shareorder.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * 用户评论
 * @author Charl
 */
@Data
public class ShareOrderComment {
	
	@Id
	private String commentId;
	
	/**
	 * 关联的晒家
	 */
	private String shareOrderId;
	
	/**
     * 用户id
     */
    private long userId;

    /**
     * 用户昵称
     */
    private String userNickName;
    
    private String userImg;
    
    /**
     * 评论内容
     */
    private String comment;
    
    /**
     * 评论时间
     */
    private long createTime;
    
    private String createTimeDesc;
    
    private Integer replyUserId;//回复用户ID
    
    private String replyNickName;//回复用户昵称
    
    private Integer officialFlag;//官方回复标志   1官方回复 2其他回复

    private Integer deleteFlag;//删除标志（-1已删除  0未删除）
    
    private String deleteTime;//删除时间
    
    private Integer commentStatus;// 1:审批通过, 2:审批不通过
    
}

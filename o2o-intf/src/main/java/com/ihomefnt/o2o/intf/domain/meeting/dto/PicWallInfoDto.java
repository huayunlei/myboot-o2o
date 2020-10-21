package com.ihomefnt.o2o.intf.domain.meeting.dto;

import lombok.Data;

import java.util.Date;

/**
 * 照片信息
 * @author ZHAO
 */
@Data
public class PicWallInfoDto {
	private Integer picId;//图片ID
	
	private Integer familyId;//家庭ID
	
	private Integer memberId;//成员ID
	
	private String projectName;//项目楼盘名称
	
	private Date publishTime;//发布时间
	
	private String urls;//图片地址集合

	private String memberName;//成员昵称
}

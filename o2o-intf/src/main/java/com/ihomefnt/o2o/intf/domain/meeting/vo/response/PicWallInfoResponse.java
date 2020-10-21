package com.ihomefnt.o2o.intf.domain.meeting.vo.response;


import lombok.Data;

/**
 * 照片墙信息
 * @author ZHAO
 */
@Data
public class PicWallInfoResponse {
	private Integer picId;//图片ID
	
	private Integer familyId;//家庭ID
	
	private Integer memberId;//成员ID
	
	private String projectName;//项目楼盘名称
	
	private String publishTime;//发布时间
	
	private String smallImage;//小图

	private String bigImage;//大图

	private String memberName;//成员昵称

	public PicWallInfoResponse() {
		this.picId = -1;
		this.familyId = -1;
		this.memberId = -1;
		this.projectName = "";
		this.publishTime = "";
		this.smallImage = "";
		this.bigImage = "";
		this.memberName = "";
	}

}

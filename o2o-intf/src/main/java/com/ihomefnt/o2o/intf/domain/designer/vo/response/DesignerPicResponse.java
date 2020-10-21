package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class DesignerPicResponse {
	private Long idtDesignerPic;
	private String designerPicUrl;
	private String designerImg;
	private String designerName;
	private Long designerId;
	private String uploadMsg;
	private Integer mark;
	public DesignerPicResponse(DesignerPic designerPic, UserDo user) {
		if(designerPic!=null){
			this.idtDesignerPic=designerPic.getId();
			this.designerPicUrl=designerPic.getUrl();
			this.designerImg=user.getuImg();
			this.designerName=user.getNickName();
			this.designerId=designerPic.getOwnerId();
			this.mark=designerPic.getMark();
			Date createTime=designerPic.getCreatetime();
			 Date now = new Date();
			 long l=now.getTime()-createTime.getTime();
			 long s=(l/1000);
			 if(s<60){
				 this.uploadMsg="刚刚";
			 }else if(s<60*60){
				 this.uploadMsg=s/60+"分钟前";
			 }else if(s<60*60*24){
				 this.uploadMsg=s/3600+"小时前";
			 }else if(s<60*60*24*2){
				 this.uploadMsg="昨天";
			 }else if(s<60*60*24*30){
				 this.uploadMsg=s/(3600*24)+"天前";
			 }else if(s<60*60*24*365){
				 SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
				 this.uploadMsg=df.format(createTime);
			 }else{
				 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 this.uploadMsg=df.format(createTime);
			 }
		}
	}
}

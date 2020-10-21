package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import lombok.Data;

@Data
public class DesignerResponse {
	private Long designerId;
	private String uImg;
	private String nickName;
	private String telephone;
    private List<DesignerPic> designerPicList;
	public String getuImg() {
		return uImg;
	}
	public void setuImg(String uImg) {
		this.uImg = uImg;
	}
}

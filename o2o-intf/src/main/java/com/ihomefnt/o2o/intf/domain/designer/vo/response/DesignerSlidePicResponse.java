package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import lombok.Data;

@Data
public class DesignerSlidePicResponse {
	private String designerPicUrl;
	private Long idtDesignerPic;
	private Boolean isLastOne=false;
	public DesignerSlidePicResponse(List<DesignerPic> designerPics) {
		if(designerPics!=null&&!designerPics.isEmpty()){
			this.designerPicUrl=designerPics.get(0).getUrl();
			this.idtDesignerPic=designerPics.get(0).getId();
			if(designerPics.size()==1){
				this.isLastOne=true;
			}else{
				this.isLastOne=false;
			}
		}
		
	}
}

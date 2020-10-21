package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import java.util.ArrayList;
import java.util.List;

import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import lombok.Data;

@Data
public class HttpDesignerPicResponse {

	private List<DesignerPicResponse> designerPicList=new ArrayList<DesignerPicResponse>();


    public HttpDesignerPicResponse(List<DesignerPic> designerPics, List<UserDo> users) {
		for (int i = 0; i < designerPics.size(); i++) {
			if(designerPics!=null){
                DesignerPicResponse designerPicResponse = new DesignerPicResponse(
                        designerPics.get(i), users.get(i));
			designerPicList.add(designerPicResponse);
			}
		}
	}
}

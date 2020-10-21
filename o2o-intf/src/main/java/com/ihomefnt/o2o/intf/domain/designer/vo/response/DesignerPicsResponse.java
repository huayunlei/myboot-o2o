package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import lombok.Data;

import java.util.List;

@Data
public class DesignerPicsResponse {
    private List<DesignerPic> designerPicList;
	private List<String> cutParams;
}

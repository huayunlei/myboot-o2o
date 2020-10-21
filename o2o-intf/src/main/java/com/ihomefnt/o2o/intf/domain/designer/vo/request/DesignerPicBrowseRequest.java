package com.ihomefnt.o2o.intf.domain.designer.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class DesignerPicBrowseRequest extends HttpBaseRequest {
	private Long idtDesignerPic;//设计师图片的id首页默认为空
	private Boolean shiftLeft;//左移右移
	private Long designerId;//设计师id
}

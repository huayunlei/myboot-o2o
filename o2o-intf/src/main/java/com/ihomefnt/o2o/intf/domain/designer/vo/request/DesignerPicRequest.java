package com.ihomefnt.o2o.intf.domain.designer.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class DesignerPicRequest extends HttpBaseRequest {

	private Long idtDesignerPic;//设计师图片的id首页默认为空
	private Long designerId;
	private Integer count;
}

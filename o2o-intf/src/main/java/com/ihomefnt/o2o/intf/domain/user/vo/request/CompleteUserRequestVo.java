package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class CompleteUserRequestVo extends HttpBaseRequest {
	
	private boolean preState = false; //修改前状态
	
	private boolean nextState = false; //修改后状态
}

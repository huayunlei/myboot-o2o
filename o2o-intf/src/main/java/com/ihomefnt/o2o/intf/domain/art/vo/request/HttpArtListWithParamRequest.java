package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class HttpArtListWithParamRequest extends HttpBaseRequest{
	
	private String searchParam; //搜索字符串

	private int pageSize; //分页大小
	
	private int pageNo; //第几个分页
	
	private long startIndex; //后台用
}

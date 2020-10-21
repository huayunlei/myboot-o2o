package com.ihomefnt.o2o.intf.domain.shareorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 评论列表请求request
 * @author Charl
 */
@Data
@ApiModel(value = "评论列表请求")
public class HttpShareOrderCommonListRequest extends HttpBaseRequest{
	
	@ApiModelProperty("晒家类型:0 表示老晒家, 1 表示专题")
	private int type;
	
	@ApiModelProperty("第几页评论")
	private int page;
	
	@ApiModelProperty("每页多少评论")
	private int limit;
	
	@ApiModelProperty("晒家id")
	private String shareOrderId;
	
	@ApiModelProperty("新家大晒条目id集合")
	private List<String> shareOrderIdList;
}

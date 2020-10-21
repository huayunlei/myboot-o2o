package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * 艺术品列表请求
* @Title: HttpArtListRequest.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月15日 下午1:06:09 
* @version V1.0
 */
@Data
public class HttpArtListRequest extends HttpBaseRequest {
	
	private Integer typeId; //艺术品类型id：墙上挂件，立体摆件 
	
	private Integer spaceId; //艺术品空间id，（第一次请求可不填，默认为0）
	
	private int pageSize; //分页大小
	
	private int pageNo; //第几个分页
	
	private Boolean isFirst; //是否为第一次请求接口
	
	private long startIndex; //后台用
}

package com.ihomefnt.o2o.intf.domain.homecard.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * WCM代理视频列表返回对象
 * @author ZHAO
 */
@Data
public class VideoListResponseVo {
	private List<VideoResponseVo> list = Lists.newArrayList();//视频列表集合
	
	private Integer pageNo = 1;//当前第几页
	
	private Integer pageSize = 10;//每页显示多少条
	
	private Integer totalCount = 0;//总共多少条
	
	private Integer totalPage = 1;//总共多少页
}
